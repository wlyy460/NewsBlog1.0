package com.inventec.frame.base;

import com.inventec.frame.themvp.presenter.FragmentPresenter;
import com.inventec.frame.themvp.view.IDelegate;

/**
 * 主界面内容Fragment
 *
 * @author kymjs (http://www.kymjs.com/) on 11/27/15.
 */
public abstract class MainFragment<T extends IDelegate> extends FragmentPresenter<T> {

    public void onChange() {
    }
}
