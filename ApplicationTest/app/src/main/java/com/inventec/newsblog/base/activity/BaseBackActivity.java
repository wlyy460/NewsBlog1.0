package com.inventec.newsblog.base.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.view.View;
import android.view.WindowManager;

import com.inventec.frame.themvp.view.IDelegate;
import com.inventec.newsblog.base.BaseFrameActivity;
import com.inventec.newsblog.base.utils.BackActivityHelper;
import com.inventec.newsblog.base.utils.Utils;
import com.inventec.newsblog.base.widget.SwipeBackLayout;


/**
 * 侧滑finish的Activity基类
 *
 * @author kymjs (http://www.kymjs.com/) on 11/6/15.
 */
public abstract class BaseBackActivity<T extends IDelegate> extends BaseFrameActivity<T> {
    private BackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //解决长按出现复制粘贴栏在顶部占位问题，将操作栏设置为悬浮方式:
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    localLayoutParams.flags);
        }
        super.onCreate(savedInstanceState);
        mHelper = new BackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null) {
            v = mHelper.findViewById(id);
        }
        return v;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setSwipeBackEnable(enable);
    }

    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
