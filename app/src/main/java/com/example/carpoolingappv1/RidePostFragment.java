package com.example.carpoolingappv1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.example.carpoolingappv1.util.Constants.MAPVIEW_BUNDLE_KEY;

public class RidePostFragment extends Fragment implements OnMapReadyCallback {

    //MAP
    public MapView mMapView;
    GoogleMap mapH;

    //post data
    TextView startPoint,endPoint;
    Button actionButton;

    public static boolean isOperating = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ride_post,container,false);

        mMapView = view.findViewById(R.id.mapPost);
        initGoogleMap(savedInstanceState);

        startPoint = view.findViewById(R.id.fStartPoint);
        endPoint = view.findViewById(R.id.fEndPoint);
        actionButton = view.findViewById(R.id.fActionBtn);


        DatabaseReference databaseReferenceModelC = MainActivity.databaseReferencePosts
                .child(HomeFragment.selectedTripID);

        if (!isOperating){
            databaseReferenceModelC.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    startPoint.setText(dataSnapshot.child("startingPoint").getValue().toString());
                    endPoint.setText(dataSnapshot.child("endingPoint").getValue().toString());


                    if (!MainActivity.isConductor){
                        //A C T I O N   B U T T O N
                        if (dataSnapshot.child("accountIDJoining1").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid()))
                        {
                            //Toast.makeText(getContext(),"Already Joined",Toast.LENGTH_SHORT).show();
                            actionButton.setText("CANCEL JOINING");
                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isOperating=true;

                                    //Toast.makeText(getContext(),"kk   "+model.getPostID(),Toast.LENGTH_SHORT).show();
                                    cancelJoiningTrip();
                                }
                            });


                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



        DatabaseReference databaseReferenceModelJ = MainActivity.databaseReferencePosts
                .child(HomeFragment.selectedTripID);

        if (!isOperating){
            databaseReferenceModelJ.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //fill data
                    startPoint.setText(dataSnapshot.child("startingPoint").getValue().toString());
                    endPoint.setText(dataSnapshot.child("endingPoint").getValue().toString());


                    if (!MainActivity.isConductor){
                        //A C T I O N   B U T T O N
                        if (dataSnapshot.child("accountIDJoining1").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid()))
                        {
                            //Toast.makeText(getContext(),"Already Joined",Toast.LENGTH_SHORT).show();

                            return;
                        }else {
                            actionButton.setText("JOIN");
                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isOperating=true;
                                    //Toast.makeText(getContext(),"kk   "+model.getPostID(),Toast.LENGTH_SHORT).show();
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

        }




        return view;
    }




    private void cancelJoiningTrip(){
        final DatabaseReference databaseReferencePC = MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID);
        databaseReferencePC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining1").setValue("");
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places")
                            .setValue(HomeFragment.selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining2").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining2").setValue("");
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places").setValue(HomeFragment.selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining3").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining3").setValue("");
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places").setValue(HomeFragment.selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining4").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining4").setValue("");
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places").setValue(HomeFragment.selectedPlacesTrip-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        isOperating=false;
    }

    private void joinTrip() {

        DatabaseReference databaseReferencePjt = MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID);
        databaseReferencePjt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean exist = true;

                if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                {
                    //Toast.makeText(getContext(),"Already Joined",Toast.LENGTH_SHORT).show();
                    exist = true;

                    return;

                }else{

                    if (dataSnapshot.child("isFull").getValue(Boolean.class)){
                        Toast.makeText(getContext(),"FULL",Toast.LENGTH_SHORT).show();
                    }else {

                        MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places")
                                .setValue((HomeFragment.selectedPlacesTrip+1));



                        if (dataSnapshot.child("accountIDJoining1").getValue().equals("")){
                            MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("accountIDJoining1")
                                    .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                        }else{
                            if (dataSnapshot.child("accountIDJoining2").getValue().equals("")){
                                MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("accountIDJoining2")
                                        .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                            }else{
                                if (dataSnapshot.child("accountIDJoining3").getValue().equals("")){
                                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("accountIDJoining3")
                                            .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                }else{
                                    if (dataSnapshot.child("accountIDJoining4").getValue().equals("")){
                                        MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("accountIDJoining4")
                                                .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                    }
                                }
                            }
                        }



                        if      ((HomeFragment.selectedPlacesTrip+1)>=4){
                            MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("isFull").setValue(true);
                        }else {
                            MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("isFull").setValue(false);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        isOperating=false;
    }

    private void takeTrip() {
        //MainActivity.databaseReference.child("places").get;
        DatabaseReference databaseReferenceP = MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID);
        databaseReferenceP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("isTaken").getValue(Boolean.class)){
                    Toast.makeText(getContext(),"already taken",Toast.LENGTH_SHORT).show();
                }else {

                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("isTaken").setValue(true);
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("accountIDTakedIt").setValue(MainActivity.mAuth.getCurrentUser().getUid());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



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

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}