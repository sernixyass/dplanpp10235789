package com.example.carpoolingappv1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProfileConductorTripsFragment extends Fragment {

    public ProfileConductorTripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile_conductor_rates, container, false);
        final View view = inflater.inflate(R.layout.fragment_profile_conductor_trips, container, false);



        return view;
    }
}
