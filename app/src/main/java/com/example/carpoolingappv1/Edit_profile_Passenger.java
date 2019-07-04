package com.example.carpoolingappv1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Edit_profile_Passenger extends AppCompatActivity {

    private FloatingActionButton saveNewPassProfile;

    private EditText fullName;
    private EditText dateOfBirth;
    private EditText phoneNumber;
    private EditText wilaya;
    private Button changePass;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile__passenger);




        fullName=findViewById(R.id.pass_edit_full_name);
        //dateOfBirth=findViewById(R.id.pass_edit_birth_date);
        phoneNumber=findViewById(R.id.pass_edit_phone);
        wilaya=findViewById(R.id.pass_edit_wilaya);
        changePass=findViewById(R.id.change_pass_pass);



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
                Intent intent = new Intent(Edit_profile_Passenger.this,change_password_pass.class);
                startActivity(intent);
            }
        });


        saveNewPassProfile = findViewById(R.id.save_pass_profile);
        saveNewPassProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save in fireBase new profile
                saveNewPassengerProfile();
            }
        });
    }

    private void saveNewPassengerProfile() {
        //savng to database
        MainActivity.databaseReference.child("fullName").setValue(fullName.getText().toString());
        MainActivity.databaseReference.child("phone").setValue(phoneNumber.getText().toString());
       // MainActivity.databaseReference.child("bDate").setValue(dateOfBirth.getText().toString());
        MainActivity.databaseReference.child("wilaya").setValue(wilaya.getText().toString());

        startActivity(new Intent(carpoolingappv1.getAppContext(),ProfilePassengerFragment.class));
        finish();
    }







    private void retriveOldData(DataSnapshot dataSnapshot) {
        fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        phoneNumber.setText(dataSnapshot.child("phone").getValue().toString());
        //dateOfBirth.setText(dataSnapshot.child("bDate").getValue().toString());
        wilaya.setText(dataSnapshot.child("wilaya").getValue().toString());
    }
}






