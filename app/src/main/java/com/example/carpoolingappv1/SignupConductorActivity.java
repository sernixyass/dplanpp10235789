package com.example.carpoolingappv1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class SignupConductorActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText email;
    public EditText password;
    public EditText password2;
    public EditText fullName;
    public EditText phone;
    public String wilayaS;
    public EditText carModel;
    public EditText carKey1;
    public EditText carKey2;
    public EditText carKey3;
//    public String carKey;


    //date
    public TextView bdate;
    private DatePickerDialog.OnDateSetListener bdateSetListener;


    public boolean oldAgeUser=false;
    private int oldAgeStandard=60;

    private FirebaseAuth mAuth;

    private Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_conductor);


        mAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.emailSignupCId);
        password = findViewById(R.id.passwordSignupCId);
        password2 = findViewById(R.id.passwordSignupCId2);
        fullName = findViewById(R.id.fullNameSignupCId);
            phone = findViewById(R.id.phoneSignupCId);
        carModel =findViewById(R.id.carModelSignupId);

        carKey1 = findViewById(R.id.carKey1SignupId);
        carKey2 = findViewById(R.id.carKey2SignupId);
        carKey3 = findViewById(R.id.carKey3SignupId);


        findViewById(R.id.SignUpCBtnId).setOnClickListener(this);
        findViewById(R.id.LogInCTxtBtnId).setOnClickListener(this);




        final Spinner wilayaSpinner = findViewById(R.id.wilayaSignupCId);
        //spinner t3 l wilaya
        final ArrayAdapter<CharSequence> wilayasAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.wilayas,android.R.layout.simple_spinner_item);
        wilayasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wilayaSpinner.setAdapter(wilayasAdapter);
        wilayaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wilayaS = wilayasAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                wilayaS = "";
            }
        });





        bdate = findViewById(R.id.birthdateSignupCId);

        //date displays widget
        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupConductorActivity.this,
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

                bdate.setText(date);
                bdate.setTextColor(Color.BLACK);

                //calculate Age

                //Toast.makeText(getApplicationContext(), "year:  "+year, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Calendar year:  "+Calendar.getInstance().get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "age:  "+age, Toast.LENGTH_SHORT).show();

            }
        };

    }

    private void registerUserC() {
        final String emailS = email.getText().toString().trim();
        String passwordS = password.getText().toString().trim();
        String passwordS2 = password2.getText().toString().trim();
        final String fulNameS = fullName.getText().toString().trim();

        final String carModelS = carModel.getText().toString().trim();

        final String carKey1S = carKey1.getText().toString().trim();
        final String carKey2S = carKey2.getText().toString().trim();
        final String carKey3S = carKey3.getText().toString().trim();

        final String carKey4 = carKey1S + " - " + carKey2S + " - " + carKey3S;

        final int phoneS;
        if (phone.getText().toString()== ""){
            phoneS = Integer.parseInt(phone.getText().toString().trim()) ;
        }
        else {
            phoneS = 0;
        }

        final String bDateS = bdate.getText().toString().trim();




        //email check
        if(emailS.isEmpty()){
            email.setError("email required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailS).matches()){
            email.setError("please enter a valid email");
            email.requestFocus();
            return;
        }
        //password check
        if(passwordS.isEmpty()){
            password.setError("password required");
            password.requestFocus();
            return;
        }
        if(passwordS2.isEmpty()){
            password2.setError("retyping password required");
            password2.requestFocus();
            return;
        }
        if(!passwordS.equals(passwordS2)){
            password2.setError("please retype the same password");
            password2.requestFocus();
            return;
        }
        if(passwordS.length()>6){
            password.setError("password should be < 6 characters");
            password.requestFocus();
            return;
        }
        //last name and first name check
        if(fulNameS.isEmpty()){
            fullName.setError("please enter your name");
            fullName.requestFocus();
            return;
        }

        //CREATING USER
        mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(true,fulNameS,emailS,bDateS,phoneS,wilayaS,carModelS,carKey4,"");

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SignUpCBtnId:

                registerUserC();

                break;

            case R.id.LogInCTxtBtnId:
                Intent intent2 = new Intent(SignupConductorActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
                break;

        }
    }
}
