package Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.carpoolingappv1.ProfileConductorRatesFragment;
import com.example.carpoolingappv1.ProfileConductorTripsFragment;

public class PagerAdapteurRatingsTrips extends FragmentPagerAdapter {


    private int numOfTabs;

    public PagerAdapteurRatingsTrips(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProfileConductorTripsFragment profileConductorTripsFragment = new ProfileConductorTripsFragment();
                return profileConductorTripsFragment;


            case 1:
                ProfileConductorRatesFragment profileConductorRatesFragment = new ProfileConductorRatesFragment();
                return profileConductorRatesFragment;

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
                return "Trips";
            case 1:
                return "Ratings";
            default:
                return null;
        }

    }
}
