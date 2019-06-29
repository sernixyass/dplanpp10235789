package com.example.carpoolingappv1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import Adapter.ChatListAdapter;
import Model.InstantMessage;

public class ChatFragment extends Fragment {

    private String mDisplayName;
    private String mTime ;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference ;
    private ChatListAdapter mAdapter ;

    public ChatFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_chat,container,false);

        final View view = inflater.inflate(R.layout.fragment_chat,container,false);

        mSendButton=view.findViewById(R.id.sendButton);
        mInputText =  view.findViewById(R.id.messageInput);
        mChatListView =  view.findViewById(R.id.list_msg);





        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                sendMessage();
                return true;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });





        return view ;
    }



    private void sendMessage() {

//        //Grab the text the user typed in and push the message to Firebase
//        String input = mInputText.getText().toString();
//        if (!input.equals("")) {
//            InstantMessage chat = new InstantMessage(input, mDisplayName ,mTime);
//            mDatabaseReference.child("messages").push().setValue(chat);
//            mInputText.setText("");
//        }
    }


//    //Override the onStart() lifecycle method. Setup the adapter here.
//    @Override
//    public void onStart(){
//        super.onStart();
//        mAdapter = new ChatListAdapter(getActivity(), mDatabaseReference, mDisplayName);
//        mChatListView.setAdapter(mAdapter);
//
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        //Remove the Firebase event listener on the adapter.
//        mAdapter.cleanup();
//
//    }

}
