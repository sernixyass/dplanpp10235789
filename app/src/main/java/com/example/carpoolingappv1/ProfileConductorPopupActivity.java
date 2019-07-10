package com.example.carpoolingappv1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.apprating.listener.RatingDialogListener;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Model.Rating;

import static com.example.carpoolingappv1.MainActivity.databaseReference;

public class ProfileConductorPopupActivity extends AppCompatActivity  {




    DatabaseReference databaseReferenceCon;
    TextView fullNameGrand,phone,willaya,carModel,carNumber;
    ImageView profilPicC;


    Button rateBut;
    RatingBar rateConBar , ratingBarProfile ;
    LinearLayout mainLayout;
    Button confirmBtn;
    EditText commentField ;


    FirebaseDatabase mDatabase;
    DatabaseReference ratingTbl;


    public Double totaleRates= 0.0;
    public static String selectedConductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_conductor_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.95),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y= -20;


        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);


        //LINK
        fullNameGrand = findViewById(R.id.con_big_profile_name_pup);
        phone = findViewById(R.id.con_phone_pup);
        willaya = findViewById(R.id.con_wilaya_pup);
        carModel = findViewById(R.id.con_car_model_pup);
        carNumber = findViewById(R.id.con_car_unique_nbe_pup);
        profilPicC = findViewById(R.id.con_profile_pic_pup);

        rateBut=findViewById(R.id.rate_conductor);
        rateConBar = findViewById(R.id.rate_conductor_popup);
        mainLayout= findViewById(R.id.rating_layout);
        confirmBtn=findViewById(R.id.share_rating);
        commentField=findViewById(R.id.comment_on_conductor);
        ratingBarProfile=findViewById(R.id.rating_bar_profile);


        mainLayout.setVisibility(View.GONE);



        //GET and SHOW DATA
        mDatabase = FirebaseDatabase.getInstance();


        databaseReferenceCon = mDatabase.getReference().child("Users").child(MainActivity.selectedDriverAccountID);

        ratingTbl = databaseReferenceCon.child("rates");

        databaseReferenceCon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshotCon) {
                fullNameGrand.setText(dataSnapshotCon.child("fullName").getValue().toString());
                phone.setText(dataSnapshotCon.child("phone").getValue().toString());
                willaya.setText(dataSnapshotCon.child("wilaya").getValue().toString());
                carModel.setText(dataSnapshotCon.child("carModel").getValue().toString());
                carNumber.setText(dataSnapshotCon.child("carKey").getValue().toString());

                totaleRates = dataSnapshotCon.child("totalRates").getValue(Double.class);

                if (totaleRates == (null)){
                    totaleRates=0.0;
                }
                ratingBarProfile.setRating(totaleRates.floatValue());

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




        if (MainActivity.currentUserID.equals(MainActivity.selectedDriverAccountID)){
            //Toast.makeText(ProfileConductorPopupActivity.this, "you can't rate your self", Toast.LENGTH_SHORT).show();
            //return;
            rateBut.setVisibility(View.GONE);
        }else
            {
                rateBut.setVisibility(View.VISIBLE);
            rateBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mainLayout.setVisibility(View.VISIBLE);

                    confirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //getRatingConductor(databaseReferenceCon);

                            //Get Rating ,Create rating obj and upload to firebase
                            String comment=commentField.getText().toString();
                            final float value = rateConBar.getRating();

                            String tripInfo = RidePostFragment.startTrip + " TO " + RidePostFragment.destinationTrip;

                            final Rating rating = new Rating(
                                    MainActivity.currentUserID,   //id user
                                    value,     //nbr stars
                                    comment,    //comment
                                    tripInfo,
                                    MainActivity.iconSender);

                            if (MainActivity.currentUserID.equals(MainActivity.selectedDriverAccountID)){
                                Toast.makeText(ProfileConductorPopupActivity.this, "you can't rate your self", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ratingTbl.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                                    //Update new Value
                                    ratingTbl.push().setValue(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Rating saved", Toast.LENGTH_SHORT).show();

                                                totaleRates = ((totaleRates * dataSnapshot.getChildrenCount()) + value)/ (dataSnapshot.getChildrenCount() + 1);
                                                databaseReferenceCon.child("totalRates").setValue(totaleRates);
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //sna dir el hsab hadek w affichih
                            //set the profile rating bar
                            ratingBarProfile.setRating(rateConBar.getRating());

                            mainLayout.setVisibility(View.GONE);
                        }
                    });
                }
            });

        }



        findViewById(R.id.negativeBtnU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(), ReportPopupActivity.class);
                intent.putExtra("whichReport","user");
                startActivity(intent);
            }
        });



        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(phone.getText().toString());
            }
        });
    }

    public void callNumber(String phoneNumber){

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }

}