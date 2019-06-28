package com.example.carpoolingappv1;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Adapter.PagerAdapterChatNotification;


public class ChatNotfFragment extends Fragment {

  private   TabLayout tabLayoutChatNotf;
  private   ViewPager mViewPager ;
  private TabItem chatTab ,NotficationTab , groupTab ;


    public ChatNotfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_chat_notf,container,false);


        //ViewPager
        tabLayoutChatNotf = view.findViewById(R.id.tab_layout_chat);
        mViewPager=view.findViewById(R.id.view_pager_chat_tab);
        NotficationTab = view.findViewById(R.id.notification_tab);
        chatTab =view.findViewById(R.id.chat_tab);




        setUpViewPageAdapter(mViewPager);
        tabLayoutChatNotf.setupWithViewPager(mViewPager);


        return view;

    }
    public void setUpViewPageAdapter (ViewPager viewPager){
        PagerAdapterChatNotification Adapter = new PagerAdapterChatNotification(getActivity().getSupportFragmentManager(),tabLayoutChatNotf.getTabCount());
        viewPager.setAdapter(Adapter);


    }


}
