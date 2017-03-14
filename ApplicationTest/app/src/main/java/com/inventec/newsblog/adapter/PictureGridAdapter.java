package com.inventec.newsblog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inventec.frame.adapter.BaseRecyclerAdapter;
import com.inventec.frame.adapter.RecyclerHolder;
import com.inventec.newsblog.BaseApplication;
import com.inventec.newsblog.R;
import com.inventec.newsblog.model.pictures.PictureBean;
import com.inventec.newsblog.utils.GlideUtil;

import java.util.Collection;

/**
 * 美图大全瀑布流适配器类
 * Created by Administrator on 2017/3/12.
 */

public class PictureGridAdapter extends BaseRecyclerAdapter<PictureBean> {
    private OnImageClickListener imageClickListener;

    public PictureGridAdapter(RecyclerView v, Collection<PictureBean> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(RecyclerHolder holder, PictureBean item, int position) {
        ImageView imageView = holder.getView(R.id.iv_picture);
        if (item.getList() != null && item.getList().size() > 0) {
            GlideUtil.loadImage(BaseApplication.getContext(), item.getList().get(0).getMiddleUrl().trim(), imageView);
        } else {
            GlideUtil.loadImage(BaseApplication.getContext(), "", imageView);
        }
        holder.setText(R.id.tv_title, item.getTitle());
    }


    public void setOnImageClickListener(OnImageClickListener listener) {
        this.imageClickListener = listener;
    }

    /**
     * item条目中图片的点击监听接口
     */
    public interface OnImageClickListener {
        void onImageClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
