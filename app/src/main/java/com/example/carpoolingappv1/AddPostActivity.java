package com.example.carpoolingappv1;


import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.MapView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddPostActivity extends AppCompatActivity  {

    public static String startingPointText;
    public static String endingPointText;
    private Integer placesText;

    public static LatLng endingPointLL;
    public static LatLng startingPointLL;

    private FloatingActionButton confermeAdd;

    public final float DEFAULT_ZOOM= 10.0f;


    //markers 2
    public MarkerOptions markerOStart;
    public MarkerOptions markerOEnd;

    public Marker markerStart;
    public Marker markerEnd;

    //m
    private LatLng sydney = new LatLng(-8.579892, 116.095239);
    //private MapFragment mapFragment;
    public MapView mMapViewS2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //startingPoint = findViewById(R.id.startPoint_autocomplete_fragment);
        //endingPoint = findViewById(R.id.endPoint_autocomplete_fragment);
        confermeAdd=findViewById(R.id.btn_conform_add);
        confermeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setingDate();
                //savePoste();
            }
        });


        //put the map
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_map_search,
                new MapSearchPointsFragment()).commit();


        init();


        setupAutoCompleteFragment();
    }

    private void setingDate() {
        Intent intent = new Intent(getApplicationContext(),CompeltPosting.class);
        startActivity(intent);
    }


    private void init(){
        /*startingPoint.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || actionId == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate(startingPoint);
                }
                return false;
            }
        });
        endingPoint.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate(endingPoint);
                }
                return false;
            }
        });*/
    }



    private void geoLocate(TextView searchingField){

        String searchingText = searchingField.getText().toString();

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchingText,1);
        }catch (IOException e){
        }

        if (list.size()>0){
            Address address = list.get(0);
            Log.d("geolocate loc: " ,address.toString());

            MapSearchPointsFragment.MoveCamera(new LatLng(address.getLatitude(),address.getLongitude()), DEFAULT_ZOOM,"Starting Point");

        }

    }
    private void geoLocateWithLL(String markerKey,LatLng searchingField){
        MapSearchPointsFragment.MoveCameraAndMarker(markerKey,searchingField, DEFAULT_ZOOM);

    }

    private void setupAutoCompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragmentStartPoint = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.startPoint_autocomplete_fragment);
        autocompleteFragmentStartPoint.setHint("Start; Search for cite, rue...");
        autocompleteFragmentStartPoint.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //sydney = place.getLatLng();
                //mapFragment.getMapAsync(MapsActivity.this);

                geoLocateWithLL("start",place.getLatLng());
                startingPointText = place.getName().toString();
                startingPointLL = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });

        PlaceAutocompleteFragment autocompleteFragmentEndPoint = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.endPoint_autocomplete_fragment);
        autocompleteFragmentEndPoint.setHint("End  ; Search for cite, rue...");

        autocompleteFragmentEndPoint.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //sydney = place.getLatLng();
                //mapFragment.getMapAsync(MapsActivity.this);

                geoLocateWithLL("end",place.getLatLng());
                endingPointText = place.getName().toString();
                endingPointLL = place.getLatLng();


                //MapSearchPointsFragment.calculateDirections(markerStart,markerEnd);

            }

            @Override
            public void onError(Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });
    }

    private void savePoste() {
        String startP = startingPointText;
        String endP = endingPointText;
        Integer places = placesText;

        //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        if (startP.trim().isEmpty() || endP.trim().isEmpty()) {
            Toast.makeText(this, "please insert a startingPoint and endingPoint", Toast.LENGTH_SHORT).show();
            return;
            //this return without executing any code below
        }

        //save to firebase table .............................

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("posts").push();
        Map<String, Object> map = new HashMap<>();
        map.put("id", databaseReference.getKey());
        map.put("startingPoint", startP);
        map.put("endingPoint", endP);
        map.put("places", places);


        databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    finish();

                }else {


                    return;
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.poste_menu, menu);
        return true;
    }
}

