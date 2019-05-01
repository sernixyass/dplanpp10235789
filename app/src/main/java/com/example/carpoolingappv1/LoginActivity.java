package com.example.carpoolingappv1;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText emailLogin,passwordLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.SignUpTxtBtnId).setOnClickListener(this);
        findViewById(R.id.LoginBtnId).setOnClickListener(this);

        emailLogin = findViewById(R.id.emailLoginId);
        passwordLogin = findViewById(R.id.passwordLoginId);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.LoginBtnId:
                LoginUser();
                break;

            case R.id.SignUpTxtBtnId:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));

                //Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                //startActivity(intent);
                break;


        }
    }

    public void LoginUser() {
        String emailL = emailLogin.getText().toString().trim();
        String passwordL = passwordLogin.getText().toString().trim();

        if(emailL.isEmpty()){
            emailLogin.setError("email required");
            emailLogin.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailL).matches()){
            emailLogin.setError("please enter a valid email");
            emailLogin.requestFocus();
            return;
        }
        if(passwordL.isEmpty()){
            passwordLogin.setError("password required");
            passwordLogin.requestFocus();
            return;
        }
        if(passwordL.length()>6){
            passwordLogin.setError("password should be < 6 characters");
            passwordLogin.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailL,passwordL).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //PASSWORD AND EMAIL ARE MATCHES
                    //EMAIL SHOULD BE VERIFIED
                    if (mAuth.getCurrentUser().isEmailVerified()){
                        //GO TO THE ACCOUNT PAGE

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "email not verified", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}