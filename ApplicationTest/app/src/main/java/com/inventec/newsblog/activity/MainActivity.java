package com.inventec.newsblog.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.inventec.frame.base.BaseFrameActivity;
import com.inventec.frame.base.MainFragment;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.MainDelegate;
import com.inventec.newsblog.fragment.BlogListFragment;
import com.inventec.newsblog.fragment.MainTabFragment;
import com.inventec.newsblog.fragment.XituListFragment;
import com.inventec.newsblog.model.Event;
import com.inventec.newsblog.utils.Api;
import com.inventec.newsblog.utils.ToastUtil;
import com.kymjs.rxvolley.rx.RxBus;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseFrameActivity<MainDelegate>
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {
    public static final String MENU_CLICK_EVEN = "slid_menu_click_event";

    private MainFragment currentFragment; //当前内容所显示的Fragment
    private Subscription rxBusSubscript;
    private MainFragment mainTabFragment = new MainTabFragment();
    private MainFragment blogListFragment = new BlogListFragment();
    private MainFragment xituListFragment = new XituListFragment();
    private boolean isOnKeyBacking;
    private Handler mMainLoopHandler = new Handler(Looper.getMainLooper());
    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            viewDelegate.cancleExit();
        }
    };

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/
        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置导航栏NavigationView的点击事件
        navigationView.setNavigationItemSelectedListener(this);*/
        setTitle(getResources().getString(R.string.menu_item_news));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, mainTabFragment, mainTabFragment.getClass().getName())
                .commit();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        rxBusSubscript = RxBus.getDefault().take(Event.class)
                .subscribe(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        changeContent(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
       viewDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFabDialog();
            }
        }, R.id.fab);
    }
    /**
     * 弹出点赞对话框
     */
    private void showFabDialog() {
        new AlertDialog.Builder(MainActivity.this).setTitle("点赞")
                .setMessage("去项目地址给作者个Star，鼓励下作者୧(๑•̀⌄•́๑)૭✧")
                .setPositiveButton("好嘞", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse(getString(R.string.app_url));   //指定网址
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);           //指定Action
                        intent.setData(uri);                            //设置Uri
                        MainActivity.this.startActivity(intent);        //启动Activity
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rxBusSubscript != null && rxBusSubscript.isUnsubscribed()) {
            rxBusSubscript.unsubscribe();
        }
    }

    /**
     * 根据MainSlidMenu点击选择不同的响应
     * @param event
     */
    private void changeContent(Event event) {
        if (!MENU_CLICK_EVEN.equals(event.getAction()))
            return;
        View view = event.getObject();
        switch (view.getId()) {
            case R.id.menu_item_tag1:
                changeFragment(mainTabFragment, getResources().getString(R.string.menu_item_news));
                break;
            case R.id.menu_item_tag2:
                changeFragment(blogListFragment, getResources().getString(R.string.menu_item_blog));
                break;
            case R.id.menu_item_tag3:
                changeFragment(xituListFragment, getResources().getString(R.string.menu_item_xitu));
                break;
            case R.id.menu_item_tag4:
                BlogDetailActivity.goinActivity(this, Api.OSL, null);
                break;
            default:
                break;
        }
        viewDelegate.changeMenuState();
    }

    /**
     * 用Fragment替换内容区
     * @param targetFragment 用来替换的Fragment
     * @param title          要替换掉的activity标题
     */
    public void changeFragment(MainFragment targetFragment, String title) {
        setTitle(title);
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(R.id.main_content, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            targetFragment.onChange();
        }
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewDelegate.menuIsOpen()) {
                viewDelegate.changeMenuState();
            } else if (isOnKeyBacking) {
                mMainLoopHandler.removeCallbacks(onBackTimeRunnable);
                isOnKeyBacking = false;
                finish();
            } else {
                isOnKeyBacking = true;
                viewDelegate.showExitTip();
                mMainLoopHandler.postDelayed(onBackTimeRunnable, 2000);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            ToastUtil toastUtil = new ToastUtil();
            toastUtil.Short(this, "暂未实现").show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}