package Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.carpoolingappv1.HomeFragment;
import com.example.carpoolingappv1.R;
import com.example.carpoolingappv1.RidePostFragment;

import java.util.List;

import Model.ListItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private HomeFragment context;
    private List<ListItem> listItem;
    private static final String TAG = "MyAdapter";

    //map
    //private RelativeLayout mMapContainer;
    //private RelativeLayout mPostsContainer;


    //HomeFragment homeFraClass = new HomeFragment();

    public RecyclerView recyclerView;




    public MyAdapter (HomeFragment context , List listitem){
        this.context=context;
        this.listItem=listitem;
    }


    public MyAdapter(List<ListItem> listData) {
        this.listItem = listData;
    }

    //inflating the view
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row,viewGroup,false);


        recyclerView = view.findViewById(R.id.recyclerViewId);


        //mMapContainer = (RelativeLayout) view.findViewById(R.id.map_container);
        //mPostsContainer = (RelativeLayout) view.findViewById(R.id.posts_Container);

        return new ViewHolder(view);
    }


    //link viewHolder with adapter
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {

        ListItem item = listItem.get(i);
        viewHolder.startPiont.setText(item.getStartingPoint());
        viewHolder.arrivePoint.setText(item.getEndPoint());

        viewHolder.cardViewRow.setOnClickListener(this);

        viewHolder.takeButton.setOnClickListener(this);

        viewHolder.joinButton.setOnClickListener(this);




    }


    //return the size of list
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardView:{

                Log.d(TAG,"you clicked on ITEEEEEEEEEEEEEEEEEM");

                Fragment fra;
                fra = new RidePostFragment();


                context.getFragmentManager().beginTransaction().add(R.id.fragment_Post_container,
                        fra).commit();



                //context.startActivity(new Intent(context.getActivity(), RidePostActivity.class));



                //animation
                /*if(HomeFragment.mMapLayoutState == HomeFragment.MAP_LAYOUT_STATE_CONTRACTED){
                    HomeFragment.mMapLayoutState = HomeFragment.MAP_LAYOUT_STATE_EXPANDED;
                    HomeFragment.expandMapAnimation();
                }
                else if(HomeFragment.mMapLayoutState == HomeFragment.MAP_LAYOUT_STATE_EXPANDED){
                    HomeFragment.mMapLayoutState = HomeFragment.MAP_LAYOUT_STATE_CONTRACTED;
                    HomeFragment.contractMapAnimation();
                }*/
                break;
            }
            case R.id.joinBut:{

                Log.d(TAG,"you clicked on JOIN button");

                break;
            }
            case R.id.takeBut:{
                Log.d(TAG,"you clicked on take button");


                HomeFragment.showSomething();
                //final Dialog dialog = new Dialog(getActivity());
                //dialog.setContentView(R.layout.list_row);
                break;
            }
        }
    }


    //hold all the itemes in our list row
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button takeButton;
        public Button joinButton;
        public TextView startPiont;
        public TextView arrivePoint;
        public CardView cardViewRow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView to let us attatche the view

            takeButton = itemView.findViewById(R.id.takeBut);
            joinButton = itemView.findViewById(R.id.joinBut);
            startPiont = itemView.findViewById(R.id.startTxtP);
            arrivePoint = itemView.findViewById(R.id.endTxtP);
            cardViewRow = itemView.findViewById(R.id.cardView);
        }

        public Button getTakeButton() {
            return takeButton;
        }

        public void setTakeButton(Button takeButton) {
            this.takeButton = takeButton;
        }

        public Button getJoinButton() {
            return joinButton;
        }

        public void setJoinButton(Button joinButton) {
            this.joinButton = joinButton;
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
    }
}
