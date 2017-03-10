package com.inventec.newsblog.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *  viewpager选项卡适配器
 * Created by Test on 2017/2/22.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private List<String> titles = new ArrayList<String>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        this(fm);
        this.context = context;
    }

    public void addTab(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  titles.get(position);
    }
}
