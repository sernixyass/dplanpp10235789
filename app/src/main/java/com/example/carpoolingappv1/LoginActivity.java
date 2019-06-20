package com.example.carpoolingappv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText emailLogin, passwordLogin;

    private FirebaseAuth mAuth;
    private CheckBox mCheckBox;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.SignUpTxtBtnId).setOnClickListener(this);
        findViewById(R.id.LoginBtnId).setOnClickListener(this);

        emailLogin = findViewById(R.id.emailLoginId);
        passwordLogin = findViewById(R.id.passwordLoginId);

        mAuth = FirebaseAuth.getInstance();

        //shared preference
        mCheckBox = (CheckBox) findViewById(R.id.remember_email_pass);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        checkSharedPreferences();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LoginBtnId:
                LoginUser();
                break;

            case R.id.SignUpTxtBtnId:
                //startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                //finish();

                //Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                //startActivity(intent);
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

        mAuth.signInWithEmailAndPassword(emailL, passwordL).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //PASSWORD AND EMAIL ARE MATCHES
                    //EMAIL SHOULD BE VERIFIED
                    if (mAuth.getCurrentUser().isEmailVerified()) {

                        //save or don't save the information tayped in
                        if (mCheckBox.isChecked()) {

                            mEditor.putString(getString(R.string.checkBoxSH), "True");
                            mEditor.commit();

                            mEditor.putString(getString(R.string.emailSH), emailL);
                            mEditor.commit();
                            mEditor.putString(getString(R.string.passwordSH), passwordL);
                            mEditor.commit();

                        } else {
                            mEditor.putString(getString(R.string.checkBoxSH), "false");
                            mEditor.commit();

                            mEditor.putString(getString(R.string.emailSH), "");
                            mEditor.commit();
                            mEditor.putString(getString(R.string.passwordSH), "");
                            mEditor.commit();
                        }

                        //GO TO THE ACCOUNT PAGE

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        showErrorDialog("email not verified");
                    }


                } else {
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


    //Shared Preferences
    private void checkSharedPreferences() {
        String checkboxSP = mPreferences.getString(getString(R.string.checkBoxSH), "false");
        String emailSP = mPreferences.getString(getString(R.string.emailSH), "");
        String passwordSP = mPreferences.getString(getString(R.string.passwordSH), "");

        emailLogin.setText(emailSP);
        passwordLogin.setText(passwordSP);

        if (checkboxSP.equals("true")) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }
    }


}