package com.example.carpoolingappv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CompeltPosting extends Activity {

    private Button conferm ;


    //TextView placesText;

    //Map<String,Boolean> weekDays = new HashMap<String, Boolean>();

    CheckBox satCB;
    CheckBox sunCB;
    CheckBox monCB;
    CheckBox tueCB;
    CheckBox wedCB;
    CheckBox thuCB;
    CheckBox friCB;

    private Boolean saturday=false;
    private Boolean sunday=false;
    private Boolean monday=false;
    private Boolean tuesday=false;
    private Boolean wednesday=false;
    private Boolean thursday=false;
    private Boolean friday=false;

    private String hourTrip;

    ElegantNumberButton places;

    EditText price;

    public Integer maxPrice;
    public Integer pricePerkiloMetre = 45;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compelt_posting);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        satCB =findViewById(R.id.saturdayCB);
        sunCB =findViewById(R.id.sundayCB);
        monCB =findViewById(R.id.mondayCB);
        tueCB =findViewById(R.id.tuesdayCB);
        wedCB =findViewById(R.id.wednesdayCB);
        thuCB =findViewById(R.id.thursdayCB);
        friCB =findViewById(R.id.fridayCB);




        places = findViewById(R.id.places_cp);

        price = findViewById(R.id.price_cp);

        //String dis = (MapSearchPointsFragment.distance.substring(0,MapSearchPointsFragment.distance.length()-3)).trim();
        //maxPrice = (Integer.valueOf((String) dis.toString())) * pricePerkiloMetre;

        //price.setHint("Max :" + maxPrice.toString() +" DA");

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*1),(int)(height*1));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y=-20;

        params.dimAmount=0.0f;

        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);



        final Spinner hoursSpinner = findViewById(R.id.hourCompletePosting);
        //spinner t3 l wilaya
        final ArrayAdapter<CharSequence> hoursAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.hours,android.R.layout.simple_spinner_item);
        hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hoursSpinner.setAdapter(hoursAdapter);
        hoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hourTrip = hoursAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hourTrip = "";
            }
        });


        conferm = findViewById(R.id.conferme_date_btn);
        conferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePoste();




//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setMessage("Do you really want to create this ride ?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                savePoste();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).setTitle("Confermation !!")
//                        .show();
//
//                AlertDialog alertDialog = builder.create();
//

                Intent intent = new Intent(getApplicationContext(),AddPostActivity.class);
                startActivity(intent);
            }
        });


    }




    private void savePoste() {
        String startP = AddPostActivity.startingPointText;
        String endP = AddPostActivity.endingPointText;
        //Integer places = Integer.valueOf(placesText.getText().toString());
        LatLng tripPos =    AddPostActivity.startingPointLL;
        LatLng tripDesPos = AddPostActivity.endingPointLL;
        String estimatedTime = MapSearchPointsFragment.estimatedTime;
        String distance = MapSearchPointsFragment.distance;

        if(satCB.isChecked()){
            saturday = true;
        }


        if(sunCB.isChecked()){
            sunday = true;
        }
        if(monCB.isChecked()){
            monday = true;
        }
        if(tueCB.isChecked()){
            tuesday = true;
        }
        if(wedCB.isChecked()){
            wednesday = true;
        }
        if(thuCB.isChecked()){
            thursday = true;
        }
        if(friCB.isChecked()){
            friday = true;
        }


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
        if (MainActivity.isConductor){
            map.put("places", 0);
        }else {
            map.put("places", 1);
        }
        map.put("totalPlaces",Integer.parseInt(places.getNumber()));

        map.put("tripPosition", tripPos);
        map.put("tripDestinationPosition", tripDesPos);
        map.put("accountIDPostedIt",MainActivity.mAuth.getCurrentUser().getUid());

        map.put("saturday",saturday);
        map.put("sunday",sunday);
        map.put("monday",monday);
        map.put("tuesday",tuesday);
        map.put("wednesday",wednesday);
        map.put("thursday",thursday);
        map.put("friday",friday);

        if (MainActivity.isConductor){
            map.put("isTaken",true);
            map.put("accountIDTakedIt",MainActivity.mAuth.getCurrentUser().getUid());
        }else {
            map.put("isTaken",false);
            map.put("accountIDTakedIt"," ");

        }

        map.put("isFull",false);
        if (MainActivity.isConductor){
            map.put("accountIDJoining1","");
        }else {
            map.put("accountIDJoining1",MainActivity.mAuth.getCurrentUser().getUid());
        }

        map.put("accountIDJoining2","");
        map.put("accountIDJoining3","");
        map.put("accountIDJoining4","");

        map.put("hourTrip",hourTrip);
        map.put("distance",distance);
        map.put("estimatedTime",estimatedTime);

        map.put("price",Integer.parseInt(price.getText().toString()));

        databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(carpoolingappv1.getAppContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    return;
                }

            }
        });


    }

}