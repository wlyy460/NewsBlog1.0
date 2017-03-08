package com.inventec.newsblog.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.inventec.newsblog.R;
import com.inventec.newsblog.fragment.NewsFragment;
import com.inventec.newsblog.fragment.PicturesFragment;
import com.inventec.newsblog.fragment.WeatherFragment;

/**
 *  viewpager选项卡适配器
 * Created by Test on 2017/2/22.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        this(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewsFragment.newInstance();
            case 1:
                return PicturesFragment.newInstance();
            case 2:
                return WeatherFragment.newInstance();
            default:
                return NewsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.tab_news);
            case 1:
                return context.getResources().getString(R.string.tab_pictures);
            case 2:
                return context.getResources().getString(R.string.tab_weather);
        }
        return  null;
    }
}
