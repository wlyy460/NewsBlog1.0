package com.inventec.newsblog.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.inventec.frame.base.MainFragment;
import com.inventec.newsblog.R;
import com.inventec.newsblog.adapter.SectionsPagerAdapter;
import com.inventec.newsblog.delegate.MainTabDelegate;

import rx.Subscription;

/**
 *  新闻图片天气切换页的主界面
 * @author
 */
public class MainTabFragment extends MainFragment<MainTabDelegate> {
    private Subscription cacheSubscript;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private Context context;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.context = getActivity();
        sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(), context);
        sectionsPagerAdapter.addTab(NewsFragment.newInstance(),
                context.getResources().getString(R.string.tab_news));
        sectionsPagerAdapter.addTab(PicturesFragment.newInstance(),
                context.getResources().getString(R.string.tab_pictures));
        sectionsPagerAdapter.addTab(WeatherFragment.newInstance(),
                context.getResources().getString(R.string.tab_weather));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setViewPagerAdapter(sectionsPagerAdapter);
        viewDelegate.setupWithViewPager();
        final ViewPager viewPager = viewDelegate.get(R.id.viewpager_container);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {

            }
            @Override
            public void onPageScrollStateChanged(int i) {
              /*  if (KeyboardUtil.isKeyboardOpened(context)){
                    KeyboardUtil.closeKeyboard(context, viewPager);
                }*/
            }
        });
    }

    @Override
    protected Class<MainTabDelegate> getDelegateClass() {
        return MainTabDelegate.class;
    }

}
