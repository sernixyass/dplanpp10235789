package com.example.carpoolingappv1.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.carpoolingappv1.R;

import java.util.ArrayList;
import java.util.List;

import Model.ListItem;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.MySearchAdapterViewHolder> {

    public Context c;
    public List<ListItem> arrayList;

    public RecyclerView recyclerView;

    public MySearchAdapter(Context c,   List<ListItem> arrayList){
        this.c = c;
        this.arrayList = arrayList;

    }


    @Override
    public MySearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row,viewGroup,false);
        recyclerView = v.findViewById(R.id.recyclerViewSearchId);

        return new MySearchAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySearchAdapterViewHolder mySearchAdapterViewHolder, int i) {
        ListItem listItem = arrayList.get(i);

        mySearchAdapterViewHolder.startPiont.setText(listItem.getStartingPoint());
        mySearchAdapterViewHolder.arrivePoint.setText(listItem.getEndPoint());
        mySearchAdapterViewHolder.places.setText(listItem.getPlaces());


        //mySearchAdapterViewHolder.cardViewRow.setOnClickListener(this);

        //mySearchAdapterViewHolder.takeButton.setOnClickListener(this);

        //mySearchAdapterViewHolder.joinButton.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class MySearchAdapterViewHolder extends RecyclerView.ViewHolder {

        public Button takeButton;
        public Button joinButton;
        public TextView startPiont;
        public TextView arrivePoint;
        public TextView places;
        public CardView cardViewRow;

        public MySearchAdapterViewHolder(View itemView){
            super(itemView);

            takeButton = itemView.findViewById(R.id.takeBut);
            joinButton = itemView.findViewById(R.id.joinBut);
            startPiont = itemView.findViewById(R.id.startTxtP);
            arrivePoint = itemView.findViewById(R.id.endTxtP);
            places = itemView.findViewById(R.id.nbr_passenger_places);
            cardViewRow = itemView.findViewById(R.id.cardView);
        }
    }


}
