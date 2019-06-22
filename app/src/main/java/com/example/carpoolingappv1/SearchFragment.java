package com.example.carpoolingappv1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carpoolingappv1.util.MySearchAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Adapter.MyAdapter;
import Model.ListItem;

public class SearchFragment extends Fragment {


    private RecyclerView mRecyclerView;


    private FirebaseRecyclerAdapter<ListItem, MySearchAdapter.MySearchAdapterViewHolder> adapterFire;

    FirebaseRecyclerOptions<ListItem> options;

    DatabaseReference databaseReference;

    EditText searchFieldS;

    List<ListItem> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_search,container,false);
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        //arrayList = new ArrayList<>();


        mRecyclerView = view.findViewById(R.id.recyclerViewSearchId);
        mRecyclerView.setHasFixedSize(true);

        searchFieldS = view.findViewById(R.id.searchText);

        searchFieldS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    //Toast.makeText(getContext(),"hhhhhh  "+ s,Toast.LENGTH_SHORT).show();
                    search(s.toString());
                }else {
                    search("");
                }

            }
        });











        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");

        options = new FirebaseRecyclerOptions.Builder<ListItem>()
                .setQuery(databaseReference,ListItem.class)
                .build();

        adapterFire= new FirebaseRecyclerAdapter<ListItem, MySearchAdapter.MySearchAdapterViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MySearchAdapter.MySearchAdapterViewHolder holder, int position, @NonNull ListItem model) {
                holder.startPiont.setText(model.getStartingPoint());

                holder.arrivePoint.setText(model.getEndPoint());

                holder.places.setText((model.getPlaces()).toString());


                //holder.cardViewRow.setOnClickListener(this);

                //holder.takeButton.setOnClickListener(this);

                //holder.joinButton.setOnClickListener(this);
            }

            @Override
            public MySearchAdapter.MySearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_row,viewGroup,false);


                //mRecyclerView = view.findViewById(R.id.recyclerViewSearchId);
                return new MySearchAdapter.MySearchAdapterViewHolder(view);
            }
        };


        adapterFire.startListening();
        mRecyclerView.setAdapter(adapterFire);



        return view;
    }

    private void search(String s) {
        Query query = databaseReference.orderByChild("startingPoint")
                .startAt(s)
                .endAt(s + "\uf8ff");

        FirebaseRecyclerOptions<ListItem> options =
                new FirebaseRecyclerOptions.Builder<ListItem>()
                        .setQuery(query, new SnapshotParser<ListItem>() {
                            @NonNull
                            @Override
                            public ListItem parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new ListItem(snapshot.child("startingPoint").getValue().toString(),
                                        snapshot.child("endingPoint").getValue().toString(),
                                        snapshot.child("places").getValue(Integer.class),
                                        snapshot.child("id").getValue().toString(),
                                        snapshot.child("tripPos").getValue(LatLng.class),
                                        snapshot.child("tripDesPos").getValue(LatLng.class),
                                        snapshot.child("accountIDPostedIt").getValue().toString(),


                                        snapshot.child("saturday").getValue(Boolean.class),
                                        snapshot.child("sunday").getValue(Boolean.class),
                                        snapshot.child("monday").getValue(Boolean.class),
                                        snapshot.child("tuesday").getValue(Boolean.class),
                                        snapshot.child("wednesday").getValue(Boolean.class),
                                        snapshot.child("thursday").getValue(Boolean.class),
                                        snapshot.child("friday").getValue(Boolean.class),

                                        snapshot.child("isTaken").getValue(Boolean.class),
                                        snapshot.child("isFull").getValue(Boolean.class),

                                        snapshot.child("accountIDTakedIt").getValue().toString(),

                                        snapshot.child("accountIDJoining1").getValue().toString(),
                                        snapshot.child("accountIDJoining2").getValue().toString(),
                                        snapshot.child("accountIDJoining3").getValue().toString(),
                                        snapshot.child("accountIDJoining4").getValue().toString(),

                                        snapshot.child("hourTrip").getValue().toString()
                                );
                            }
                        })
                        .build();

        Toast.makeText(getContext(),"ffffff  "+ s,Toast.LENGTH_SHORT).show();

        adapterFire= new FirebaseRecyclerAdapter<ListItem, MySearchAdapter.MySearchAdapterViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MySearchAdapter.MySearchAdapterViewHolder holder, int position, @NonNull ListItem model) {
                holder.startPiont.setText(model.getStartingPoint());

                holder.arrivePoint.setText(model.getEndPoint());

                //holder.places.setText(model.getPlaces());


                //holder.cardViewRow.setOnClickListener(this);

                //holder.takeButton.setOnClickListener(this);

                //holder.joinButton.setOnClickListener(this);
            }

            @Override
            public MySearchAdapter.MySearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_row,viewGroup,false);


                //mRecyclerView = view.findViewById(R.id.recyclerViewSearchId);
                return new MySearchAdapter.MySearchAdapterViewHolder(view);
            }
        };


        adapterFire.startListening();
        mRecyclerView.setAdapter(adapterFire);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {

                    //arrayList.clear();
                    for(DataSnapshot dss: dataSnapshot.getChildren()){
                        final ListItem listItem = dss.getValue(ListItem.class);
                        //arrayList.add(listItem);
                    }
                    /*MySearchAdapter mySearchAdapter = new MySearchAdapter(getContext(),arrayList);
                    mRecyclerView.setAdapter(mySearchAdapter);
                    mySearchAdapter.notifyDataSetChanged();*/

                    adapterFire.startListening();
                    mRecyclerView.setAdapter(adapterFire);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
