package com.inventec.newsblog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.frame.adapter.RecyclerHolder;
import com.inventec.newsblog.BaseApplication;
import com.inventec.newsblog.R;
import com.inventec.newsblog.model.news.NewsBean;
import com.inventec.newsblog.utils.GlideUtil;
import com.inventec.newsblog.utils.Loger;

import java.util.Collection;

/**
 * 新闻列表适配器
 * Created by Test on 2017/2/28.
 */
public class NewsListAdapter extends BaseRecyclerAdapter<NewsBean>{
    public static final int TYPE_ITEM = 0;//item内容
    public static final int TYPE_FOOTER = 1;//加载更多
    private static final String TAG = NewsListAdapter.class.getSimpleName();

    /**
     * 是否显示加载更多视图
     */
    private boolean showFooter = true;

    public NewsListAdapter(RecyclerView v, Collection<NewsBean> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(RecyclerHolder holder, NewsBean item, int position) {
        ImageView descImageView = holder.getView(R.id.iv_desc);
        if (item.getImageurls() != null && item.getImageurls().size() > 0) {
            GlideUtil.loadImage(BaseApplication.getContext(), item.getImageurls().get(0).getUrl().trim(), descImageView);
        } else {
            GlideUtil.loadImage(BaseApplication.getContext(), "", descImageView);
        }
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_news_time, item.getPubDate());
        holder.setText(R.id.tv_news_source, item.getSource());
    }

    @Override
    public int getItemViewType(int position) {
        Loger.debug(TAG, "getItemViewType");
        // 最后一个item设置为footerView
        if (!showFooter) {
            return TYPE_ITEM;
        }
        if (position == getItemCount() - 1){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        Loger.debug(TAG, "getItemCount");
        int begin = showFooter ? 1 : 0;
        if (realDatas == null || realDatas.isEmpty()) {
            return begin;
        }
        return super.getItemCount() + begin;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Loger.debug(TAG, "onCreateViewHolder");
        if (viewType == TYPE_ITEM) {
            return super.onCreateViewHolder(parent, viewType);
        } else if (viewType == TYPE_FOOTER){
            View footerView = LayoutInflater.from(cxt).inflate(
                    R.layout.item_footer, parent, false);
            footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new RecyclerHolder(footerView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        Loger.debug(TAG, "onBindViewHolder");
        if (position == getItemCount() - 1) {
            //没有数据的时候不显示footer
            if (position == 0) {
                this.showFooter = false;
            }
        } else {
            this.showFooter = true;
            super.onBindViewHolder(holder, position);
        }
    }

    public boolean isShowFooter() {
        return this.showFooter;
    }

    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }
}
