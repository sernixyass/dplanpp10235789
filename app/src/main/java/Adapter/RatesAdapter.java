package Adapter;

import android.app.NotificationChannel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.carpoolingappv1.HomeFragment;
import com.example.carpoolingappv1.R;
import com.example.carpoolingappv1.carpoolingappv1;

import java.util.List;
import java.util.Objects;

import Model.Rating;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> implements View.OnClickListener {

    private HomeFragment context;
    private List<Rating> ratesItems;

    public RecyclerView recyclerView;

    public RatesAdapter(HomeFragment context, List<Rating> ratesItems) {
        this.context = context;
        this.ratesItems = ratesItems;
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public RatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rate_row,viewGroup,false);


        recyclerView = view.findViewById(R.id.ratesRecyclerViewId);


        return new RatesAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RatesAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return ratesItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tripInfo,comment;
        public ImageView icon;
        RatingBar rate;

        public ViewHolder(@NonNull View view)
        {
            super(view);

            tripInfo = view.findViewById(R.id.rateInfo);
            comment = view.findViewById(R.id.rateComment);
            icon = view.findViewById(R.id.rateIcon);
            rate = view.findViewById(R.id.rateStars);
        }

        public void setIcon(String mm){
            if (!Objects.equals(mm, "") ){
                //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
                if (carpoolingappv1.getAppContext()!=null){
                    Glide.with(carpoolingappv1.getAppContext()).load(mm)
                            .apply(RequestOptions.circleCropTransform())
                            .into(icon);
                }
            }
        }

        public void setTripInfo(String tripInfo) {
            this.tripInfo.setText(tripInfo);
        }

        public void setComment(String comment) {
            this.comment.setText(comment);
        }


        public void setRate(Double rate) {
            this.rate.setRating(rate.floatValue());
        }

    }
}
