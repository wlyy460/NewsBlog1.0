package com.inventec.newsblog.delegate;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuDrawable.Stroke;
import com.inventec.frame.themvp.view.AppDelegate;
import com.inventec.newsblog.R;
import com.inventec.newsblog.utils.KJAnimations;

/**
 * @author kymjs (http://www.kymjs.com/) on 11/6/15.
 */
public class MainDelegate extends AppDelegate {
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    FloatingActionButton mFab;
    MainDrawerListener mainDrawerListener;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Toolbar getToolbar() {
        mToolbar = get(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("Must include Toolbar and define @+id/toolbar." +
                    " You can get @layout/base_toolbar");
        }
        return mToolbar;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        AppCompatActivity activity = getActivity();
        //设置显示home图标
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //初始化 MaterialMenuDrawable
        MaterialMenuDrawable materialMenu =
                new MaterialMenuDrawable(activity, Color.WHITE, Stroke.THIN);
        mDrawerLayout = get(R.id.drawer_layout);
        mFab = get(R.id.fab_main);
        // 根据侧滑菜单进度控制 MaterialMenuDrawable 旋转进度
        mainDrawerListener = new MainDrawerListener(materialMenu);
        mDrawerLayout.addDrawerListener(mainDrawerListener);
        // 将 MaterialMenuDrawable 设置为home图标
        mToolbar.setNavigationIcon(materialMenu);
        // 点击home图标,控制侧滑开关
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMenuState();
            }
        });
    }

    /**
     * 取消DrawerLayout事件的监听
     * Removes the specified listener from the list of listeners that will be notified of drawer events.
     */
    public void removeDrawerListener(){
        if(mainDrawerListener != null){
            mDrawerLayout.removeDrawerListener(mainDrawerListener);
        }
    }

    public boolean menuIsOpen() {
        return mDrawerLayout.isDrawerOpen(Gravity.LEFT);
    }

    /**
     * 修改侧滑菜单状态
     *
     * @return 修改后菜单的状态
     */
    public boolean changeMenuState() {
        if (mDrawerLayout == null) mDrawerLayout = get(R.id.drawer_layout);
        boolean isOpen = mDrawerLayout.isDrawerOpen(Gravity.LEFT);
        if (isOpen) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
        return !isOpen;
    }

    /**
     * 显示Toolbar的退出tip
     */
    public void showExitTip() {
        TextView view = get(R.id.titlebar_text_exittip);
        view.setVisibility(View.VISIBLE);
        Animation a = KJAnimations.getTranslateAnimation(0f, 0f, -mToolbar.getHeight(), 0f, 300);
        view.startAnimation(a);
    }
    /**
     * 取消退出
     */
    public void cancleExit() {
        final TextView view = get(R.id.titlebar_text_exittip);
        Animation a = KJAnimations.getTranslateAnimation(0f, 0f, 0f, -mToolbar.getHeight(), 300);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(a);
    }

    /**
     * DrawerLayout事件的监听实现类
     */
    private class MainDrawerListener extends DrawerLayout.SimpleDrawerListener{
        private MaterialMenuDrawable materialMenu;

        public MainDrawerListener(MaterialMenuDrawable materialMenu) {
            this.materialMenu = materialMenu;
        }
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            materialMenu.setTransformationOffset(
                    MaterialMenuDrawable.AnimationState.BURGER_ARROW, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            mFab.hide();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            mFab.show();
        }
    }
}
