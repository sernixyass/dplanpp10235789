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

public class ProfilePassengerFragment extends Fragment {


    private FloatingActionButton buttonPassEditProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
       // return inflater.inflate(R.layout.fragment_profile_passenger,container,false);
        final View view = inflater.inflate(R.layout.fragment_profile_passenger,container,false);



        buttonPassEditProfile = view.findViewById(R.id.btn_add_Post);
        buttonPassEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_profile_Passenger.class));
            }
        });


      return view;
    }
}
