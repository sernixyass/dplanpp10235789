package com.example.carpoolingappv1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class ChoosingUserType extends Activity implements View.OnClickListener {

    public Button conductorBtn;
    public Button passengerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_user_type);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //placesText = findViewById(R.id.C_P_places);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.95),(int)(height*.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y= -20;


        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        //
        conductorBtn = findViewById(R.id.typeConductorBtn);
        passengerBtn = findViewById(R.id.typePassengerBtn);

        passengerBtn.setOnClickListener(this);
        conductorBtn.setOnClickListener(this);


    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.typePassengerBtn:
                toThePassengerForm();
                break;
            case R.id.typeConductorBtn:
                toTheConductorForm();
                break;
        }
    }

    private void toThePassengerForm() {
        startActivity(new Intent(this,SignupPassengerActivity.class));
    }

    private void toTheConductorForm() {
        startActivity(new Intent(this,SignupConductorActivity.class));
    }

}
