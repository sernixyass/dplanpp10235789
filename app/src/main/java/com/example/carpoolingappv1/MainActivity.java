package com.example.carpoolingappv1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.carpoolingappv1.util.Constants.ERROR_DIALOG_REQUEST;
import static com.example.carpoolingappv1.util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.carpoolingappv1.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {

    public static FirebaseAuth mAuth;

    public static DatabaseReference databaseReference;
    public static DatabaseReference databaseReferencePosts;
    public static String userID;
    public static Boolean isConductor = false;
    public static Boolean isTaken = false;
    public static String currentUserID;
    public static String currentUserFullName;


    public static String selectedDriverAccountID = "";
    public static String selectedJoinAccountID = "";
    public static String iconSender = "";


    private boolean mLocationPermissionGranted = false;


    public Fragment fragment1;
    public Fragment fragment2;
    public Fragment fragment3;
    public Fragment fragment4;
    public Fragment activeFragment;

    public boolean startOperating = false;

    boolean doubleBackExitPressed = false;

    public static boolean ridePostIsDisplaying = false;
    public static boolean messagePostIsDisplaying = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //for getting witch activity to run first
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null || !mAuth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            //** login activity first
            return;
        }
        //** or main activity directly
        setContentView(R.layout.activity_main);

        //
        currentUserID = mAuth.getCurrentUser().getUid();
        userID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        databaseReferencePosts = FirebaseDatabase.getInstance().getReference().child("posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isConductor = dataSnapshot.child("isConductor").getValue(Boolean.class);
                currentUserFullName = dataSnapshot.child("fullName").getValue().toString();
                iconSender = dataSnapshot.child("profilePic").getValue().toString();
                startOperating = true;
                startOperatingFra();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //btmnav
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //default fragment to start at the beginning

        if (startOperating) {
        }
    }


    public void startOperatingFra() {
        fragment1 = new HomeFragment();
        fragment2 = new SearchFragment();
        fragment3 = new NotificationFragment();

        if (isConductor) {
            fragment4 = new ProfileConductorFragment();
        } else {
            fragment4 = new ProfilePassengerFragment();
        }


        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment1, "1").hide(fragment1).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();

        getSupportFragmentManager().beginTransaction().show(fragment1).commit();
        activeFragment = fragment1;
    }


    //for gps permission
    //copied
    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    //for helping the user to enable the gps*
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        //is user active gps permission*
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    //is gps enabled*
    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //the result*
            //getChatrooms();
        } else {
            //asking if it is okey to use permission*
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //is device able to use google services*
    public boolean isServicesOK() {
        //Log.d("isServicesOK: checking google services version");


        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            //Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            //Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            //help user to installe it*
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    //the result*
                    //getChatrooms();
                } else {
                    getLocationPermission();
                }
            }
        }

    }
    //end the copied area


    public static void sendNotification(String userReciver, String title, String message, String iconeUserID) {
        DatabaseReference databaseReferenceNot = FirebaseDatabase.getInstance().getReference().child("Users").child(userReciver).child("notification").push();
        Map<String, Object> notifMap = new HashMap<>();
        notifMap.put("title", title);
        notifMap.put("message", message);
        notifMap.put("iconUserID", iconeUserID);
        databaseReferenceNot.setValue(notifMap);
    }

    public static void sendPostReport(String userSender, String postID, String message) {
        DatabaseReference databaseReferenceNot = FirebaseDatabase.getInstance().getReference().child("admin").child("reports").child("posts").push();
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("userSender", userSender);
        reportMap.put("message", message);
        reportMap.put("postID", postID);
        databaseReferenceNot.setValue(reportMap);
    }

    public static void sendUserReport(String userSender, String userID, String message) {
        DatabaseReference databaseReferenceNot = FirebaseDatabase.getInstance().getReference().child("admin").child("reports").child("users").push();
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("userSender", userSender);
        reportMap.put("message", message);
        reportMap.put("userID", userID);
        databaseReferenceNot.setValue(reportMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //the result*
        //getgetChatrooms();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                //the result*
                //getChatrooms();
            } else {
                getLocationPermission();
            }

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    //Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(fragment1).commit();
                            activeFragment = fragment1;
                            break;
                        case R.id.nav_chat:
                            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(fragment2).commit();
                            activeFragment = fragment2;
                            break;
                        case R.id.nav_carte:

                            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(fragment3).commit();
                            activeFragment = fragment3;
                            break;
                        case R.id.nav_profile:
                            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(fragment4).commit();
                            activeFragment = fragment4;
                            break;
                    }

                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };


    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) carpoolingappv1.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (!ridePostIsDisplaying && !messagePostIsDisplaying) {
            if (doubleBackExitPressed) {
                super.onBackPressed();
                return;
            }

            this.doubleBackExitPressed = true;
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackExitPressed = false;
                }
            }, 2000);
        } else {
            Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.fragment_Post_container);
            if (messagePostIsDisplaying) {
                //onBackPressed

                if (fragment2 != null) {
                    Fragment fra = new RidePostFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Post_container,fra).commit();
                    MainActivity.messagePostIsDisplaying = false;
                    //Fragment fra = new RidePostFragment();
                    //getSupportFragmentManager().beginTransaction().add(R.id.fragment_Post_container, fra).commit();
                    MainActivity.ridePostIsDisplaying = true;
                }



                //remove post fragment opened at the top of the home Fragment
            } else {

                if (ridePostIsDisplaying){
                    //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_Post_container);
                    if (fragment2 != null) {
                        getSupportFragmentManager().beginTransaction().hide(fragment2).commit();
                        MainActivity.ridePostIsDisplaying = false;
                    }
                }

            }

        }
    }
}