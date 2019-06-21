package com.example.carpoolingappv1;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Edit_profile_Conductor extends AppCompatActivity {

    private FloatingActionButton saveNewConProfile;

    private EditText fullName;
    private EditText email;
    private EditText dateOfBirth;
    private EditText phoneNumber;
    private EditText wilaya;
    private EditText password;
    private EditText confirmPassword;
    private EditText carModel;
    private EditText carUniqueNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile__conductor);


        fullName=findViewById(R.id.con_edit_full_name);
        email=findViewById(R.id.con_edit_email);
        dateOfBirth=findViewById(R.id.con_edit_birth_date);
        phoneNumber=findViewById(R.id.con_edit_phone);
        wilaya=findViewById(R.id.con_edit_wilaya);
        password=findViewById(R.id.con_edit_password);
        confirmPassword=findViewById(R.id.con_edit_confirm_password);
        carModel=findViewById(R.id.con_edit_car_model);
        carUniqueNumber=findViewById(R.id.con_edit_car_unique_nbe);







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
    }
}
