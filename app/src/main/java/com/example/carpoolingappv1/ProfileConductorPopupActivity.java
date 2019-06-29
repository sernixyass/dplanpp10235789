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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

import static com.example.carpoolingappv1.MainActivity.databaseReference;

public class ProfileConductorPopupActivity extends AppCompatActivity {

    DatabaseReference databaseReferenceCon;
    TextView fullNameGrand,phone,willaya,carModel,carNumber;
    ImageView profilPicC;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_conductor_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.95),(int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y= -20;


        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //LINK
        fullNameGrand = findViewById(R.id.con_big_profile_name_pup);
        phone = findViewById(R.id.con_phone_pup);
        willaya = findViewById(R.id.con_wilaya_pup);
        carModel = findViewById(R.id.con_car_model_pup);
        carNumber = findViewById(R.id.con_car_unique_nbe_pup);

        profilPicC = findViewById(R.id.con_profile_pic_pup);


        //GET and SHOW DATA
        databaseReferenceCon = FirebaseDatabase.getInstance().getReference().child("Users").child(MainActivity.selectedDriverAccountID);


        databaseReferenceCon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshotCon) {
                fullNameGrand.setText(dataSnapshotCon.child("fullName").getValue().toString());
                phone.setText(dataSnapshotCon.child("phone").getValue().toString());
                willaya.setText(dataSnapshotCon.child("wilaya").getValue().toString());
                carModel.setText(dataSnapshotCon.child("carModel").getValue().toString());
                carNumber.setText(dataSnapshotCon.child("carKey").getValue().toString());

                if (!Objects.equals(dataSnapshotCon.child("profilePic").getValue(), "") ){
                    //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                    if (carpoolingappv1.getAppContext()!=null){
                        Glide.with(carpoolingappv1.getAppContext()).load(dataSnapshotCon.child("profilePic").getValue())
                                .apply(RequestOptions.circleCropTransform())
                                .into(profilPicC);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
