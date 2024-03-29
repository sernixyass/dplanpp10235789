package com.example.carpoolingappv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
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

    public Boolean weeklyTrip=true;

    private Boolean saturday=false;
    private Boolean sunday=false;
    private Boolean monday=false;
    private Boolean tuesday=false;
    private Boolean wednesday=false;
    private Boolean thursday=false;
    private Boolean friday=false;

    private String hourTrip;
    private String tripDate;

    ElegantNumberButton places;

    EditText price;

    public Integer maxPrice;
    public Integer pricePerkiloMetre = 45;


    //date
    public TextView gDate;
    private DatePickerDialog.OnDateSetListener gDateSetListener;
    private RadioGroup mRadioGroup ;
    private LinearLayout checkDaysLayout;
    private LinearLayout setDaysLayout;




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

        if (MainActivity.isConductor){
            price.setVisibility(View.VISIBLE);
        }else {
            price.setVisibility(View.GONE);
        }

        mRadioGroup =findViewById(R.id.radio_group);
        checkDaysLayout= findViewById(R.id.daily_layout);
        setDaysLayout= findViewById(R.id.set_date_layout);


        checkDaysLayout.setVisibility(View.VISIBLE);
        setDaysLayout.setVisibility(View.GONE);



        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_daily:
                        // do operations specific to this selection
                        checkDaysLayout.setVisibility(View.VISIBLE);
                        setDaysLayout.setVisibility(View.GONE);
                        weeklyTrip = true;
                        break;
                    case R.id.radio_set_date:
                        checkDaysLayout.setVisibility(View.GONE);
                        setDaysLayout.setVisibility(View.VISIBLE);
                        weeklyTrip = false;
                        break;
                }

            }
        });


        gDate = findViewById(R.id.set_date_edit_text);

        //date displays widget
        gDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CompeltPosting.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth,
                        gDateSetListener,
                        year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        //get the date and put it in the textField
        gDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //
                //Log.d("onDate set: mm/dd/yy" + month + "/" + dayOfMonth + "/" + year);

                String monthName = "";
                if (month == 1){
                    monthName = "January";
                }
                if (month == 2){
                    monthName = "February";
                }
                if (month == 3){
                    monthName = "March";
                }
                if (month == 4){
                    monthName = "April";
                }
                if (month == 5){
                    monthName = "May";
                }
                if (month == 6){
                    monthName = "June";
                }
                if (month == 7){
                    monthName = "July";
                }
                if (month == 8){
                    monthName = "August";
                }
                if (month == 9){
                    monthName = "September";
                }
                if (month == 10){
                    monthName = "October";
                }
                if (month == 11){
                    monthName = "November";
                }
                if (month == 12){
                    monthName = "December";
                }
                String date = dayOfMonth + " " + monthName + " " + year;

                int age = (int) ((Calendar.getInstance().get(Calendar.YEAR)) - year);

                if (age>0) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                gDate.setText(date);
                gDate.setTextColor(Color.BLACK);

                tripDate =gDate.getText().toString();

            }
        };




        //price.setHint("Max :" + maxPrice.toString() +" DA");

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*1),(int)(height*1));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y=0;

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
                //savePoste();

                AlertDialog.Builder builder = new AlertDialog.Builder(CompeltPosting.this);
                builder.setMessage("Create this ride ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                savePoste();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Confirmation")
                        .show();

                //Intent intent = new Intent(getApplicationContext(),AddPostActivity.class);
                //startActivity(intent);
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

        if (weeklyTrip){
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
            tripDate ="";
        }else {
            saturday=false;
            sunday=false;
            monday=false;
            tuesday=false;
            wednesday=false;
            thursday=false;
            friday=false;
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


        ///adding the full date
        map.put("tripDate", tripDate);

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
        map.put("accountIDJoining5","");
        map.put("accountIDJoining6","");
        map.put("accountIDJoining7","");

        map.put("hourTrip",hourTrip);
        map.put("distance",distance);
        map.put("estimatedTime",estimatedTime);

        String pricedata = price.getText().toString();
        if (price.getText().toString().equals(null) || price.getText().toString().equals("")){
            pricedata = "0";
        }
        map.put("price",Integer.parseInt(pricedata));
        map.put("weeklyTrip",weeklyTrip);


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



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}