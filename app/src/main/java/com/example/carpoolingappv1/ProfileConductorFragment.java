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

public class ProfileConductorFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton buttonConducEditProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String userID;
    TextView fullName,fullNameGrand,email,phone,birthDate,willaya,carModel,carNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_profile_conductor,container,false);
        View view = inflater.inflate(R.layout.fragment_profile_conductor,container,false);


        //link

        fullNameGrand = view.findViewById(R.id.con_big_profile_name);
        //fullName = view.findViewById(R.id.con_full_name);
        //email = view.findViewById(R.id.con_email);
        //birthDate = view.findViewById(R.id.con_birth_date);
        phone = view.findViewById(R.id.con_phone);
        willaya = view.findViewById(R.id.con_wilaya);
        carModel = view.findViewById(R.id.con_car_model);
        carNumber = view.findViewById(R.id.con_car_unique_nbe);



       // view.findViewById(R.id.add_friend).setOnClickListener(this);
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


        //view.findViewById(R.id.drop_down_option_menu).setOnClickListener(this);

        buttonConducEditProfile = view.findViewById(R.id.edit_con_profile);
        buttonConducEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_profile_Conductor.class));
            }
        });

        return view ;
    }


    private void showData(DataSnapshot dataSnapshot) {
        //fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        fullNameGrand.setText(dataSnapshot.child("fullName").getValue().toString());
        //email.setText(dataSnapshot.child("email").getValue().toString());
        phone.setText(dataSnapshot.child("phone").getValue().toString());
        //birthDate.setText(dataSnapshot.child("bDate").getValue().toString());
        willaya.setText(dataSnapshot.child("wilaya").getValue().toString());
        carModel.setText(dataSnapshot.child("carModel").getValue().toString());
        carNumber.setText(dataSnapshot.child("carKey").getValue().toString());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.drop_down_option_menu:
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
