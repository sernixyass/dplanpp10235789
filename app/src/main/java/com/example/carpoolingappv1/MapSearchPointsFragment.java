package com.example.carpoolingappv1;

import android.Manifest;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.carpoolingappv1.util.Constants.MAPVIEW_BUNDLE_KEY;

public class MapSearchPointsFragment extends Fragment implements OnMapReadyCallback {

    //searching MAP
    public MapView mMapViewS;
    public static GoogleMap mapS;

    public static GeoApiContext mGeoApiContext = null;

    public static Polyline mPolyline;


    //
    public static HashMap<String,Marker> hashMapMarker = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map_search_points,container,false);


        //map search
        mMapViewS =  view.findViewById(R.id.mapSearch);
        initGoogleMap(savedInstanceState);


        hashMapMarker.clear();

        return view;
    }

    /*private void setupAutoCompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                sydney = place.getLatLng();
                mapFragment.getMapAsync(this);

            }

            @Override
            public void onError(Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapS = googleMap;

        //Do your stuff here
        //LatLng somewhere = new  LatLng(0,10);
        //mapS.addMarker(new MarkerOptions().position(somewhere).title("Marker Title").snippet("Marker Description"));

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
        mapS.setMyLocationEnabled(true);

    }

    public static void MoveCamera(LatLng latLng,float zoom,String title){
        mapS.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
         mapS.addMarker(options);

    }

    public static void MoveCameraAndMarker(String key, LatLng latLng, float zoom){
        mapS.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));



        //removing
        if (hashMapMarker.get(key) != null){
            Marker marker = hashMapMarker.get(key);
            marker.remove();
            hashMapMarker.remove(key);
            /*if (key == "start"){
                if (hashMapMarker.get("end") != null){
                    Marker markerHelping = hashMapMarker.get("end");
                    hashMapMarker.clear();
                    hashMapMarker.put("end",markerHelping);
                }
            }
            if (key == "end"){
                if (hashMapMarker.get("start") != null){
                    Marker markerHelping = hashMapMarker.get("start");
                    hashMapMarker.clear();
                    hashMapMarker.put("start",markerHelping);
                }
            }*/
        }

        //Toast.makeText(carpoolingappv1.getAppContext(),"bbbb",Toast.LENGTH_SHORT);

        MarkerOptions options = new MarkerOptions()
                .position(latLng);

        //adding
        Marker marker1 = mapS.addMarker(options);
        hashMapMarker.put(key,marker1);



        Marker markerS = hashMapMarker.get("start");
        Marker markerE = hashMapMarker.get("end");
        if (markerE != null && markerS != null){
//            Log.d("DURATION  ******       ","test");

            calculateDirections(markerS,markerE);
        }
        //marker.setPosition(latLng);
    }

    public void changeMarkerPos(MarkerOptions markerOptions,LatLng latLng){
        markerOptions.position(latLng);
    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapViewS.onCreate(mapViewBundle);

        mMapViewS.getMapAsync(this);


        if (mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_api_key))
                    .build();
        }
    }

    public static void calculateDirections(final Marker markerStart, final Marker markerEnd){
        //Log.d(TAG, "calculateDirections: calculating directions.");

        //Log.d("DURATION  ******       ","test 2");

        //clear old polylines for creating the new one .. and retrieve the markers
        mapS.clear();
        mapS.addMarker(new MarkerOptions().position(markerStart.getPosition()));
        mapS.addMarker(new MarkerOptions().position(markerEnd.getPosition()));


        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                markerEnd.getPosition().latitude,
                markerEnd.getPosition().longitude
        );
        final com.google.maps.model.LatLng startOrigine = new com.google.maps.model.LatLng(
                markerStart.getPosition().latitude,
                markerStart.getPosition().longitude
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
    }

    public static void addPolylinesToMap(final DirectionsResult result){
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
                    mPolyline = mapS.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    mPolyline.setColor(ContextCompat.getColor( carpoolingappv1.getAppContext() , R.color.blue));
                    mPolyline.setClickable(true);

                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapViewS.onSaveInstanceState(mapViewBundle);
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapViewS.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapViewS.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapViewS.onStop();
    }

    @Override
    public void onPause() {
        mMapViewS.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapViewS.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapViewS.onLowMemory();
    }

}
