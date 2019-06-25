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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.carpoolingappv1.util.Constants.MAPVIEW_BUNDLE_KEY;

public class RidePostFragment extends Fragment implements OnMapReadyCallback {

    //MAP
    public MapView mMapView;
    GoogleMap mapH;



    //post data
    TextView startPoint,endPoint,rideHour,rideDay,driverName,carModel,distance,checkPersoonesInRide;
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
        rideHour=view.findViewById(R.id.hour_ride);
        rideDay=view.findViewById(R.id.day_ride);
        driverName=view.findViewById(R.id.driver_full_name);
        carModel=view.findViewById(R.id.car_model_in_ride);
        distance=view.findViewById(R.id.distance);


        checkPersoonesInRide=view.findViewById(R.id.check_perssones_ride);
        checkPersoonesInRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the perssones in the ride
            }
        });



        final DatabaseReference databaseReferenceModel = MainActivity.databaseReferencePosts
                .child(HomeFragment.selectedTripID);

        if (!isOperating){
            databaseReferenceModel.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    startPoint.setText(dataSnapshot.child("startingPoint").getValue().toString());
                    endPoint.setText(dataSnapshot.child("endingPoint").getValue().toString());
                    rideHour.setText(dataSnapshot.child("hourTrip").getValue().toString());
                    //distance.serText(dataSnapshot.child("").getValue().toString());

                    //rideDay.setText(dataSnapshot.child());


                    //lkj

                    if (dataSnapshot.child("isTaken").getValue(Boolean.class)){

                        DatabaseReference mdataRef = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(dataSnapshot.child("accountIDTakedIt").getValue().toString());
                        mdataRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                carModel.setText(dataSnapshot1.child("carModel").getValue().toString());
                                driverName.setText(dataSnapshot1.child("fullName").getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        driverName.setText("No Driver available");
                        carModel.setText("No car");
                    }





                    int places = 0;
                    if (!dataSnapshot.child("accountIDJoining1").getValue().equals("")){
                        places++;
                    }
                    if (!dataSnapshot.child("accountIDJoining2").getValue().equals("")){
                        places++;
                    }
                    if (!dataSnapshot.child("accountIDJoining3").getValue().equals("")){
                        places++;
                    }
                    if (!dataSnapshot.child("accountIDJoining4").getValue().equals("")){
                        places++;
                    }
                    HomeFragment.selectedPlacesTrip=places;

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

                                    if (!dataSnapshot.child("accountIDJoining1").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining1").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining1").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining2").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining3").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining4").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ");
                                    }

                                    cancelJoiningTrip();
                                }
                            });


                        }else {
                            actionButton.setText("JOIN");
                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isOperating=true;
                                    //Toast.makeText(getContext(),"kk   "+model.getPostID(),Toast.LENGTH_SHORT).show();
                                    joinTrip();
                                    if (!dataSnapshot.child("accountIDJoining1").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining1").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining1").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining2").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining3").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining4").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ");
                                    }
                                }
                            });
                        }
                    }else {


                        //CONDUCTOR
                        if (!dataSnapshot.child("isTaken").getValue(Boolean.class)){
                            actionButton.setText("TAKE");
                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    takeTrip();

                                    if (!dataSnapshot.child("accountIDJoining1").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining1").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining2").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining3").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ");
                                    }
                                    if (!dataSnapshot.child("accountIDJoining4").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ");
                                    }
                                }
                            });
                        }else {
                            //Toast.makeText(getContext(),"rrr",Toast.LENGTH_SHORT).show();
                            if (dataSnapshot.child("accountIDTakedIt").getValue().equals(MainActivity.currentUserID)){

                                actionButton.setText("CANCEL");
                                actionButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (!dataSnapshot.child("accountIDJoining1").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining1").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ");
                                        }
                                        if (!dataSnapshot.child("accountIDJoining2").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ");
                                        }
                                        if (!dataSnapshot.child("accountIDJoining3").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ");
                                        }
                                        if (!dataSnapshot.child("accountIDJoining4").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ");
                                        }
                                        cancelTakingTrip();

                                    }
                                });

                            }else {
                                actionButton.setText("is TAKEN");
                            }
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
        databaseReferencePC.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places")
                            .setValue(HomeFragment.selectedPlacesTrip-1);
                }

                if (dataSnapshot.child("accountIDJoining3").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining3").setValue("");
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places")
                            .setValue(HomeFragment.selectedPlacesTrip-1);
                }

                if (dataSnapshot.child("accountIDJoining4").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining4").setValue("");
                    MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places")
                            .setValue(HomeFragment.selectedPlacesTrip-1);
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
        databaseReferencePjt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                {
                    //Toast.makeText(getContext(),"Already Joined",Toast.LENGTH_SHORT).show();

                    return;

                }else{

                    if (dataSnapshot.child("isFull").getValue(Boolean.class)){
                        Toast.makeText(getContext(),"FULL",Toast.LENGTH_SHORT).show();
                    }else {

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

                        MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("places")
                                .setValue((HomeFragment.selectedPlacesTrip+1));

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
        MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("accountIDTakedIt")
                .setValue(MainActivity.mAuth.getCurrentUser().getUid());
        MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("isTaken").setValue(true);
    }

    private void cancelTakingTrip() {
        //MainActivity.databaseReference.child("places").get;
        MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("accountIDTakedIt")
                .setValue("");
        MainActivity.databaseReferencePosts.child(HomeFragment.selectedTripID).child("isTaken").setValue(false);

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