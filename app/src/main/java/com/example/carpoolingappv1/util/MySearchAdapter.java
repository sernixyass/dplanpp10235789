package com.example.carpoolingappv1.util;

import android.content.Context;
import android.graphics.Typeface;
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

        public Button ActionButton;
        public TextView startPiont;
        public TextView arrivePoint;
        public TextView places;
        public CardView cardViewRow;
        public TextView conductor;
        public TextView hourTrip;
        public TextView tripDate;

        public TextView satTxt,sunTxt,monTxt,tueTxt,wedTxt,thuTxt,friTxt;

        public MySearchAdapterViewHolder(View itemView){
            super(itemView);

//            actionButton = itemView.findViewById(R.id.actionBtn);
            startPiont = itemView.findViewById(R.id.startTxtP);
            arrivePoint = itemView.findViewById(R.id.endTxtP);
            places = itemView.findViewById(R.id.nbr_passenger_places);
            cardViewRow = itemView.findViewById(R.id.cardView);
            conductor = itemView.findViewById(R.id.conductorSign);
            hourTrip = itemView.findViewById(R.id.hourTripID);
            tripDate = itemView.findViewById(R.id.trip_date);

            satTxt = itemView.findViewById(R.id.satID);
            sunTxt = itemView.findViewById(R.id.sunID);
            monTxt = itemView.findViewById(R.id.monID);
            tueTxt = itemView.findViewById(R.id.tueID);
            wedTxt = itemView.findViewById(R.id.wedID);
            thuTxt = itemView.findViewById(R.id.thuID);
            friTxt = itemView.findViewById(R.id.friID);

        }

        public TextView getSatTxt() {
            return satTxt;
        }

        public void setSatTxt(int color) {
            this.satTxt.setTypeface(Typeface.DEFAULT_BOLD);
            this.satTxt.setTextColor(color);
        }
        public void setSunTxt(int color) {
            this.sunTxt.setTypeface(Typeface.DEFAULT_BOLD);
            this.sunTxt.setTextColor(color);
        }
        public void setMonTxt(int color) {
            this.monTxt.setTypeface(Typeface.DEFAULT_BOLD);
            this.monTxt.setTextColor(color);
        }
        public void setTueTxt(int color) {
            this.tueTxt.setTypeface(Typeface.DEFAULT_BOLD);
            this.tueTxt.setTextColor(color);
        }
        public void setWedTxt(int color) {
            this.wedTxt.setTypeface(Typeface.DEFAULT_BOLD);
            this.wedTxt.setTextColor(color);
        }
        public void setThuTxt(int color) {
            this.thuTxt.setTypeface(Typeface.DEFAULT_BOLD);
            this.thuTxt.setTextColor(color);
        }
        public void setFriTxt(int color) {
            this.friTxt.setTypeface(Typeface.DEFAULT_BOLD);
            this.friTxt.setTextColor(color);
        }


        public TextView getConductor() {
            return conductor;
        }

        public void setConductor(String conductor) {
            this.conductor.setText(conductor);
        }

        public TextView getPlaces() {
            return places;
        }

        public void setPlaces(Integer places,Integer totalPlaces) {
            this.places.setText(places.toString()+"/"+totalPlaces.toString());
        }

        public void setHourTrip(String hourTrip) {
            this.hourTrip.setText(hourTrip);
        }

        public void setTripDate(String tripDate) {
            this.tripDate.setText(tripDate);
        }

        public Button getTakeButton() {
            return ActionButton;
        }

        public void setTakeButton(Button takeButton) {
            this.ActionButton = takeButton;
        }


        public TextView getStartPiont() {
            return startPiont;
        }

        public void setStartPiont(String startPiont) {
            this.startPiont.setText(startPiont);
        }

        public TextView getArrivePoint() {
            return arrivePoint;
        }

        public void setArrivePoint(String arrivePoint) {
            this.arrivePoint.setText(arrivePoint);
        }

        public CardView getCardViewRow() {
            return cardViewRow;
        }

        public void setCardViewRow(CardView cardViewRow) {
            this.cardViewRow = cardViewRow;
        }

        public void setDetails(String startingPoint, String endPoint, Integer places) {

            TextView startPiont = itemView.findViewById(R.id.startTxtP);
            TextView arrivePoint = itemView.findViewById(R.id.endTxtP);
            TextView placesNbe=itemView.findViewById(R.id.nbr_passenger_places);

            startPiont.setText(startingPoint);
            arrivePoint.setText(endPoint);
            placesNbe.setText(places);

        }

        public void DateTripNeeds(){
            itemView.findViewById(R.id.weekLL).setVisibility(View.GONE);
            itemView.findViewById(R.id.dateLL).setVisibility(View.VISIBLE);
        }
    }
}