package com.example.carpoolingappv1;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText emailSignup,passwordSignup;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.SignUpBtnId).setOnClickListener(this);
        findViewById(R.id.LogInTxtBtnId).setOnClickListener(this);

        emailSignup = findViewById(R.id.emailSignupId);
        passwordSignup = findViewById(R.id.passwordSignupId);


        mAuth = FirebaseAuth.getInstance();
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

                                               // Toast.makeText(getApplicationContext(), "Please verify your email address to complete registration", Toast.LENGTH_SHORT).show();
                                                showErrorDialog("Please verify your email address to complete registration");

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
    private void showErrorDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok   , null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
                
    }
}
