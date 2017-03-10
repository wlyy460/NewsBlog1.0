package com.inventec.newsblog.delegate;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.frame.themvp.view.AppDelegate;
import com.inventec.newsblog.R;
import com.inventec.newsblog.inter.ILoadingView;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;
import com.inventec.newsblog.widget.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import static com.inventec.newsblog.R.id.floating_action_menu;
import static com.inventec.newsblog.R.id.progress_layout;

/**
 *  recycleview的通用视图代理类
 * Created by Test on 2017/2/23.
 */

public abstract class BaseRecyclerViewDelegate extends AppDelegate implements ILoadingView{
    ProgressLayout progressLayout;//进度条布局（通用，可实现错误按钮，点击重试）
    SwipeRefreshLayout swipeRefreshLayout;//下拉刷新控件
    RecyclerView recyclerView;
    //悬浮菜单
    FloatingActionMenu fabMenu;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    FloatingActionButton fab4;

    protected List<FloatingActionButton> floatingActionButtons;//悬浮菜单选项数组

    /**
     * 初始化recyclerView相关设置，必须重写
     */
    abstract void initRecyclerView();

    /**
     * 设置悬浮菜单是否显示
     */
    abstract boolean setFloatingActionMenuVisible();

    @Override
    public int getRootLayoutId() {
        return R.layout.base_layout_recyclerview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initView();
        //设置下拉刷新控件变换的四个颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.base_swiperefresh_color1,
                R.color.base_swiperefresh_color2,
                R.color.base_swiperefresh_color3,
                R.color.base_swiperefresh_color4);
        initRecyclerView();
        initFloatingActionMenu();
    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        progressLayout = get(progress_layout);
        swipeRefreshLayout = get(R.id.base_swipe_refresh_layout);
        recyclerView = get(R.id.base_recyclerview);
        fabMenu = get(floating_action_menu);
        fab1 = get(R.id.floating_action_button1);
        fab2 = get(R.id.floating_action_button2);
        fab3 = get(R.id.floating_action_button3);
        fab4 = get(R.id.floating_action_button4);
    }

    /**
     * 初始化悬浮菜单
     */
    private void initFloatingActionMenu() {
        fabMenu.setVisibility(setFloatingActionMenuVisible()? View.VISIBLE:View.GONE);
        fabMenu.setClosedOnTouchOutside(true);
        floatingActionButtons = new ArrayList<FloatingActionButton>();
        floatingActionButtons.add(fab1);
        floatingActionButtons.add(fab2);
        floatingActionButtons.add(fab3);
        floatingActionButtons.add(fab4);
    }

    /**
     * 设置是否隐藏悬浮菜单选项卡
     * @param animate 是否动画
     */
    public void hideMenu(boolean animate){
        fabMenu.close(animate);
    }

    /**
     * 设置数据适配器
     * @param adapter
     */
    public void setListAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    /**
     * 设置下拉刷新接口
     * @param callBack 下拉刷新的回调接口
     */
    public void registerSwipeRefreshCallBack(final SwipeRefreshAndLoadMoreCallBack callBack){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callBack.refresh();
            }
        });
        /*RxSwipeRefreshLayout.refreshes(swipeRefreshLayout).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                // 调用fragment的方法加载数据，需要解耦(已用接口解决)
                callBack.refresh();
            }
        });*/
    }

    /**
     * 设置加载更多接口
     *
     * @param callBack 加载更多的回调
     */
    public abstract void registerLoadMoreCallBack(SwipeRefreshAndLoadMoreCallBack callBack, BaseRecyclerAdapter adapter);

    @Override
    public void showLoading() {
        //swipeRefreshLayout.setRefreshing(true);
        if (!progressLayout.isLoading()){
            progressLayout.showLoading();
        }
    }

    @Override
    public void showContent() {
        swipeRefreshLayout.setRefreshing(false);
        //RxSwipeRefreshLayout.refreshing(swipeRefreshLayout).call(false);
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(int messageId, View.OnClickListener listener) {
        swipeRefreshLayout.setRefreshing(false);
        //RxSwipeRefreshLayout.refreshing(swipeRefreshLayout).call(false);
        if (!progressLayout.isError()) {
            progressLayout.showError(messageId, listener);
        }
    }

    @Override
    public Context getContext() {
        return null;
    }
}
