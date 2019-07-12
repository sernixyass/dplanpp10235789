package com.example.carpoolingappv1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.carpoolingappv1.util.Constants.MAPVIEW_BUNDLE_KEY;

public class RidePostFragment extends Fragment implements OnMapReadyCallback {

    //MAP
    public MapView mMapView;
    GoogleMap mapH;

    String join1AccountID,join2AccountID,join3AccountID,join4AccountID,join5AccountID,join6AccountID,join7AccountID;

    public Polyline mPolyline;

    //post data
    TextView startPoint,endPoint,rideHour,rideDate,rideDay,driverName,carModel,distance,estimatedTime,price;
    Button actionButton;
    TextView saturday,sunday,monday,tuesday,wednesday,thursday,friday;
    ImageButton driverIcon,passengerIcon1,passengerIcon2,passengerIcon3,passengerIcon4,passengerIcon5,passengerIcon6,passengerIcon7;

    public static boolean isOperating = false;

    public LatLng tripPos,tripDestPos;
    MarkerOptions optionsPos,optionDestPos;
    public static GeoApiContext mGeoApiContext = null;

    public static String startTrip,destinationTrip;

    public Integer tripPlaces;
    public Integer tripTotalPlaces;



    Button messagebtn;

    Boolean isMapReady = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_ride_post,container,false);

        mMapView = view.findViewById(R.id.mapPost);
        initGoogleMap(savedInstanceState);

        startPoint = view.findViewById(R.id.fStartPoint);
        endPoint = view.findViewById(R.id.fEndPoint);
        actionButton = view.findViewById(R.id.fActionBtn);

        rideHour=view.findViewById(R.id.hour_ride);
        rideDate=view.findViewById(R.id.date_ridePost);
        rideDay=view.findViewById(R.id.day_ride);
        driverName=view.findViewById(R.id.driver_full_name);
        carModel=view.findViewById(R.id.car_model_in_ride);
        distance=view.findViewById(R.id.distance);
        estimatedTime = view.findViewById(R.id.estimatedTime);

        saturday = view.findViewById(R.id.satIDf);
        sunday = view.findViewById(R.id.sunIDf);
        monday = view.findViewById(R.id.monIDf);
        tuesday = view.findViewById(R.id.tueIDf);
        wednesday = view.findViewById(R.id.wedIDf);
        thursday = view.findViewById(R.id.thuIDf);
        friday = view.findViewById(R.id.friIDf);

        driverIcon = view.findViewById(R.id.driver_icon);

        passengerIcon1 = view.findViewById(R.id.passenger_joining1);
        passengerIcon2 = view.findViewById(R.id.passenger_joining2);
        passengerIcon3 = view.findViewById(R.id.passenger_joining3);
        passengerIcon4 = view.findViewById(R.id.passenger_joining4);
        passengerIcon5 = view.findViewById(R.id.passenger_joining5);
        passengerIcon6 = view.findViewById(R.id.passenger_joining6);
        passengerIcon7 = view.findViewById(R.id.passenger_joining7);

        price = view.findViewById(R.id.ride_price);

        /*checkPersoonesInRide=view.findViewById(R.id.check_perssones_ride);
        checkPersoonesInRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the perssones in the ride
            }
        });*/


        messagebtn = view.findViewById(R.id.fMessageBtn);
        view.findViewById(R.id.fMessageBtn).setVisibility(View.GONE);


        DatabaseReference databaseReferenceStart =
                MainActivity.databaseReferencePosts
                .child(MainActivity.selectedTripID)
                .child("tripPosition");
        databaseReferenceStart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tripPos = new LatLng( dataSnapshot.child("latitude").getValue(double.class),
                        dataSnapshot.child("longitude").getValue(double.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReferenceEnd = MainActivity.databaseReferencePosts
                .child(MainActivity.selectedTripID).child("tripDestinationPosition");
        databaseReferenceEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tripDestPos = new LatLng( dataSnapshot.child("latitude").getValue(double.class),
                        dataSnapshot.child("longitude").getValue(double.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReferenceModel = MainActivity.databaseReferencePosts
                .child(MainActivity.selectedTripID);

        //conductor
        driverIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ProfileConductorPopupActivity.class));
            }
        });



        // 4 passengers
        view.findViewById(R.id.passenger_joining1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedJoinAccountID=join1AccountID;
                startActivity(new Intent(getContext(),ProfilePassengerPopupActivity.class));
            }
        });
        view.findViewById(R.id.passenger_joining2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedJoinAccountID=join2AccountID;
                startActivity(new Intent(getContext(),ProfilePassengerPopupActivity.class));
            }
        });
        view.findViewById(R.id.passenger_joining3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedJoinAccountID=join3AccountID;
                startActivity(new Intent(getContext(),ProfilePassengerPopupActivity.class));
            }
        });
        view.findViewById(R.id.passenger_joining4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedJoinAccountID=join4AccountID;
                startActivity(new Intent(getContext(),ProfilePassengerPopupActivity.class));
            }
        });
        view.findViewById(R.id.passenger_joining5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedJoinAccountID=join5AccountID;
                startActivity(new Intent(getContext(),ProfilePassengerPopupActivity.class));
            }
        });
        view.findViewById(R.id.passenger_joining6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedJoinAccountID=join6AccountID;
                startActivity(new Intent(getContext(),ProfilePassengerPopupActivity.class));
            }
        });
        view.findViewById(R.id.passenger_joining7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedJoinAccountID=join7AccountID;
                startActivity(new Intent(getContext(),ProfilePassengerPopupActivity.class));
            }
        });

        if (!isOperating){
            databaseReferenceModel.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    startPoint.setText(dataSnapshot.child("startingPoint").getValue().toString());
                    endPoint.setText(dataSnapshot.child("endingPoint").getValue().toString());
                    rideHour.setText(dataSnapshot.child("hourTrip").getValue().toString());
                    rideDate.setText(dataSnapshot.child("tripDate").getValue().toString());
                    distance.setText(dataSnapshot.child("distance").getValue().toString());
                    estimatedTime.setText(dataSnapshot.child("estimatedTime").getValue().toString());

                    price.setText(dataSnapshot.child("price").getValue().toString() + " DA");

                    startTrip = dataSnapshot.child("startingPoint").getValue().toString();
                    destinationTrip = dataSnapshot.child("endingPoint").getValue().toString();

                    tripPlaces = dataSnapshot.child("places").getValue(Integer.class);
                    tripTotalPlaces = dataSnapshot.child("totalPlaces").getValue(Integer.class);
                    //LATLNG
                    //tripPos = dataSnapshot.child("tripPosition").getValue(LatLng.class);
                    //tripDestPos = dataSnapshot.child("tripDestinationPosition").getValue(LatLng.class);

                    if (dataSnapshot.child("weeklyTrip").getValue(boolean.class)){
                        view.findViewById(R.id.week_ridePost).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.date_ridePost).setVisibility(View.GONE);
                    }else {
                        view.findViewById(R.id.week_ridePost).setVisibility(View.GONE);
                        view.findViewById(R.id.date_ridePost).setVisibility(View.VISIBLE);
                    }

                    if (isMapReady){
                        calculateDirections(tripPos,tripDestPos);
                    }

                    //ICONS
                    //selectedDriverAccountID = dataSnapshot.child("accountIDTakedIt").getValue().toString();

                    //ICON PASSENGER 1
                    if (!dataSnapshot.child("accountIDJoining1").getValue().toString().equals("")){
                        passengerIcon1.setVisibility(view.VISIBLE);
                        join1AccountID = dataSnapshot.child("accountIDJoining1").getValue().toString();

                        DatabaseReference databaseReferenceUJ1 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(join1AccountID);
                        databaseReferenceUJ1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotUJ1) {
                                if (!Objects.equals(dataSnapshotUJ1.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getAppContext()!=null){
                                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotUJ1.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(passengerIcon1);

                                    }
                                }
                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        passengerIcon1.setVisibility(view.GONE);
                    }

                    if (!dataSnapshot.child("accountIDJoining2").getValue().toString().equals("")){
                        passengerIcon2.setVisibility(view.VISIBLE);
                        join2AccountID = dataSnapshot.child("accountIDJoining2").getValue().toString();

                        DatabaseReference databaseReferenceUJ2 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(join2AccountID);
                        databaseReferenceUJ2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotUJ2) {
                                if (!Objects.equals(dataSnapshotUJ2.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getAppContext()!=null){
                                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotUJ2.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(passengerIcon2);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        passengerIcon2.setVisibility(view.GONE);
                    }

                    if (!dataSnapshot.child("accountIDJoining3").getValue().toString().equals("")){
                        passengerIcon3.setVisibility(view.VISIBLE);
                        join3AccountID = dataSnapshot.child("accountIDJoining3").getValue().toString();

                        DatabaseReference databaseReferenceUJ3 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(join3AccountID);
                        databaseReferenceUJ3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotUJ3) {
                                if (!Objects.equals(dataSnapshotUJ3.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getAppContext()!=null){
                                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotUJ3.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(passengerIcon3);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        passengerIcon3.setVisibility(view.GONE);
                    }

                    if (!dataSnapshot.child("accountIDJoining4").getValue().toString().equals("")){
                        passengerIcon4.setVisibility(view.VISIBLE);
                        join4AccountID = dataSnapshot.child("accountIDJoining4").getValue().toString();

                        DatabaseReference databaseReferenceUJ4 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(join4AccountID);
                        databaseReferenceUJ4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotUJ4) {
                                if (!Objects.equals(dataSnapshotUJ4.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getAppContext()!=null){
                                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotUJ4.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(passengerIcon4);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        passengerIcon4.setVisibility(view.GONE);
                    }

                    if (!dataSnapshot.child("accountIDJoining5").getValue().toString().equals("")){
                        passengerIcon5.setVisibility(view.VISIBLE);
                        join5AccountID = dataSnapshot.child("accountIDJoining5").getValue().toString();

                        DatabaseReference databaseReferenceUJ4 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(join5AccountID);
                        databaseReferenceUJ4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotUJ4) {
                                if (!Objects.equals(dataSnapshotUJ4.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getAppContext()!=null){
                                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotUJ4.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(passengerIcon5);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        passengerIcon5.setVisibility(view.GONE);
                    }

                    if (!dataSnapshot.child("accountIDJoining6").getValue().toString().equals("")){
                        passengerIcon6.setVisibility(view.VISIBLE);
                        join6AccountID = dataSnapshot.child("accountIDJoining6").getValue().toString();

                        DatabaseReference databaseReferenceUJ4 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(join4AccountID);
                        databaseReferenceUJ4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotUJ4) {
                                if (!Objects.equals(dataSnapshotUJ4.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getAppContext()!=null){
                                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotUJ4.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(passengerIcon6);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        passengerIcon6.setVisibility(view.GONE);
                    }

                    if (!dataSnapshot.child("accountIDJoining7").getValue().toString().equals("")){
                        passengerIcon7.setVisibility(view.VISIBLE);
                        join7AccountID = dataSnapshot.child("accountIDJoining7").getValue().toString();

                        DatabaseReference databaseReferenceUJ4 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(join7AccountID);
                        databaseReferenceUJ4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotUJ4) {
                                if (!Objects.equals(dataSnapshotUJ4.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getAppContext()!=null){
                                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotUJ4.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(passengerIcon7);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        passengerIcon7.setVisibility(view.GONE);
                    }

                    //rideDay.setText(dataSnapshot.child());

                    //WEEK days
                    if (dataSnapshot.child("saturday").getValue(Boolean.class)){
                        saturday.setTextColor(getResources().getColor(R.color.colorPrimary));

                    }
                    if (dataSnapshot.child("sunday").getValue(Boolean.class)){
                        sunday.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    if (dataSnapshot.child("monday").getValue(Boolean.class)){
                        monday.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    if (dataSnapshot.child("tuesday").getValue(Boolean.class)){
                        tuesday.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    if (dataSnapshot.child("wednesday").getValue(Boolean.class)){
                        wednesday.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    if (dataSnapshot.child("thursday").getValue(Boolean.class)){
                        thursday.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    if (dataSnapshot.child("friday").getValue(Boolean.class)){
                        friday.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }


                    //lkj

                    if (dataSnapshot.child("isTaken").getValue(Boolean.class)){

                        MainActivity.selectedDriverAccountID = dataSnapshot.child("accountIDTakedIt").getValue().toString();

                        DatabaseReference mdataRef = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(dataSnapshot.child("accountIDTakedIt").getValue().toString());
                        mdataRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                carModel.setText(dataSnapshot1.child("carModel").getValue().toString());
                                driverName.setText(dataSnapshot1.child("fullName").getValue().toString());


                                if(dataSnapshot.child("accountIDTakedIt").getValue().toString().equals(MainActivity.currentUserID)){
                                    price.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intentPrice =  new Intent(getContext(), PriceSetterActivity.class);
                                            startActivity(intentPrice);
                                        }
                                    });
                                }else {
                                    price.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    });
                                }

                                if (!Objects.equals(dataSnapshot1.child("profilePic").getValue(), "") ){
                                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                                    if (carpoolingappv1.getApplication().getApplicationContext()!=null){
                                        Glide.with(carpoolingappv1.getApplication().getApplicationContext()).load(dataSnapshot1.child("profilePic").getValue())
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(driverIcon);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else {
                        driverName.setText("No Driver available");
                        carModel.setText("No car");
                        if (carpoolingappv1.getApplication().getApplicationContext()!=null){
                            driverIcon.setImageDrawable(getResources().getDrawable(R.drawable.profile_circular_border_imageview));
                        }

                        MainActivity.selectedDriverAccountID = "";
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
                    if (!dataSnapshot.child("accountIDJoining5").getValue().equals("")){
                        places++;
                    }
                    if (!dataSnapshot.child("accountIDJoining6").getValue().equals("")){
                        places++;
                    }
                    if (!dataSnapshot.child("accountIDJoining7").getValue().equals("")){
                        places++;
                    }
                    MainActivity.selectedPlacesTrip=places;

                    if      ((tripTotalPlaces)<=tripPlaces){
                        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("isFull").setValue(true);
                    }else {
                        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("isFull").setValue(false);
                    }


                    if (!MainActivity.isConductor){
                        //A C T I O N   B U T T O N
                        if (dataSnapshot.child("accountIDJoining1").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining5").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining6").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid())
                                || dataSnapshot.child("accountIDJoining7").getValue().equals(MainActivity.mAuth.getCurrentUser().getUid()))
                        {
                            //Toast.makeText(getContext(),"Already Joined",Toast.LENGTH_SHORT).show();
                            actionButton.setText("CANCEL JOINING");
                            view.findViewById(R.id.fMessageBtn).setVisibility(View.VISIBLE);
                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isOperating=true;

                                    if (!dataSnapshot.child("accountIDJoining1").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining1").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining1").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining2").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining3").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining4").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining5").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining5").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining5").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining6").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining6").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining6").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining7").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining7").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining7").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit this trip ",
                                                MainActivity.iconSender);
                                    }
                                    //conductor
                                    if (!dataSnapshot.child("accountIDTakedIt").getValue().equals("") )
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDTakedIt").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "-" + MainActivity.currentUserFullName +"- exit Your trip ",
                                                MainActivity.iconSender);
                                    }

                                    cancelJoiningTrip();
                                }
                            });


                        }else {
                            actionButton.setText("JOIN");
                            view.findViewById(R.id.fMessageBtn).setVisibility(View.GONE);
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
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining2").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining3").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining4").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining5").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining5").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining5").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining6").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining6").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining6").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining7").getValue().equals("") &&
                                            !dataSnapshot.child("accountIDJoining7").getValue().equals(MainActivity.currentUserID))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining7").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join with you this trip ",
                                                MainActivity.iconSender);
                                    }
                                    //conductor
                                    if (!dataSnapshot.child("accountIDTakedIt").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDTakedIt").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Passenger -" + MainActivity.currentUserFullName +"- Join Your trip ",
                                                MainActivity.iconSender);
                                    }
                                }
                            });
                        }
                    }else {


                        //CONDUCTOR
                        if (!dataSnapshot.child("isTaken").getValue(Boolean.class)){
                            actionButton.setText("TAKE");
                            view.findViewById(R.id.fMessageBtn).setVisibility(View.GONE);
                            actionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    takeTrip();

                                    if (!dataSnapshot.child("accountIDJoining1").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining1").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining2").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip "
                                        ,MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining3").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip "
                                        ,MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining4").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining5").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining5").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining6").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining6").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ",
                                                MainActivity.iconSender);
                                    }
                                    if (!dataSnapshot.child("accountIDJoining7").getValue().equals(""))
                                    {
                                        MainActivity.sendNotification(dataSnapshot.child("accountIDJoining7").getValue().toString(),
                                                dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                "New Conductor -" + MainActivity.currentUserFullName +"- Take this trip ",
                                                MainActivity.iconSender);
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
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ",
                                                    MainActivity.iconSender);
                                        }
                                        if (!dataSnapshot.child("accountIDJoining2").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining2").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ",
                                                    MainActivity.iconSender);
                                        }
                                        if (!dataSnapshot.child("accountIDJoining3").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining3").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ",
                                                    MainActivity.iconSender);
                                        }
                                        if (!dataSnapshot.child("accountIDJoining4").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining4").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ",
                                                    MainActivity.iconSender);
                                        }
                                        if (!dataSnapshot.child("accountIDJoining5").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining5").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ",
                                                    MainActivity.iconSender);
                                        }
                                        if (!dataSnapshot.child("accountIDJoining6").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining6").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ",
                                                    MainActivity.iconSender);
                                        }
                                        if (!dataSnapshot.child("accountIDJoining7").getValue().equals(""))
                                        {
                                            MainActivity.sendNotification(dataSnapshot.child("accountIDJoining7").getValue().toString(),
                                                    dataSnapshot.child("startingPoint").getValue()+" TO "+dataSnapshot.child("endingPoint").getValue(),
                                                    "-" + MainActivity.currentUserFullName +"- Take this trip ",
                                                    MainActivity.iconSender);
                                        }
                                        cancelTakingTrip();

                                    }
                                });

                                view.findViewById(R.id.fMessageBtn).setVisibility(View.VISIBLE);
                            }else {
                                actionButton.setText("is TAKEN");
                                view.findViewById(R.id.fMessageBtn).setVisibility(View.GONE);
                            }
                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        view.findViewById(R.id.reportBtnP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(getContext(), ReportPopupActivity.class);
                intent.putExtra("whichReport","post");
                startActivity(intent);            }
        });






        view.findViewById(R.id.fMessageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fra2;
                fra2 = new ChatFragment();
                getFragmentManager().beginTransaction().add(R.id.fragment_Post_container,
                        fra2).commit();
            }
        });


        return view;
    }

    public void calculateDirections(final LatLng markerStart, final LatLng markerEnd){
        //Log.d(TAG, "calculateDirections: calculating directions.");

        //Log.d("DURATION  ******       ","test 2");

        //clear old polylines for creating the new one .. and retrieve the markers
        //mapH.clear();

        mapH.clear();
        mapH.addMarker(new MarkerOptions().position(new LatLng(markerStart.latitude,markerStart.longitude)));
        mapH.addMarker(new MarkerOptions().position(new LatLng(markerEnd.latitude,markerEnd.longitude)));


        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                tripDestPos.latitude,
                tripDestPos.longitude
        );
        final com.google.maps.model.LatLng startOrigine = new com.google.maps.model.LatLng(
                tripPos.latitude,
                tripPos.longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(true);
        directions.origin(startOrigine);
        //Log.d("DURATION  ******       ","test 3");
        //Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                //Log.d("DURATION  ******       ","test 4");
                //Log.d(TAG, "calculateDirections: routes: " + result.routes[0].toString());
                //Log.d(TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration);
                //Log.d("DURATION  ******       ",result.routes[0].legs[0].duration.toString());

                //distance.setText(result.routes[0].legs[0].distance.toString());
                //estimatedTime.setText(result.routes[0].legs[0].duration.toString());

                //Log.d(TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance);
                //Log.d(TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());

                //mPolyline.remove();
                //mPolyline = null;
                addPolylinesToMap(result);
            }



            @Override
            public void onFailure(Throwable e) {
                //Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage() );

                //Log.d("DURATION  ******       ","test 5");
                Log.d("ERROR    *** ",e.getMessage());
            }
        });


        mapH.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(tripPos.latitude,tripPos.longitude),
                10.0f));

    }

    public void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                //Log.d(TAG, "run: result routes: " + result.routes.length);

                for(DirectionsRoute route: result.routes){
                    //Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    mPolyline = mapH.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    mPolyline.setColor(ContextCompat.getColor( carpoolingappv1.getAppContext() , R.color.blue));
                    //mPolyline.setClickable(false);
                }
            }
        });
    }

    private void cancelJoiningTrip(){
        final DatabaseReference databaseReferencePC = MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID);
        databaseReferencePC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining1").setValue("");
                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                            .setValue(MainActivity.selectedPlacesTrip-1);
                }

                if (dataSnapshot.child("accountIDJoining2").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining2").setValue("");
                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                            .setValue(MainActivity.selectedPlacesTrip-1);
                }

                if (dataSnapshot.child("accountIDJoining3").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining3").setValue("");
                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                            .setValue(MainActivity.selectedPlacesTrip-1);
                }

                if (dataSnapshot.child("accountIDJoining4").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining4").setValue("");
                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                            .setValue(MainActivity.selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining5").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining5").setValue("");
                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                            .setValue(MainActivity.selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining6").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining6").setValue("");
                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                            .setValue(MainActivity.selectedPlacesTrip-1);
                }
                if (dataSnapshot.child("accountIDJoining7").getValue().equals( MainActivity.currentUserID))
                {
                    databaseReferencePC.child("accountIDJoining7").setValue("");
                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                            .setValue(MainActivity.selectedPlacesTrip-1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if      ((tripTotalPlaces)<=tripPlaces){
            MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("isFull").setValue(true);
        }else {
            MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("isFull").setValue(false);
        }

        isOperating=false;
    }

    private void joinTrip() {
        DatabaseReference databaseReferencePjt = MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID);
        databaseReferencePjt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("accountIDJoining1").getValue().equals( MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining2").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining3").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining4").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining5").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining6").getValue().equals(MainActivity.currentUserID)
                        || dataSnapshot.child("accountIDJoining7").getValue().equals(MainActivity.currentUserID))
                {
                    return;

                }else{
                    if (dataSnapshot.child("isFull").getValue(Boolean.class)){
                        Toast.makeText(getContext(),"FULL",Toast.LENGTH_SHORT).show();
                    }else {
                        if (dataSnapshot.child("accountIDJoining1").getValue().equals("")){
                            MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDJoining1")
                                    .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                        }else{
                            if (dataSnapshot.child("accountIDJoining2").getValue().equals("")){
                                MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDJoining2")
                                        .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                            }else{
                                if (dataSnapshot.child("accountIDJoining3").getValue().equals("")){
                                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDJoining3")
                                            .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                }else{
                                    if (dataSnapshot.child("accountIDJoining4").getValue().equals("")){
                                        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDJoining4")
                                                .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                    }else {
                                        if (dataSnapshot.child("accountIDJoining5").getValue().equals("")){
                                            MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDJoining5")
                                                    .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                        }else {
                                            if (dataSnapshot.child("accountIDJoining6").getValue().equals("")){
                                                MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDJoining6")
                                                        .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                            }else {
                                                if (dataSnapshot.child("accountIDJoining7").getValue().equals("")){
                                                    MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDJoining7")
                                                            .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("places")
                                .setValue((MainActivity.selectedPlacesTrip+1));

                        if      ((tripTotalPlaces)<=tripPlaces){
                            MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("isFull").setValue(true);
                        }else {
                            MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("isFull").setValue(false);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("please confirm taking this trip ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDTakedIt")
                                .setValue(MainActivity.mAuth.getCurrentUser().getUid());
                        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID)
                                .child("isTaken").setValue(true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Confirmation")
                .show();
    }

    private void cancelTakingTrip() {
        //MainActivity.databaseReference.child("places").get;
        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("isTaken").setValue(false);
        MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("accountIDTakedIt")
                .setValue("");

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapH = googleMap;



        //Do your stuff here
        //LatLng somewhere = new  LatLng(0,10);
        //mapH.addMarker(new MarkerOptions().position(somewhere).title("Marker Title").snippet("Marker Description"));

        isMapReady = true;

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

        if (mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_api_key))
                    .build();
        }



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

        if (mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_api_key))
                    .build();
        }
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
        MainActivity.ridePostIsDisplaying = true;

    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
        MainActivity.ridePostIsDisplaying = true;
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
        MainActivity.ridePostIsDisplaying = true;
    }



}