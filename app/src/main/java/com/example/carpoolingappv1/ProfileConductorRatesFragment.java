package com.example.carpoolingappv1;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import Adapter.NotificationAdapter;
import Adapter.RatesAdapter;
import Model.NotificationModel;
import Model.Rating;


public class ProfileConductorRatesFragment extends Fragment {


    public RecyclerView mRecyclerView;
    public List<Rating> mRatesItems;
    public FirebaseRecyclerAdapter rateAdapterFire;


    public ProfileConductorRatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile_conductor_rates, container, false);
        final View view = inflater.inflate(R.layout.fragment_profile_conductor_rates, container, false);


        mRecyclerView = view.findViewById(R.id.ratesRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRatesItems = new ArrayList<>();


        fetch();



        return view;
    }


    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users").child(MainActivity.currentUserID).child("rates");


        FirebaseRecyclerOptions<Rating> options =
                new FirebaseRecyclerOptions.Builder<Rating>()
                        .setQuery(query, new SnapshotParser<Rating>() {
                            @NonNull
                            @Override
                            public Rating parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Rating(
                                        snapshot.child("userId").getValue().toString(),
                                        snapshot.child("rateValue").getValue(double.class),
                                        snapshot.child("comment").getValue().toString(),
                                        snapshot.child("tripInfo").getValue().toString(),
                                        snapshot.child("iconSender").getValue().toString()
                                );
                            }
                        })
                        .build();


        rateAdapterFire = new FirebaseRecyclerAdapter<Rating, RatesAdapter.ViewHolder>(options) {

            @Override
            public RatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rate_row, parent, false);

                return new RatesAdapter.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RatesAdapter.ViewHolder holder, int position, @NonNull Rating model) {
                holder.setComment(model.getComment());
                holder.setRate(model.getRateValue());
                holder.setTripInfo(model.getTripInfo());
                holder.setIcon(model.getIconSender());
            }



        };
        mRecyclerView.setAdapter(rateAdapterFire);

    }


    @Override
    public void onStart() {
        super.onStart();
        //mMapView.onStart();
        rateAdapterFire.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mMapView.onStop();
        rateAdapterFire.stopListening();


    }


}
