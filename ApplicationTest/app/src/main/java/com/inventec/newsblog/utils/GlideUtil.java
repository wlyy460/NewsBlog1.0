package com.inventec.newsblog.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.inventec.newsblog.R;

/**
 * glide图片加载工具类
 * Created by Test on 2017/3/1.
 */

public class GlideUtil {
    public static void loadImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_holding)
                .error(R.mipmap.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }
}
