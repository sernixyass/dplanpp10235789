package com.example.carpoolingappv1;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.carpoolingappv1.util.ViewWeightAnimationWrapper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Adapter.MyAdapter;
import Model.ListItem;

import static com.example.carpoolingappv1.util.Constants.MAPVIEW_BUNDLE_KEY;

public class HomeFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {


    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter ;
    private List<ListItem> mListItems;
    private FloatingActionButton buttonAddPost;

    //private MyAdapter adapter;
    private FirebaseRecyclerAdapter adapterFire;
    private FirebaseRecyclerAdapter adapterFireS;

    public static String selectedTripID;
    public static Integer selectedPlacesTrip;
    public static String selectedIDPostedIt;

    //MAP
    public MapView mMapView;
    GoogleMap mapH;
    //public static RelativeLayout mMapContainer;
    //public static RelativeLayout mPostsContainer;

    private EditText searchField ;
    private DatabaseReference mRideReference ;


    public boolean exist;


    /*
    public static final int MAP_LAYOUT_STATE_CONTRACTED = 0;
    public static final int MAP_LAYOUT_STATE_EXPANDED = 1;
    public static int mMapLayoutState = 0;
*/



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_home,container,false);


        //view.findViewById(R.id.logoutBtnId).setOnClickListener(this);
        mRecyclerView=view.findViewById(R.id.recyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mListItems=new ArrayList<>();





        fetch();

/*
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference().child("posts");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        ListItem l=npsnapshot.getValue(ListItem.class);
                        mListItems.add(l);
                    }
                    mAdapter=new MyAdapter(mListItems);
                    mRecyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        //map
        //mMapView =  view.findViewById(R.id.mapHome);
        //mMapContainer =  view.findViewById(R.id.map_container);
        //mPostsContainer =  view.findViewById(R.id.posts_Container);



        /*
        mMapView = (MapView) view.findViewById(R.id.mapHome);
        mMapContainer = (RelativeLayout) view.findViewById(R.id.map_container);
        mPostsContainer = (RelativeLayout) view.findViewById(R.id.posts_Container);
        */
        //view.findViewById(R.id.btn_full_screen_map).setOnClickListener(this);

        //initGoogleMap(savedInstanceState);

        FragmentActivity activity = (FragmentActivity) view.getContext();
        FragmentManager manager = activity.getSupportFragmentManager();

        //floating button
        buttonAddPost = view.findViewById(R.id.btn_add_Post);
        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddPostActivity.class));

            }
        });



        return view;
    }

    private void search() {
        Intent intent = new Intent(getActivity(),search_pop_up.class);
        startActivity(intent);
    }


    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("posts");

        FirebaseRecyclerOptions<ListItem> options =
                new FirebaseRecyclerOptions.Builder<ListItem>()
                        .setQuery(query, new SnapshotParser<ListItem>() {
                            @NonNull
                            @Override
                            public ListItem parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new ListItem(
                                        snapshot.child("startingPoint").getValue().toString(),
                                        snapshot.child("endingPoint").getValue().toString(),
                                        snapshot.child("places").getValue(Integer.class),
                                        snapshot.child("id").getValue().toString(),
                                        snapshot.child("tripPos").getValue(LatLng.class),
                                        snapshot.child("tripDesPos").getValue(LatLng.class),
                                        snapshot.child("accountIDPostedIt").getValue().toString(),


                                        snapshot.child("saturday").getValue(Boolean.class),
                                        snapshot.child("sunday").getValue(Boolean.class),
                                        snapshot.child("monday").getValue(Boolean.class),
                                        snapshot.child("tuesday").getValue(Boolean.class),
                                        snapshot.child("wednesday").getValue(Boolean.class),
                                        snapshot.child("thursday").getValue(Boolean.class),
                                        snapshot.child("friday").getValue(Boolean.class),

                                        snapshot.child("isTaken").getValue(Boolean.class),
                                        snapshot.child("isFull").getValue(Boolean.class),

                                        snapshot.child("accountIDTakedIt").getValue().toString(),

                                        snapshot.child("accountIDJoining1").getValue().toString(),
                                        snapshot.child("accountIDJoining2").getValue().toString(),
                                        snapshot.child("accountIDJoining3").getValue().toString(),
                                        snapshot.child("accountIDJoining4").getValue().toString(),

                                        snapshot.child("hourTrip").getValue().toString()
                                        );
                            }
                        })
                        .build();




        adapterFire = new FirebaseRecyclerAdapter<ListItem, MyAdapter.ViewHolder>(options) {
            @Override
            public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_row, parent, false);

                return new MyAdapter.ViewHolder(view);
            }




            @Override
            protected void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position, final ListItem model) {
                holder.setStartPiont(model.getStartingPoint());
                holder.setArrivePoint(model.getEndPoint());
                holder.setPlaces(model.getPlaces());
/*
                //boolean alreadyJoined = false;
                DatabaseReference databaseReferenceModel = MainActivity.databaseReferencePosts.child(model.getPostID());
                databaseReferenceModel.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID)
                                || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID)
                                || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID)
                                || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                        {
                            //Toast.makeText(getContext(),"Already Joined",Toast.LENGTH_SHORT).show();

                            holder.ActionButton.setText("CANCEL JOINING");
                            holder.ActionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selectedTripID = model.getPostID();
                                    selectedPlacesTrip = model.getPlaces();
                                    //Toast.makeText(getContext(),"kk   "+model.getPostID(),Toast.LENGTH_SHORT).show();
                                    cancelJoiningTrip();
                                }
                            });

                        }else {
                            if (MainActivity.isConductor){
                                holder.ActionButton.setText("TAKE");
                                holder.ActionButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        selectedTripID = model.getPostID();
                                        selectedPlacesTrip = model.getPlaces();
                                        takeTrip();

                                    }
                                });
                                return;
                            }else {
                                    holder.ActionButton.setText("JOIN");
                                    holder.ActionButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                    public void onClick(View v) {
                                    selectedTripID = model.getPostID();
                                    selectedPlacesTrip = model.getPlaces();
                                    Toast.makeText(getContext(),"kk   "+model.getPostID(),Toast.LENGTH_SHORT).show();
                                    joinTrip();
                                }
                                });
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
*/


                if (model.isTaken()){
                    holder.conductor.setText("TAKEN.");
                }else {
                    holder.conductor.setText("NOT taken.");
                }

                if (model.getSaturday()){
                    holder.setSatTxt(Color.RED);
                }

                if (model.getSunday()){
                    holder.setSunTxt(Color.RED);
                }

                if (model.getMonday()){
                    holder.setMonTxt(Color.RED);
                }

                if (model.getTuesday()){
                    holder.setTueTxt(Color.RED);
                }

                if (model.getWednesday()){
                    holder.setWedTxt(Color.RED);
                }

                if (model.getThursday()){
                    holder.setThuTxt(Color.RED);
                }

                if (model.getFriday()){
                    holder.setFriTxt(Color.RED);
                }


                holder.cardViewRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        selectedPlacesTrip = model.getPlaces();
                        selectedTripID = model.getPostID();
                        Fragment fra;
                        fra = new RidePostFragment();

                        getFragmentManager().beginTransaction().add(R.id.fragment_Post_container,
                                fra).commit();
                    }
                });
            }

        };
        mRecyclerView.setAdapter(adapterFire);
    }


    /*
    private void cancelJoiningTrip(){
        final DatabaseReference databaseReferencePC = MainActivity.databaseReferencePosts.child(selectedTripID);
        databaseReferencePC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining1").setValue("");
                    MainActivity.databaseReferencePosts.child(selectedTripID).child("places").setValue(selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining2").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining2").setValue("");
                    MainActivity.databaseReferencePosts.child(selectedTripID).child("places").setValue(selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining3").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining3").setValue("");
                    MainActivity.databaseReferencePosts.child(selectedTripID).child("places").setValue(selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining4").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining4").setValue("");
                    MainActivity.databaseReferencePosts.child(selectedTripID).child("places").setValue(selectedPlacesTrip-1);
                }
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void joinTrip() {


        DatabaseReference databaseReferencePjt = MainActivity.databaseReferencePosts.child(selectedTripID);
        databaseReferencePjt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID)
                    || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID)
                    || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID)
                    || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                {
                    //Toast.makeText(getContext(),"Already Joined",Toast.LENGTH_SHORT).show();
                    exist = true;
                    return;

                }



                    if (dataSnapshot.child("isFull").getValue(Boolean.class)){
                    Toast.makeText(getContext(),"FULL",Toast.LENGTH_SHORT).show();
                }else {
                    MainActivity.databaseReferencePosts.child(selectedTripID).child("places").setValue(selectedPlacesTrip+1);



                    if (dataSnapshot.child("accountIDJoining1").getValue().equals("")){
                        MainActivity.databaseReferencePosts.child(selectedTripID).child("accountIDJoining1")
                                .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                    }else{
                        if (dataSnapshot.child("accountIDJoining2").getValue().equals("")){
                            MainActivity.databaseReferencePosts.child(selectedTripID).child("accountIDJoining2")
                                    .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                        }else{
                            if (dataSnapshot.child("accountIDJoining3").getValue().equals("")){
                                MainActivity.databaseReferencePosts.child(selectedTripID).child("accountIDJoining3")
                                        .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                            }else{
                                if (dataSnapshot.child("accountIDJoining4").getValue().equals("")){
                                    MainActivity.databaseReferencePosts.child(selectedTripID).child("accountIDJoining4")
                                            .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                }
                            }
                        }
                    }



                    if      ((selectedPlacesTrip+1)>=4){
                        MainActivity.databaseReferencePosts.child(selectedTripID).child("isFull").setValue(true);
                    }else {
                        MainActivity.databaseReferencePosts.child(selectedTripID).child("isFull").setValue(false);
                    }
                }
                    return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void takeTrip() {
        //MainActivity.databaseReference.child("places").get;
        DatabaseReference databaseReferenceP = MainActivity.databaseReferencePosts.child(selectedTripID);
        databaseReferenceP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("isTaken").getValue(Boolean.class)){
                    Toast.makeText(getContext(),"already taken",Toast.LENGTH_SHORT).show();
                }else {

                    MainActivity.databaseReferencePosts.child(selectedTripID).child("isTaken").setValue(true);
                    MainActivity.databaseReferencePosts.child(selectedTripID).child("accountIDTakedIt").setValue(MainActivity.mAuth.getCurrentUser().getUid());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
*/
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapH = googleMap;

        //Do your stuff here
        LatLng somewhere = new  LatLng(0,10);
        mapH.addMarker(new MarkerOptions().position(somewhere).title("Marker Title").snippet("Marker Description"));



        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mapH.setMyLocationEnabled(true);

    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        //mMapView.onCreate(mapViewBundle);

        //mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        //mMapView.onSaveInstanceState(mapViewBundle);
    }



    @Override
    public void onResume() {
        super.onResume();
        //mMapView.onResume();

        //onBackPressed
        if (getView() == null){
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    //handle back button's click listener

                    //remove post fragment opened at the top of the home Fragment
                    Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_Post_container);
                    if (fragment != null){
                        getFragmentManager().beginTransaction().remove(fragment).commit();
                    }

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //mMapView.onStart();
        adapterFire.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mMapView.onStop();
        adapterFire.stopListening();


    }

    @Override
    public void onPause() {
        //mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        //mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    /*
    //expand
    public static void expandMapAnimation(){
        ViewWeightAnimationWrapper mapAnimationWrapper = new ViewWeightAnimationWrapper(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
                "weight",
                0,
                100);
        mapAnimation.setDuration(600);

        ViewWeightAnimationWrapper recyclerAnimationWrapper = new ViewWeightAnimationWrapper(mPostsContainer);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
                "weight",
                100,
                0);
        recyclerAnimation.setDuration(600);

        recyclerAnimation.start();
        mapAnimation.start();
    }

    public static void contractMapAnimation(){
        ViewWeightAnimationWrapper mapAnimationWrapper = new ViewWeightAnimationWrapper(mMapContainer);
        ObjectAnimator mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
                "weight",
                100,
                0);
        mapAnimation.setDuration(200);

        ViewWeightAnimationWrapper recyclerAnimationWrapper = new ViewWeightAnimationWrapper(mPostsContainer);
        ObjectAnimator recyclerAnimation = ObjectAnimator.ofFloat(recyclerAnimationWrapper,
                "weight",
                0,
                100);
        recyclerAnimation.setDuration(200);

        recyclerAnimation.start();
        mapAnimation.start();
    }
*/


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.btn_full_screen_map:{


                if(mMapLayoutState == MAP_LAYOUT_STATE_CONTRACTED){
                    mMapLayoutState = MAP_LAYOUT_STATE_EXPANDED;
                    expandMapAnimation();
                }
                else if(mMapLayoutState == MAP_LAYOUT_STATE_EXPANDED){
                    mMapLayoutState = MAP_LAYOUT_STATE_CONTRACTED;
                    contractMapAnimation();
                }

                break;
             */


        }

    }



    public static void showSomething(){
        //FragmentActivity activity = (FragmentActivity)

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.logoutBtnId:
//                logout();
//                break;
//        }
//    }

//    private void logout() {
//        mAuth.getInstance().signOut();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//        getActivity().finish();
//    }



    //floating
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);


        /*ListItem poste = new ListItem(data.getStringExtra(AddPostActivity.EXTRA_STARTpOINT),data.getStringExtra(AddPostActivity.EXTRA_ENDpOINT));
        mListItems.add(poste);
        mAdapter = new MyAdapter(this,mListItems);
        mRecyclerView.setAdapter(mAdapter);*/

        //hna 7at insert fi base de donnes
        Toast.makeText(getActivity(),"poste add in DB",Toast.LENGTH_SHORT).show();

        //Toast.makeText(getActivity(),"poste not add in DB",Toast.LENGTH_SHORT).show();




   /* //retriving data from addpostact and creating new item
    Bundle b = new Bundle();
    ListItem item = new ListItem(b.getString("EXTRA_STARTpOINT") , b.getString("EXTRA_ENDpOINT"));
    mListItems.add(item);

    mAdapter = new MyAdapter(this,mListItems);
    //mAdapter.notifyItemInserted(mListItems.size()-1);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    Toast.makeText(getActivity(), "add", Toast.LENGTH_SHORT).show();*/

}


}
