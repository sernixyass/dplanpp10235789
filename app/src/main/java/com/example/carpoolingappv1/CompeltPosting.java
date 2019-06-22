package com.example.carpoolingappv1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CompeltPosting extends Activity {

    private Button conferm ;


    TextView placesText;

    Map<String,Boolean> weekDays = new HashMap<>();

    CheckBox satCB;
    CheckBox sunCB;
    CheckBox monCB;
    CheckBox tueCB;
    CheckBox wedCB;
    CheckBox thuCB;
    CheckBox friCB;

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




        placesText = findViewById(R.id.C_P_places);


        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y=-20;

        params.dimAmount=0.0f;

        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);




        conferm = findViewById(R.id.conferme_date_btn);
       conferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePoste();

                Intent intent = new Intent(getApplicationContext(),AddPostActivity.class);
                startActivity(intent);
            }
        });

        if(satCB.isChecked()){
            weekDays.put("saturday",true);
        }

        if(sunCB.isChecked()) weekDays.put("sunday",true);

        if(monCB.isChecked()) weekDays.put("monday",true);

        if(tueCB.isChecked()) weekDays.put("tuesday",true);

        if(wedCB.isChecked()) weekDays.put("wednesday",true);

        if(thuCB.isChecked()) weekDays.put("thursday",true);

        if(friCB.isChecked()) weekDays.put("friday",true);


    }




    private void savePoste() {
        String startP = AddPostActivity.startingPointText;
        String endP = AddPostActivity.endingPointText;
        Integer places = Integer.valueOf(placesText.getText().toString());
        LatLng tripPos = AddPostActivity.startingPointLL;
        LatLng tripDesPos = AddPostActivity.endingPointLL;



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
        map.put("tripPosition", tripPos);
        map.put("tripDestinationPosition", tripDesPos);
        map.put("accountIDPostedIt",MainActivity.mAuth.getCurrentUser().getUid());
        map.put("weekDays",weekDays);


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

}