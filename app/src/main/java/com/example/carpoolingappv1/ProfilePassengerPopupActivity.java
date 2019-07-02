package com.example.carpoolingappv1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfilePassengerPopupActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    private TextView fullNameGrand,phone,willaya;
    ImageView profilPicP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_passenger_popup);


        //POPUP
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*1),(int)(height*1));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y= -20;


        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);


        //LINK
        fullNameGrand = findViewById(R.id.user_big_profile_name_pup);
        phone = findViewById(R.id.pass_phone_pup);
        willaya = findViewById(R.id.pass_wilaya_pup);
        profilPicP = findViewById(R.id.pass_profile_pic_pup);


        //GET and SHOW DATA
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(MainActivity.selectedJoinAccountID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fullNameGrand.setText(dataSnapshot.child("fullName").getValue().toString());
                phone.setText(dataSnapshot.child("phone").getValue().toString());
                willaya.setText(dataSnapshot.child("wilaya").getValue().toString());

                if (!Objects.equals(dataSnapshot.child("profilePic").getValue(), "") ){
                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                    if (carpoolingappv1.getAppContext()!=null){
                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshot.child("profilePic").getValue())
                                .apply(RequestOptions.circleCropTransform())
                                .into(profilPicP);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });














    }
}
