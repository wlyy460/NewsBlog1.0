package com.inventec.newsblog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.R;
import com.inventec.newsblog.adapter.PictureGridAdapter;
import com.inventec.newsblog.delegate.PicturesFragmentDelegate;
import com.inventec.newsblog.inter.IPictureData;
import com.inventec.newsblog.inter.NetLoadImpl;
import com.inventec.newsblog.inter.OnNetRequestListener;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;
import com.inventec.newsblog.model.pictures.PictureBean;
import com.inventec.newsblog.utils.Loger;
import com.inventec.newsblog.widget.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片大全列表界面
 * Created by Test on 2017/3/2.
 */

public class PicturesFragment extends FragmentPresenter<PicturesFragmentDelegate> implements SwipeRefreshAndLoadMoreCallBack,
        View.OnClickListener, BaseRecyclerAdapter.OnItemClickListener{
    private static final String TAG = PicturesFragment.class.getSimpleName();
    private String pictureType = IPictureData.DEFAULT_TYPE;//图片类别
    private int pageNum = 1;
    private  boolean isSwitchType = false;//用于判断是否切换图片类型

    private List<PictureBean> pictureDatas = new ArrayList<>();
    private FloatingActionMenu floatingActionMenu;
    protected RecyclerView recyclerView;
    private IPictureData iPictureData;

    private PictureGridAdapter pictureGridAdapter;

    public static PicturesFragment newInstance() {
        PicturesFragment fragment = new PicturesFragment();
        return fragment;
    }

    @Override
    protected Class<PicturesFragmentDelegate> getDelegateClass() {
        return PicturesFragmentDelegate.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doLoadPicturesList(true);
        /*if (pictureDatas == null || pictureDatas.isEmpty()){
            floatingActionMenu.setVisibility(View.GONE);
        }*/
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        recyclerView = viewDelegate.get(R.id.base_recyclerview);
        floatingActionMenu = viewDelegate.get(R.id.floating_action_menu);
        iPictureData = new NetLoadImpl();
        pictureGridAdapter = new PictureGridAdapter(recyclerView, pictureDatas, R.layout.item_pictures);
        viewDelegate.setListAdapter(pictureGridAdapter);
        viewDelegate.setOnClickListener(this, R.id.floating_action_button1,
                R.id.floating_action_button2,
                R.id.floating_action_button3,
                R.id.floating_action_button4);
        //注册下拉刷新
        viewDelegate.registerSwipeRefreshCallBack(this);
        //注册加载更多
        viewDelegate.registerLoadMoreCallBack(this, pictureGridAdapter);
        pictureGridAdapter.setOnItemClickListener(this);
    }

    private void bindEven() {
        ProgressLayout progressLayout =  viewDelegate.get(R.id.progress_layout);
        progressLayout.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLoadPicturesList(true);
                viewDelegate.showLoading();
            }
        });
    }

    @Override
    public void refresh() {
        doLoadPicturesList(true);
    }

    @Override
    public void loadMore() {
        doLoadPicturesList(false);
    }

    /**
     * 从网络加载数据列表
     * @param isRefresh 是否刷新
     */
    private void doLoadPicturesList(final boolean isRefresh) {
        if(isRefresh){
            pageNum = 1;
        }else {
            pageNum++;
        }
        iPictureData.doRequestPictures(pictureType, pageNum, new OnNetRequestListener<List<PictureBean>>() {
            @Override
            public void onStart() {
                viewDelegate.showRefreshLayout();
            }
            @Override
            public void onFinish() {
                if (viewDelegate != null) {
                    viewDelegate.showContent();
                }
            }
            @Override
            public void onSuccess(List<PictureBean> list) {
                if (list != null && !list.isEmpty()) {
                    if (isRefresh) {
                        if (isSwitchType) {
                            pictureDatas.clear();
                            pictureDatas.addAll(list);
                            pictureGridAdapter.notifyDataSetChanged();
                            return;
                        }
                        if (pictureDatas != null && !pictureDatas.isEmpty()) {
                            //去重
                            for (PictureBean data : list) {
                                if (!pictureDatas.contains(data))
                                    pictureDatas.add(data);
                            }
                        }else{
                            pictureDatas.addAll(list);
                        }
                    } else {
                        pictureDatas.addAll(list);
                    }
                    pictureGridAdapter.notifyDataSetChanged();
                }else if (pictureDatas == null || pictureDatas.isEmpty()
                        || pictureGridAdapter == null || pictureGridAdapter.getItemCount() < 1) {
                    viewDelegate.showError(R.string.load_error);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Loger.debug(TAG ,"====网络请求异常," +  t.getMessage());
                if (viewDelegate != null && pictureGridAdapter != null) {
                    //有可能界面已经关闭网络请求仍然返回
                    if (pictureGridAdapter.getItemCount() > 1) {
                        viewDelegate.showContent();
                    } else {
                        viewDelegate.showError(R.string.load_error);
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }

    /**
     * 悬浮菜单项目的点击动作的实现
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floating_action_button1:
                pictureType = "4001";//清纯
                break;
            case R.id.floating_action_button2:
                pictureType = "4004";//校花
                break;
            case R.id.floating_action_button3:
                pictureType = "4007";//非主流
                break;
            case R.id.floating_action_button4:
                pictureType = "4013";//美女魅惑
                break;
            default:
                pictureType = "4004";
                break;
        }
        isSwitchType = true;
        viewDelegate.hideMenu(true);
        refresh();

    }
}
