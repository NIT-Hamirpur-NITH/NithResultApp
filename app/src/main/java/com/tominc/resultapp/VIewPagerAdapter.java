package com.tominc.resultapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by shubham on 23/8/15.
 */
public class VIewPagerAdapter extends FragmentStatePagerAdapter {
    String[] titles;
    int numTabs;

    public VIewPagerAdapter(FragmentManager fm, String[] titles, int numTabs) {
        super(fm);

        this.titles=titles;
        this.numTabs=numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new Tab1();
        } else{
            return new Tab2();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
