package com.dargoz.jetpack.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final TabLayout tabLayout;

    ViewPagerAdapter(@NonNull FragmentManager fragmentManager, TabLayout tabLayout){
        super(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabLayout = tabLayout;
    }
    void addFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        tabLayout.addTab(tabLayout.newTab().setText(title));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
