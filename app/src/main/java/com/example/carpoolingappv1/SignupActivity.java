package com.example.carpoolingappv1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public EditText emailSignup,passwordSignup,firstName,lastName,phone;
    public boolean oldAgeUser=false;
    private int oldAgeStandard=50;

    //date
    public TextView bdateSignup;
    private DatePickerDialog.OnDateSetListener bdateSetListener;

    private FirebaseAuth mAuth;


    private CheckBox carCheckBox;
    private EditText testBrk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.SignUpBtnId).setOnClickListener(this);
        findViewById(R.id.LogInTxtBtnId).setOnClickListener(this);


        emailSignup = findViewById(R.id.emailSignupId);
        passwordSignup = findViewById(R.id.passwordSignupId);
        bdateSignup = (TextView) findViewById(R.id.birthdateSignupId);
        firstName = findViewById(R.id.firstNameSignupId);
        lastName = findViewById(R.id.lastNameSignupId);
        phone = findViewById(R.id.phoneSignupId);

        Spinner wilayaSpinner = findViewById(R.id.wilayaSignupId);

        //spinner t3 l wilaya
        ArrayAdapter<CharSequence> wilayasAdapter = ArrayAdapter.createFromResource(this,R.array.wilayas,android.R.layout.simple_spinner_item);
        wilayasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wilayaSpinner.setAdapter(wilayasAdapter);
        wilayaSpinner.setOnItemSelectedListener(this);


        Spinner carModelsSpinner = findViewById(R.id.carModelSignupId);

        //spinner t3 l car models
        ArrayAdapter<CharSequence> carModelsAdapter = ArrayAdapter.createFromResource(this,R.array.carModels,android.R.layout.simple_spinner_item);
        carModelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModelsSpinner.setAdapter(carModelsAdapter);

        carModelsSpinner.setOnItemSelectedListener(this);

        //date displays widget
        bdateSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        bdateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        //get the date and put it in the textField
        bdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                //
                //Log.d("onDate set: mm/dd/yy" + month + "/" + dayOfMonth + "/" + year);

                String date ="Date of birth: " + month + "/" + dayOfMonth + "/" + year;

                int age = (int) ((Calendar.getInstance().get(Calendar.YEAR)) - year);

                if (age<=0){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    return;
                }else if (age<oldAgeStandard){
                    oldAgeUser = false;
                }else {
                    oldAgeUser = true;
                }

                bdateSignup.setText(date);
                bdateSignup.setTextColor(Color.BLACK);

                //calculate Age

                //Toast.makeText(getApplicationContext(), "year:  "+year, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Calendar year:  "+Calendar.getInstance().get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "age:  "+age, Toast.LENGTH_SHORT).show();


            }
        };
        //



        //car check box
        carCheckBox = findViewById(R.id.carCheckSignupId);
        carCheckBox.setChecked(false);
        testBrk = findViewById(R.id.test1SignupId);
        testBrk.setVisibility(View.GONE);

        carCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //display or not .. for the car's data
                if (carCheckBox.isChecked()){
                    testBrk.setVisibility(View.VISIBLE);
                }else {
                    testBrk.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.SignUpBtnId:
                registerUser();
                break;

            case R.id.LogInTxtBtnId:
                Intent intent2 = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
                break;



        }

    }

    private void registerUser() {
        final String emailS = emailSignup.getText().toString().trim();
        String passwordS = passwordSignup.getText().toString().trim();
        final String lastNameS = lastName.getText().toString().trim();
        final String firstNameS = firstName.getText().toString().trim();

        final int phoneS = Integer.parseInt(phone.getText().toString().trim()) ;
        final String bDateS = bdateSignup.getText().toString().trim();




        //email check
        if(emailS.isEmpty()){
            emailSignup.setError("email required");
            emailSignup.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailS).matches()){
            emailSignup.setError("please enter a valid email");
            emailSignup.requestFocus();
            return;
        }
        //password check
        if(passwordS.isEmpty()){
            passwordSignup.setError("password required");
            passwordSignup.requestFocus();
            return;
        }
        if(passwordS.length()>6){
            passwordSignup.setError("password should be < 6 characters");
            passwordSignup.requestFocus();
            return;
        }
        //last name and first name check
        if(lastNameS.isEmpty()){
            lastName.setError("password required");
            lastName.requestFocus();
            return;
        }
        if(firstNameS.isEmpty()){
            firstName.setError("password required");
            firstName.requestFocus();
            return;
        }

        //CREATING USER
        mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(firstNameS,lastNameS,emailS,bDateS,phoneS);

                            //put the account's data into (database) Users > "userID" > {}
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            //SEND EMAIL VERIFICATION
                            mAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Toast.makeText(getApplicationContext(), "Please verify your email address to complete registration", Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });



                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    //WILAYAS
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    //WILAYAS
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
