package com.example.carpoolingappv1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class PriceSetterActivity extends AppCompatActivity {

    EditText newPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_setter);

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



        newPrice = findViewById(R.id.priceSetter_txt);

        findViewById(R.id.priceSetter_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPrice(Integer.parseInt(newPrice.getText().toString()));
            }
        });

    }

    public void setNewPrice(int s) {
        DatabaseReference databaseReferencePrice = MainActivity.databaseReferencePosts.child(MainActivity.selectedTripID).child("price");

        databaseReferencePrice.setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }else {
                    Toast.makeText(PriceSetterActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

        });
        finish();
    }
}
