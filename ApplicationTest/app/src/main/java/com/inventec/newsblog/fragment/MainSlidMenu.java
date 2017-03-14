package com.inventec.newsblog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.newsblog.AppConfig;
import com.inventec.newsblog.R;
import com.inventec.newsblog.delegate.MainSlidMenuDelegate;
import com.kymjs.core.bitmap.client.BitmapCore;


/**
 * 侧滑界面逻辑代码
 *
 * @author
 */
public class MainSlidMenu extends FragmentPresenter<MainSlidMenuDelegate> {
    private onDrawerMenuItemClickListener listener;

    @Override
    protected Class<MainSlidMenuDelegate> getDelegateClass() {
        return MainSlidMenuDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        /*viewDelegate.setOnClickListener(getOnClickListener() ,
                R.id.menu_item_tag1,
                R.id.menu_item_tag2,
                R.id.menu_item_tag3,
                R.id.menu_item_tag4,
                R.id.menu_rootview);*/
        viewDelegate.get(R.id.menu_item_tag1)
                .setOnClickListener(getOnClickListener(R.id.menu_item_tag1));
        viewDelegate.get(R.id.menu_item_tag2)
                .setOnClickListener(getOnClickListener(R.id.menu_item_tag2));
        viewDelegate.get(R.id.menu_item_tag3)
                .setOnClickListener(getOnClickListener(R.id.menu_item_tag3));
        viewDelegate.get(R.id.menu_item_tag4)
                .setOnClickListener(getOnClickListener(R.id.menu_item_tag4));
        viewDelegate.get(R.id.menu_rootview)
                .setOnClickListener(getOnClickListener(R.id.menu_rootview));

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new BitmapCore.Builder()
                .view(viewDelegate.get(R.id.menu_header))
                .url(AppConfig.getAvatarURL())
                .errorResId(R.mipmap.def_avatar)
                .doTask();
    }

    /**
     * 设置抽屉菜单item选项的点击监听接口
     * @param listener
     */
    public void setOnDrawerMenuItemClickListener(onDrawerMenuItemClickListener listener) {
        this.listener = listener;
    }

    public interface onDrawerMenuItemClickListener{
        /**
         *抽屉式侧滑菜单item选项的点击回调
         * @param id 被点击的菜单选项id
         */
        void onDrawerMenuItemClick(int id);
    }

    public View.OnClickListener getOnClickListener(final int id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (listener != null && v != null) {
                    listener.onDrawerMenuItemClick(id);
                }
            }
        };
    }

    /*@Override
    public void onClick(View v) {
        //使用RxBus事件总线机制向主界面activity发送消息
        Event event = new Event(null, null, 0);
        event.setAction(MainActivity.MENU_CLICK_EVEN);
        event.setObject(v);
        RxBus.getDefault().post(event);
    }*/
}
