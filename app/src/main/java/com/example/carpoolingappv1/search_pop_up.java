package com.example.carpoolingappv1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Adapter.MyAdapter;
import Model.ListItem;


public class search_pop_up extends Activity {
    private Button confirme;
    private DatabaseReference mRideReference ;
    private EditText searchBar;
    private FirebaseRecyclerAdapter adapterFireS;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pop_up);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);



        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.95),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x= 0 ;
        params.y=-20;


        getWindow().setAttributes(params);



        //confirme = findViewById(R.id.confirm_search);

        searchBar = findViewById(R.id.search_field01);
        mRideReference = FirebaseDatabase.getInstance().getReference("posts");


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
                Toast.makeText(carpoolingappv1.getAppContext(),"SS:"+s.toString(),Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void filter (String text){
        Query fireSearchQuery = mRideReference.orderByChild("posts").startAt(text).endAt(text+ "\uf8ff");

        FirebaseRecyclerOptions<ListItem> options = new FirebaseRecyclerOptions.Builder<ListItem>()
                .setQuery(fireSearchQuery, ListItem.class)
                .build();

        adapterFireS = new FirebaseRecyclerAdapter<ListItem, MyAdapter.ViewHolder>(options) {
            @NonNull
            @Override
            public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_row, viewGroup, false);

                return new MyAdapter.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position, @NonNull ListItem model) {
                holder.setStartPiont(model.getStartingPoint());
                holder.setArrivePoint(model.getEndPoint());
                holder.setPlaces(model.getPlaces(),model.getTotalPlaces());

                holder.cardViewRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

                        Fragment fra;
                        fra = new RidePostFragment();


                        //getFragmentManager().beginTransaction().add(R.id.pop_up_post_container, fra).commit();
                    }
                });            }
        } ;
        mRecyclerView.setAdapter(adapterFireS);
        adapterFireS.startListening();

  /*      ArrayList<ListItem> filtedSearch =new ArrayList<>();

        for (String s :)*/

    }
}
