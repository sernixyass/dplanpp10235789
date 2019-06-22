package com.example.carpoolingappv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePassengerFragment extends Fragment implements View.OnClickListener{


    private FloatingActionButton buttonPassEditProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String userID;
    private TextView fullName,fullNameGrand,email,phone,birthDate,willaya;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
       // return inflater.inflate(R.layout.fragment_profile_passenger,container,false);
        final View view = inflater.inflate(R.layout.fragment_profile_passenger,container,false);



        view.findViewById(R.id.add_friend).setOnClickListener(this);
        buttonPassEditProfile = view.findViewById(R.id.edit_pass_profile);
        buttonPassEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_profile_Passenger.class));
            }
        });

        //link

        fullNameGrand = view.findViewById(R.id.user_big_profile_name);
        fullName = view.findViewById(R.id.pass_full_name);
        email = view.findViewById(R.id.pass_email);
        birthDate = view.findViewById(R.id.pass_birth_date);
        phone = view.findViewById(R.id.pass_phone);
        willaya = view.findViewById(R.id.pass_wilaya);


        mAuth = FirebaseAuth.getInstance();
        //retrieve data
        userID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        view.findViewById(R.id.drop_down_option_menu);

        buttonPassEditProfile = view.findViewById(R.id.btn_add_Post);
        buttonPassEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_profile_Passenger.class));
            }
        });

        return view ;
    }


    private void showData(DataSnapshot dataSnapshot) {
        fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        fullNameGrand.setText(dataSnapshot.child("fullName").getValue().toString());
        email.setText(dataSnapshot.child("email").getValue().toString());
        phone.setText(dataSnapshot.child("phone").getValue().toString());
        birthDate.setText(dataSnapshot.child("bDate").getValue().toString());
        willaya.setText(dataSnapshot.child("wilaya").getValue().toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_friend:
                logout();
                break;
        }
    }



    private void logout() {
        MainActivity.mAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}