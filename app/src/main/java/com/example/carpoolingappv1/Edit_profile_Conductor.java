package com.example.carpoolingappv1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Edit_profile_Conductor extends AppCompatActivity {

    private FloatingActionButton saveNewConProfile;

    private EditText fullName;
    private EditText phoneNumber;
    private EditText wilaya;
    private EditText carModel;
    private EditText carUniqueNumber;
    private Button changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile__conductor);


        fullName=findViewById(R.id.con_edit_full_name);
        //dateOfBirth=findViewById(R.id.con_edit_birth_date);
        phoneNumber=findViewById(R.id.con_edit_phone);
        wilaya=findViewById(R.id.con_edit_wilaya);
        changePass=findViewById(R.id.change_pass_con);
        carModel=findViewById(R.id.con_edit_car_model);
        carUniqueNumber=findViewById(R.id.con_edit_car_unique_nbe);



        MainActivity.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                retriveOldData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_profile_Conductor.this,change_password_con.class);
                startActivity(intent);
            }
        });

        saveNewConProfile = findViewById(R.id.save_con_profile);
        saveNewConProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save in fireBase new profile
                saveNewConducProfile();
            }
        });
    }

    private void saveNewConducProfile() {
        //savng to database
        MainActivity.databaseReference.child("fullName").setValue(fullName.getText().toString());
        MainActivity.databaseReference.child("phone").setValue(phoneNumber.getText().toString());
       // MainActivity.databaseReference.child("bDate").setValue(dateOfBirth.getText().toString());
        MainActivity.databaseReference.child("wilaya").setValue(wilaya.getText().toString());
        MainActivity.databaseReference.child("carModel").setValue(carModel.getText().toString());
        MainActivity.databaseReference.child("carKey").setValue(carUniqueNumber.getText().toString());

        //startActivity(new Intent(carpoolingappv1.getAppContext(),MainActivity.class));
        finish();
    }




    private void  retriveOldData(DataSnapshot dataSnapshot) {
        fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        phoneNumber.setText(dataSnapshot.child("phone").getValue().toString());
       // dateOfBirth.setText(dataSnapshot.child("bDate").getValue().toString());
        wilaya.setText(dataSnapshot.child("wilaya").getValue().toString());
        carModel.setText(dataSnapshot.child("carModel").getValue().toString());
        carUniqueNumber.setText(dataSnapshot.child("carKey").getValue().toString());
    }

}
