package com.inventec.newsblog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.delegate.PicturesFragmentDelegate;
import com.inventec.newsblog.inter.IPictureData;
import com.inventec.newsblog.inter.SwipeRefreshAndLoadMoreCallBack;
import com.inventec.newsblog.model.pictures.PictureBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片大全列表界面
 * Created by Test on 2017/3/2.
 */

public class PicturesFragment extends FragmentPresenter<PicturesFragmentDelegate> implements SwipeRefreshAndLoadMoreCallBack, PicturesFragmentDelegate.FloatingActionButtonListener{
    private String pictureId = IPictureData.DEFAULT_TYPE;//图片类别id

    private List<PictureBean> pictureList = new ArrayList<>();

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
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setFloatingActionButtonListener(this);
    }

    @Override
    public void onClick(String id) {
        pictureId = id;
        refresh();
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }
}
