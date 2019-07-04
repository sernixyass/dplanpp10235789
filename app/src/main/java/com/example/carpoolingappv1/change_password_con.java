package com.example.carpoolingappv1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class change_password_con extends AppCompatActivity {

    private EditText oldPassword , newPassword , retypePassword ;
    private Button saveBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_con);

        oldPassword=findViewById(R.id.con_set_old_password);
        newPassword=findViewById(R.id.con_set_new_password);
        retypePassword=findViewById(R.id.con_retype_new_password);
        saveBut=findViewById(R.id.con_save_password);

        saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewPassword();
                finish();
            }
        });

    }

    private void saveNewPassword() {


        String passwordOld = oldPassword.getText().toString().trim();
        String passwordS = newPassword.getText().toString().trim();
        String passwordS2 = retypePassword.getText().toString().trim();

        //password check
        if(passwordS.isEmpty()){
            newPassword.setError("password required");
            newPassword.requestFocus();
            return;
        }
        if(passwordS2.isEmpty()){
            retypePassword.setError("retyping password required");
            retypePassword.requestFocus();
            return;
        }
        if(!passwordS.equals(passwordS2)){
            retypePassword.setError("please retype the same password");
            retypePassword.requestFocus();
            return;
        }

        if(passwordS.length()>6){
            newPassword.setError("password should be < 6 characters");
            newPassword.requestFocus();
            return;
        }

        if (passwordOld.isEmpty()) {
            oldPassword.setError("password required");
            oldPassword.requestFocus();
            return;
        }

        String mEmail= MainActivity.mAuth.getCurrentUser().getEmail();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(mEmail,passwordOld);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(String.valueOf(newPassword)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(change_password_con.this,"Something went wrong  ",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(change_password_con.this,"Password Successfully Changed ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(change_password_con.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
