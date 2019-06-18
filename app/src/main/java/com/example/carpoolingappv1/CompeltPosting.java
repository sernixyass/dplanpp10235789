package com.example.carpoolingappv1;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compelt_posting);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        placesText = findViewById(R.id.C_P_places);


        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y=-20;

        getWindow().setAttributes(params);



        conferm = findViewById(R.id.conferme_date_btn);
       conferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePoste();

                Intent intent = new Intent(getApplicationContext(),AddPostActivity.class);
                startActivity(intent);
            }
        });


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

