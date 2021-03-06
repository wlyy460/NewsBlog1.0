package com.inventec.newsblog.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.inventec.newsblog.BaseApplication;


/**
 * 屏幕界面参数转化和获取相关工具类
 * Created by Test on 16/10/24.
 */

public class UIUtil {


    public static Resources getResources() {
        return BaseApplication.getContext().getResources();
    }

    public static int getColor(Context context, int colorId) {
        return ContextCompat.getColor(context, colorId);
    }

    public static Drawable getDrawable(Context context, int colorId) {
        return ContextCompat.getDrawable(context, colorId);
    }

    public static int dipToPx(Context context, int dpId) {

        return (int) context.getResources().getDimension(dpId);

    }

    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Bitmap takeScreenShot(Activity activity) {

        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        Bitmap cache = decorView.getDrawingCache();
        if (cache == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cache, 0, statusBarHeight, decorView.getMeasuredWidth(), decorView.getMeasuredHeight() - statusBarHeight);
        decorView.destroyDrawingCache();
        return bitmap;
    }


    /**
     * Gets the width of the display, in pixels.
     * <p/>
     * Note that this value should not be used for computing layouts, since a
     * device will typically have screen decoration (such as a status bar) along
     * the edges of the display that reduce the amount of application space
     * available from the size returned here. Layouts should instead use the
     * window size.
     * <p/>
     * The size is adjusted based on the current rotation of the display.
     * <p/>
     * The size returned by this method does not necessarily represent the
     * actual raw size (native resolution) of the display. The returned size may
     * be adjusted to exclude certain system decoration elements that are always
     * visible. It may also be scaled to provide compatibility with older
     * applications that were originally designed for smaller displays.
     *
     * @return Screen width in pixels.
     */
    public static int getScreenWidth(Context context) {
        return getScreenSize(context, null).x;
    }

    /**
     * Gets the height of the display, in pixels.
     * <p/>
     * Note that this value should not be used for computing layouts, since a
     * device will typically have screen decoration (such as a status bar) along
     * the edges of the display that reduce the amount of application space
     * available from the size returned here. Layouts should instead use the
     * window size.
     * <p/>
     * The size is adjusted based on the current rotation of the display.
     * <p/>
     * The size returned by this method does not necessarily represent the
     * actual raw size (native resolution) of the display. The returned size may
     * be adjusted to exclude certain system decoration elements that are always
     * visible. It may also be scaled to provide compatibility with older
     * applications that were originally designed for smaller displays.
     *
     * @return Screen height in pixels.
     */
    public static int getScreenHeight(Context context) {
        return getScreenSize(context, null).y;
    }

    /**
     * Gets the size of the display, in pixels.
     * <p/>
     * Note that this value should not be used for computing layouts, since a
     * device will typically have screen decoration (such as a status bar) along
     * the edges of the display that reduce the amount of application space
     * available from the size returned here. Layouts should instead use the
     * window size.
     * <p/>
     * The size is adjusted based on the current rotation of the display.
     * <p/>
     * The size returned by this method does not necessarily represent the
     * actual raw size (native resolution) of the display. The returned size may
     * be adjusted to exclude certain system decoration elements that are always
     * visible. It may also be scaled to provide compatibility with older
     * applications that were originally designed for smaller displays.
     *
     * @param outSize null-ok. If it is null, will create a Point instance inside,
     *                otherwise use it to fill the output. NOTE if it is not null,
     *                it will be the returned value.
     * @return Screen size in pixels, the x is the width, the y is the height.
     */
    public static Point getScreenSize(Context context, Point outSize) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point ret = outSize == null ? new Point() : outSize;
        final Display defaultDisplay = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(ret);
        } else {
            defaultDisplay.getSize(ret);
        }
        return ret;
    }

    public static int getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId  = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return Resources.getSystem().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     *
     * 获取标题栏高度
     * @param activity
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //应用区域
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        int viewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        /**标题栏高度 = View绘制区顶端位置 - 应用区顶端位置
                * */
        int titleHeight = viewTop - outRect.top;
        Log.e("WangJ", "标题栏高度：" + titleHeight);
        return titleHeight;
    }

}
