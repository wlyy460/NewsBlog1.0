package com.inventec.newsblog.delegate;

import android.support.v4.widget.SwipeRefreshLayout;

import com.inventec.newsblog.R;
import com.inventec.newsblog.base.delegate.BaseListDelegate;
import com.inventec.newsblog.widget.EmptyLayout;


/**
 * @author kymjs (http://www.kymjs.com/) on 11/27/15.
 */
public class PullListDelegate extends BaseListDelegate {

    public EmptyLayout mEmptyLayout;

    @Override
    public void initWidget() {
        super.initWidget();
        mEmptyLayout = new EmptyLayout(getActivity());
        setEmptyLayout(mEmptyLayout);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener l) {
        SwipeRefreshLayout swipeRefreshLayout = get(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(l);
    }
}
