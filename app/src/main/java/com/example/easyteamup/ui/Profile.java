package com.example.easyteamup.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.easyteamup.ChangeProfile;
import com.example.easyteamup.R;
import com.example.easyteamup.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * Profile page
 * @author Lucy Shi
 */
public class Profile extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    Button changeProfileButton;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        changeProfileButton = view.findViewById(R.id.change_profile);
        changeProfileButton.setOnClickListener(this::onClick);
        // TODO: add onClick
        return view;
    }

    /**
     * Switch to change profile page
     * @param v
     * @author Lucy Shi
     */
    private void onClick(View v) {
        Intent intent = new Intent(getActivity(), ChangeProfile.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}