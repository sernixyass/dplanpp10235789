package Adapter;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpoolingappv1.HomeFragment;
import com.example.carpoolingappv1.R;

import java.util.List;
import java.util.zip.Inflater;

import Model.ListItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private HomeFragment context;
    private List<ListItem> listItem;
    private static final String TAG = "MyAdapter";

    public MyAdapter (HomeFragment context , List listitem){
        this.context=context;
        this.listItem=listitem;
    }


    //inflating the view
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row,viewGroup,false);
        return new ViewHolder(view);
    }


    //link viewHolder with adapter
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {
        ListItem item = listItem.get(i);
        viewHolder.startPiont.setText(item.getStartingPoint());
        viewHolder.startPiont.setText(item.getEndPoint());

        viewHolder.takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"you clicked on take button");

               // Toast.makeText(context, "Take Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"you clicked on JOIN button");

                //Toast.makeText(context, "Join Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //return the size of list
    @Override
    public int getItemCount() {
        return listItem.size();
    }


    //hold all the itemes in our list row
    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button takeButton;
        public Button joinButton;
        public TextView startPiont;
        public TextView arrivePoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView to let us attatche the view

            takeButton = itemView.findViewById(R.id.takeBut);
            joinButton = itemView.findViewById(R.id.joinBut);
            startPiont = itemView.findViewById(R.id.startTxtP);
            arrivePoint = itemView.findViewById(R.id.endTxtP);
        }
    }
}
