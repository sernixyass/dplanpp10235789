package Adapter;

import android.app.NotificationChannel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.carpoolingappv1.HomeFragment;
import com.example.carpoolingappv1.R;
import com.example.carpoolingappv1.carpoolingappv1;


import java.util.List;
import java.util.Objects;

import Model.ChatRoomModel;
import Model.ListItem;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> implements View.OnClickListener {

    private HomeFragment context;
    private List<ChatRoomModel> chatRoomsItems;

    public RecyclerView recyclerView;


    public ChatRoomAdapter(HomeFragment context, List<ChatRoomModel> chatRoomItems) {
        this.context = context;
        this.chatRoomsItems = chatRoomItems;
    }

    /*
        @NonNull
        @Override
        public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.notification_row,viewGroup,false);


            recyclerView = view.findViewById(R.id.notificationRecyclerViewId);


            return new RecyclerView.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return notificationItems.size();
        }
    */
    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public ChatRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_room_row,viewGroup,false);


        recyclerView = view.findViewById(R.id.chatRoomRecyclerViewId);


        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return chatRoomsItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title,message;
        public ImageView icon;

        public ViewHolder(@NonNull View view)
        {
            super(view);

            title = view.findViewById(R.id.chatRoomTitle);

        }

        public TextView getTitle() {
            return title;
        }

  /*      public void setIcon(String mm){
            if (!Objects.equals(mm, "") ){
                //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                if (carpoolingappv1.getAppContext()!=null){
                    Glide.with(carpoolingappv1.getAppContext()).load(mm)
                            .apply(RequestOptions.circleCropTransform())
                            .into(icon);
                }
            }
        }
*/
        public void setTitle(String title) {
            this.title.setText(title);
        }

    }
}
