package com.example.carpoolingappv1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Edit_profile_Passenger extends AppCompatActivity {

    private FloatingActionButton saveNewPassProfile;

    private EditText fullName;
    private EditText email;
    private EditText dateOfBirth;
    private EditText phoneNumber;
    private EditText wilaya;
    private EditText password;
    private EditText confirmPassword;
    private EditText oldPass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile__passenger);




        fullName=findViewById(R.id.pass_edit_full_name);
        //email=findViewById(R.id.pass_edit_email);
        dateOfBirth=findViewById(R.id.pass_edit_birth_date);
        phoneNumber=findViewById(R.id.pass_edit_phone);
        wilaya=findViewById(R.id.pass_edit_wilaya);
        oldPass=findViewById(R.id.pass_edit_OldPassword);
        //password=findViewById(R.id.pass_new_edit_password);
        //confirmPassword=findViewById(R.id.pass_edit_confirm_new_password);


        MainActivity.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                retriveOldData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        MainActivity.databaseReference.child("bDate").setValue(dateOfBirth.getText().toString());
        MainActivity.databaseReference.child("wilaya").setValue(wilaya.getText().toString());

        startActivity(new Intent(carpoolingappv1.getAppContext(),ProfilePassengerFragment.class));
        finish();
    }







    private void retriveOldData(DataSnapshot dataSnapshot) {
        fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        phoneNumber.setText(dataSnapshot.child("phone").getValue().toString());
        dateOfBirth.setText(dataSnapshot.child("bDate").getValue().toString());
        wilaya.setText(dataSnapshot.child("wilaya").getValue().toString());
    }
}






