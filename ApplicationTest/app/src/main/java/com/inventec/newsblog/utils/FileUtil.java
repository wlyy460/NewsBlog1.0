package com.inventec.newsblog.utils;

import android.os.Environment;

import com.inventec.newsblog.BaseApplication;

import java.io.File;

/**
 * Created by kymjs on 3/2/16.
 */
public final class FileUtil {

    /***
     * 获取文件夹大小
     ***/
    public static long getFileSize(File f) {
        long size = 0L;
        if (f != null && f.exists()) {
            File flist[] = f.listFiles();
            for (File aFlist : flist) {
                size += aFlist.isDirectory() ? getFileSize(aFlist) : aFlist.length();
            }
        }
        return size;
    }

    /**
     * 获取缓存文件对象（指定缓存目录放在程序包名下）
     *
     * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
     */
    public static File getCacheDir(String folderName) {
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        String cacheDir = "";
        if (BaseApplication.getContext().getExternalCacheDir() != null && isExistSDCard()) {
            cacheDir = BaseApplication.getContext().getExternalCacheDir().toString();
        } else {
            cacheDir = BaseApplication.getContext().getCacheDir().toString();
        }
        File file = new File(cacheDir + File.separator + folderName + File.separator);
        file.mkdirs();
        return file;
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 判断SD卡是否存在
     * @return
     */
    private static boolean isExistSDCard() {
        return android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
    }

}
