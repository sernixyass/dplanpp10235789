package Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.carpoolingappv1.ChatFragment;
import com.example.carpoolingappv1.NotificationFragment;


public class PagerAdapterChatNotification extends FragmentPagerAdapter {


    private int numOfTabs;


    public PagerAdapterChatNotification(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChatFragment();
            case 1:
                return new NotificationFragment();
            default:
                return null;
        }

    }


    @Override
    public int getCount() {
        return numOfTabs;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "CHAT";
            case 1:
                return "Notification";
            default:
                return null;
        }

    }
}
