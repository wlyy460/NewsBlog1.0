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
import com.inventec.newsblog.utils.LogUtil;
import com.inventec.newsblog.utils.TimeUtil;

import java.util.Collection;

/**
 * 新闻列表适配器
 * Created by Test on 2017/2/28.
 */
public class NewsListAdapter extends BaseRecyclerAdapter<NewsBean> {
    public static final int TYPE_ITEM_DEFAULT = 0;//item内容(单图片)
    public static final int TYPE_ITEM_MUTIPLE = 1;//item内容（多图片显示）
    public static final int TYPE_FOOTER = 2;//加载更多
    private static final String TAG = NewsListAdapter.class.getSimpleName();

    /**
     * 是否显示加载更多视图
     */
    private boolean isShowFooter = true;
    private View footerView;
    private View defaultItemView;

    public NewsListAdapter(RecyclerView v, Collection<NewsBean> datas) {
        super(v, datas);
    }

    public NewsListAdapter(RecyclerView v, Collection<NewsBean> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(RecyclerHolder holder, NewsBean item, int position) {
        if (holder instanceof ItemDefaultHolder) {
            ItemDefaultHolder viewHolder = (ItemDefaultHolder) holder;
            ImageView descImageView = viewHolder.getView(R.id.iv_desc);
            if (item.getImageurls() != null && item.getImageurls().size() > 0) {
                GlideUtil.loadImage(BaseApplication.getContext(), item.getImageurls().get(0)
                        .getUrl().trim(), descImageView);
                if (descImageView.getVisibility() == View.GONE)
                    descImageView.setVisibility(View.VISIBLE);
            } else {
                descImageView.setVisibility(View.GONE);
            }
            viewHolder.setText(R.id.tv_title, item.getTitle());
            String pubDate = item.getPubDate();
            if (pubDate.split(" ")[0].equals(TimeUtil.getNowYMD())) {
                viewHolder.setText(R.id.tv_news_time, pubDate.split(" ")[1]);
            } else {
                viewHolder.setText(R.id.tv_news_time, pubDate);
            }
            viewHolder.setText(R.id.tv_default_news_source, item.getSource());
        } else if (holder instanceof ItemMutipleHolder) {
            ItemMutipleHolder viewHolder = (ItemMutipleHolder) holder;
            ImageView descImageView1 = viewHolder.getView(R.id.iv_desc1);
            ImageView descImageView2 = viewHolder.getView(R.id.iv_desc2);
            ImageView descImageView3 = viewHolder.getView(R.id.iv_desc3);
            if (item.getImageurls() != null && item.getImageurls().size() >= 3) {
                GlideUtil.loadImage(BaseApplication.getContext(), item.getImageurls()
                        .get(0).getUrl().trim(), descImageView1);
                //获取url列表中的倒数第二项url
                GlideUtil.loadImage(BaseApplication.getContext(), item.getImageurls()
                        .get(item.getImageurls().size()-2).getUrl().trim(), descImageView2);
                //获取url列表中的最后一项url
                GlideUtil.loadImage(BaseApplication.getContext(), item.getImageurls()
                        .get(item.getImageurls().size()-1).getUrl().trim(), descImageView3);
                if (descImageView3.getVisibility() == View.GONE)
                    descImageView3.setVisibility(View.VISIBLE);
            } else {
                descImageView3.setVisibility(View.GONE);
                GlideUtil.loadImage(BaseApplication.getContext(), item.getImageurls()
                        .get(0).getUrl().trim(), descImageView1);
                GlideUtil.loadImage(BaseApplication.getContext(), item.getImageurls()
                        .get(1).getUrl().trim(), descImageView2);
            }
            viewHolder.setText(R.id.tv_title, item.getTitle());
            String pubDate = item.getPubDate();
            if (pubDate.split(" ")[0].equals(TimeUtil.getNowYMD())) {
                viewHolder.setText(R.id.tv_news_time, pubDate.split(" ")[1]);
            } else {
                viewHolder.setText(R.id.tv_news_time, pubDate);
            }
            viewHolder.setText(R.id.tv_news_source, item.getSource());
        }

    }

    @Override
    public int getItemViewType(int position) {
        LogUtil.d(TAG, "getItemViewType");
        // 最后一个item设置为footerView
        if (!isShowFooter) {
            if (realDatas.get(position).getImageurls().size() > 1) {
                return TYPE_ITEM_MUTIPLE;
            }
            return TYPE_ITEM_DEFAULT;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            if (realDatas.get(position).getImageurls().size() > 1) {
                return TYPE_ITEM_MUTIPLE;
            }
            return TYPE_ITEM_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        LogUtil.d(TAG, "getItemCount");
        int begin = isShowFooter ? 1 : 0;
        if (realDatas == null || realDatas.isEmpty()) {
            return begin;
        }
        return super.getItemCount() + begin;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.d(TAG, "onCreateViewHolder");
        switch (viewType){
            case TYPE_ITEM_DEFAULT:
                defaultItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_default_news, parent, false);
                return new ItemDefaultHolder(defaultItemView);
            case TYPE_ITEM_MUTIPLE:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_mutiple_type_news_, parent, false);
                return new ItemMutipleHolder(itemView);

            case TYPE_FOOTER:
                footerView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_footer, parent, false);
                footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerHolder(footerView);

            default:
                return new ItemDefaultHolder(defaultItemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        LogUtil.d(TAG, "onBindViewHolder");
        if (position == getItemCount() - 1) {
            //没有数据的时候不显示footer
            if (position == 0) {
                this.isShowFooter = false;
                if (footerView != null) footerView.setVisibility(View.GONE);
            }
        } else {
            this.isShowFooter = true;
            if (footerView != null && footerView.getVisibility() == View.GONE)
                footerView.setVisibility(View.VISIBLE);
            super.onBindViewHolder(holder, position);
        }
    }

    public boolean isShowFooter() {
        return this.isShowFooter;
    }

    /**
     * 只显示单张图片item布局的ViewHolder类
     */
    private static class ItemDefaultHolder extends RecyclerHolder {
        public ItemDefaultHolder(View view) {
            super(view);
        }
    }

    /**
     * 显示多张图片item布局的ViewHolder类
     */
    private static class ItemMutipleHolder extends RecyclerHolder {
        public ItemMutipleHolder(View view) {
            super(view);
        }
    }
}
