package com.totyrora.fishcalc4;

import android.content.Context;
//import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by thomas on 15-07-08.
 *
 * Fish Estimation
 *
 */
public class FishFragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private final String[] tabTitles = new String[] {"Tab1", "Tab2" };
    private final Context context;

    public FishFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        // Create fragments based on item position
        // TODO create class at init to save memory
        switch (position) {
            case 0:
                return new WeightFragment();
            case 1:
                return new HealthFragment();
        }
        return null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];

    }
}
