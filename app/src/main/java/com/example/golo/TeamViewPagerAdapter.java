package com.example.golo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListNames = new ArrayList<>();

    public TeamViewPagerAdapter(FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) { return fragmentList.get(position); }

    @Override
    public int getCount() { return fragmentList.size(); }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentListNames.get(position);
    }

    public void AddFragment(Fragment fragment, String name){
        fragmentList.add(fragment);
        fragmentListNames.add(name);
    }
}
