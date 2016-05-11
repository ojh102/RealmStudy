package com.example.inno_09.realmstudy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by INNO-09 on 2016-05-11.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FirstFragment.newInstance();
                break;
            case 1:
                fragment = SecondFragment.newInstance();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title="";

        switch (position) {
            case 0:
                title = "Realm의 기본";
                break;
            case 1:
                title = "Realm의 관계";
                break;
        }

        return title;
    }
}
