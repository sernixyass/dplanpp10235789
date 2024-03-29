package com.example.carpoolingappv1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText emailLogin, passwordLogin;

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
        switch (v.getId()) {
            case R.id.LoginBtnId:
                LoginUser();
                break;

            case R.id.SignUpTxtBtnId:
                signUpButton();
                break;
        }

    }

    public void signUpButton(){
        Intent intent = new Intent(this.getApplicationContext(),ChoosingUserType.class);
        startActivity(intent);
    }

    public void LoginUser() {
        final String emailL = emailLogin.getText().toString().trim();
        final String passwordL = passwordLogin.getText().toString().trim();

        if (emailL.isEmpty()) {
            emailLogin.setError("email required");
            emailLogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailL).matches()) {
            emailLogin.setError("please enter a valid email");
            emailLogin.requestFocus();
            return;
        }
        if (passwordL.isEmpty()) {
            passwordLogin.setError("password required");
            passwordLogin.requestFocus();
            return;
        }
        if (passwordL.length() > 6) {
            passwordLogin.setError("password should be < 6 characters");
            passwordLogin.requestFocus();
            return;
        }

        findViewById(R.id.login_progresbar).setVisibility(View.VISIBLE);
        findViewById(R.id.LoginBtnId).setClickable(false);
        findViewById(R.id.SignUpTxtBtnId).setClickable(false);
        mAuth.signInWithEmailAndPassword(emailL, passwordL).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //PASSWORD AND EMAIL ARE MATCHES
                    //EMAIL SHOULD BE VERIFIED
                    findViewById(R.id.login_progresbar).setVisibility(View.GONE);
                    if (mAuth.getCurrentUser().isEmailVerified()) {


                        //GO TO THE ACCOUNT PAGE

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        showErrorDialog("email not verified");
                    }


                } else {
                    findViewById(R.id.login_progresbar).setVisibility(View.GONE);
                    findViewById(R.id.LoginBtnId).setClickable(true);
                    findViewById(R.id.SignUpTxtBtnId).setClickable(true);
                    showErrorDialog(task.getException().getMessage());
                }
            }
        });
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }




}