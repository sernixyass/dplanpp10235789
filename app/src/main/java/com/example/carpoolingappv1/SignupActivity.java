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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText emailSignup,passwordSignup;

    //date
    public TextView bdateSignup;
    private DatePickerDialog.OnDateSetListener bdateSetListener;

    private FirebaseAuth mAuth;

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
                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth,
                        bdateSetListener,
                        year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

                String date = month + "/" + dayOfMonth + "/" + year;
                bdateSignup.setText(date);

            }
        };
        //


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
        String emailS = emailSignup.getText().toString().trim();
        String passwordS = passwordSignup.getText().toString().trim();

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

        //CREATING USER
        mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
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
}
