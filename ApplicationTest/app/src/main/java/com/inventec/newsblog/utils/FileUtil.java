package com.inventec.newsblog.utils;

import android.os.Environment;

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
     * 获取文件夹对象
     *
     * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
     */
    public static File getCacheDir(String folderName) {
        File file = new File( folderName);
        file.mkdirs();
        return file;
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
