package com.example.carpoolingappv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String userID;

    TextView fullName,fullNameGrand,email,phone;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_profile,container,false);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        view.findViewById(R.id.add_friend).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        //link
        fullName = view.findViewById(R.id.fullName_profile);
        fullNameGrand = view.findViewById(R.id.fullNameGrand_profile);
        email = view.findViewById(R.id.email_profile);
        phone = view.findViewById(R.id.phone_profile);



//        logoutBtn = view.findViewById(R.id.P_LogoutBtn);
//        logoutBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

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
    }

    private void showData(DataSnapshot dataSnapshot) {
        fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        fullNameGrand.setText(dataSnapshot.child("fullName").getValue().toString());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.add_friend):
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