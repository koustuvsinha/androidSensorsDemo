package com.koustuvsinha.testsensors.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.koustuvsinha.testsensors.view.DisplayRawSensorData;
import com.koustuvsinha.testsensors.view.DisplaySensorHistoryData;

import java.util.ArrayList;

/**
 * Created by koustuv on 19/5/15.
 */
public class ViewerPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments;

    public ViewerPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fr = fragments.get(position);
        if(fr.getClass().equals(DisplayRawSensorData.class)) {
            return DisplayRawSensorData.PAGE_NAME;
        } else {
            return DisplaySensorHistoryData.PAGE_NAME;
        }
    }
}
