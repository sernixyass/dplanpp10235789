package com.example.carpoolingappv1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Adapter.NotificationAdapter;
import Model.ListItem;
import Model.NotificationModel;

public class NotificationFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public List<NotificationModel> mNotificationItems;
    public FirebaseRecyclerAdapter notAdapterFire;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_notification,container,false);
        final View view = inflater.inflate(R.layout.fragment_notification, container, false);


        //view.findViewById(R.id.logoutBtnId).setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.notificationRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mNotificationItems = new ArrayList<>();


        fetch();


        return view;
    }


    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users").child(MainActivity.currentUserID).child("notification");


        FirebaseRecyclerOptions<NotificationModel> options =
                new FirebaseRecyclerOptions.Builder<NotificationModel>()
                        .setQuery(query, new SnapshotParser<NotificationModel>() {
                            @NonNull
                            @Override
                            public NotificationModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new NotificationModel(
                                        snapshot.child("title").getValue().toString(),
                                        snapshot.child("message").getValue().toString()
                                );
                            }
                        })
                        .build();


        notAdapterFire = new FirebaseRecyclerAdapter<NotificationModel, NotificationAdapter.ViewHolder>(options) {

            @Override
            public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.notification_row, parent, false);

                return new NotificationAdapter.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position, @NonNull NotificationModel model) {

                holder.setTitle(model.getTitle());
                holder.setMessage(model.getBodyMessage());


            }



        };
        mRecyclerView.setAdapter(notAdapterFire);

    }


    @Override
    public void onStart() {
        super.onStart();
        //mMapView.onStart();
        notAdapterFire.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mMapView.onStop();
        notAdapterFire.stopListening();


    }
}
