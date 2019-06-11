package com.example.carpoolingappv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.ListItem;

public class HomeFragment extends Fragment  {
//    implements View.OnClickListener

    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter ;
    private List<ListItem> mListItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        //view.findViewById(R.id.logoutBtnId).setOnClickListener(this);
        mRecyclerView=view.findViewById(R.id.recyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mListItems=new ArrayList<>();

        for (int i =0 ; i<10 ; i++){
            ListItem item = new ListItem(
                    "StartPoint" + (i+1),
                    "endingPoint");
            mListItems.add(item);
        }

        mAdapter = new MyAdapter(this,mListItems);
        mRecyclerView.setAdapter(mAdapter);




    return view;
    }



//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.logoutBtnId:
//                logout();
//                break;
//        }
//    }

//    private void logout() {
//        mAuth.getInstance().signOut();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//        getActivity().finish();
//    }

}
