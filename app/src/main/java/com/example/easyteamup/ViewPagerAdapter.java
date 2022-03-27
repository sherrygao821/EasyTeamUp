package com.example.easyteamup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.easyteamup.ui.EventHistoryFragment;
import com.example.easyteamup.ui.MyEventFragment;

/**
 * View pager adapter for the profile page
 * @author Lucy Shi
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new EventHistoryFragment();
            case 1: return new MyEventFragment();
            default: return new EventHistoryFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){

        String title = null;
        if (position == 0) {title = "EVENT HISTORY";}
        else if (position == 1) {title = "MY EVENT";}

        return title;
    }
}
