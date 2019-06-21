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

public class ProfileConductorFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton buttonConducEditProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_profile_conductor,container,false);
        View view = inflater.inflate(R.layout.fragment_profile_conductor,container,false);


        view.findViewById(R.id.drop_down_option_menu);

        /*buttonConducEditProfile = view.findViewById(R.id.btn_add_Post);
        buttonConducEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_profile_Conductor.class));
            }
        });*/

        return view ;
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
