package com.example.carpoolingappv1;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Edit_profile_Passenger extends AppCompatActivity {

    private FloatingActionButton saveNewPassProfile;

    private EditText fullName;
    private EditText email;
    private EditText dateOfBirth;
    private EditText phoneNumber;
    private EditText wilaya;
    private EditText password;
    private EditText confirmPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile__passenger);


        fullName=findViewById(R.id.pass_edit_full_name);
        email=findViewById(R.id.pass_edit_email);
        dateOfBirth=findViewById(R.id.pass_edit_birth_date);
        phoneNumber=findViewById(R.id.pass_edit_phone);
        wilaya=findViewById(R.id.pass_edit_wilaya);
        password=findViewById(R.id.pass_edit_password);
        confirmPassword=findViewById(R.id.pass_edit_confirm_password);




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
    }
}
