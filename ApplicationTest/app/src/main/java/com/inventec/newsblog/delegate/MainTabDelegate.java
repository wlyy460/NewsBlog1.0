package com.inventec.newsblog.delegate;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.inventec.frame.themvp.view.AppDelegate;
import com.inventec.newsblog.R;

/**
 * 切换Tab界面的代理类
 * Created by Test on 2017/2/21.
 */

public class MainTabDelegate extends AppDelegate {
    protected ViewPager viewPager;
    protected TabLayout tabLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_base_tab;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        viewPager = get(R.id.viewpager_container);
        tabLayout = get(R.id.tabs);

    }

    /**
     * 设置ViewPager的适配器
     * @param adapter
     */
    public void setViewPagerAdapter(FragmentPagerAdapter adapter){
        viewPager.setOffscreenPageLimit(3);//设置viewpager预加载页面数
        viewPager.setPageMarginDrawable(R.drawable.icon_share);
        viewPager.setAdapter(adapter);
    }

    public void setupWithViewPager(){
        tabLayout.setupWithViewPager(viewPager, false);
    }
}
