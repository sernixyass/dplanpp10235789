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
import Model.ChatRoomModel;
import Model.NotificationModel;

public class ChatRoomsFragment extends Fragment {


    public RecyclerView mRecyclerView;
    public List<ChatRoomModel> mChatRoomItems;
    public FirebaseRecyclerAdapter chatRoomAdapterFire;

    public ChatRoomsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_rooms, container, false);

        mRecyclerView = view.findViewById(R.id.chatRoomRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mChatRoomItems = new ArrayList<>();


        fetch();

        return view;
    }




    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("messages").child(MainActivity.currentUserID).child("notification");


        FirebaseRecyclerOptions<NotificationModel> options =
                new FirebaseRecyclerOptions.Builder<NotificationModel>()
                        .setQuery(query, new SnapshotParser<NotificationModel>() {
                            @NonNull
                            @Override
                            public NotificationModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new NotificationModel(
                                        snapshot.child("title").getValue().toString(),
                                        snapshot.child("message").getValue().toString(),
                                        snapshot.child("iconUserID").getValue().toString()
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
                holder.setIcon(model.getIconUserID());
            }



        };
        mRecyclerView.setAdapter(notAdapterFire);

    }


    @Override
    public void onStart() {
        super.onStart();
        //mMapView.onStart();
        chatRoomAdapterFire.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mMapView.onStop();
        chatRoomAdapterFire.stopListening();


    }














}