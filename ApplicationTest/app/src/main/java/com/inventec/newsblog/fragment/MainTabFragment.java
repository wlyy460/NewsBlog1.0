package com.inventec.newsblog.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.inventec.frame.base.MainFragment;
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
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setViewPagerAdapter(sectionsPagerAdapter);
        viewDelegate.setupWithViewPager();
    }

    @Override
    protected Class<MainTabDelegate> getDelegateClass() {
        return MainTabDelegate.class;
    }

}
