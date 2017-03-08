package com.inventec.newsblog;


import com.inventec.newsblog.utils.CommonUtil;
import com.inventec.newsblog.utils.FileUtil;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;
import java.util.LinkedList;


/**
 * 全局常量配置器
 * Created by kymjs on 3/2/16.
 */
public class AppConfig {
    //捐赠页域名包含的字符串
    public static final String DONATE_STR = "kymjs.com/donate";

    public static final String DEF_AVATAR = "http://7xjria.com5.z0.glb.clouddn.com/default162813_JnM6_1767531.png";//已失效

    //支付宝相关
    public static final String ALIPAY_PKGNAME = "com.eg.android.AlipayGphone";
    public static final String ALIPAY_MAIN_NAME = "com.alipay.mobile.quinox.LauncherActivity";
    public static final String ALIPAY_ID = "#吱口令# EaIq4w54ym";

    //腾讯相关
    public static final String QQ_AVATAR = "http://qlogo4.store.qq.com/qzone/%ld/%ld/100";

    public static String getAvatarURL() {
        long qq = getQQNumber();
        if (qq > 0) {
            return QQ_AVATAR.replace("%ld", qq + "");
        } else {
            return QQ_AVATAR;
        }
    }

    /**
     * 获取用户的QQ号(获取不到时例如用户没有登陆过QQ等，使用默认的头像)
     * 根据用户手机SD卡中QQ缓存文件夹的文件夹名获取
     */
    public static long getQQNumber() {
        File rootFile = getTencentQQFolder();
        if (rootFile.exists() && rootFile.isDirectory()) {
            final LinkedList<Long> list = new LinkedList<>();
            for (String fileName : rootFile.list()) {
                long qq = CommonUtil.toLong(fileName, -1);
                if (qq > 0) {
                    list.add(qq);
                }
            }
            return getMaxFolderName(list);
        } else {
            return -1L;
        }
    }

    /**
     * 找到QQ所在文件夹()
     */
    public static File getTencentQQFolder() {
        return new File(FileUtils.getSDCardPath() + "/tencent/MobileQQ/");
    }

    /**
     * 获取最大文件夹的QQ号码
     * 缓存目录可能有多个QQ号,根据每个缓存文件夹大小,找出最常用的文件夹
     *
     * @param folderList 所有缓存文件夹的名字(QQ号)集合
     */
    public static long getMaxFolderName(LinkedList<Long> folderList) {
        File rootFolder = getTencentQQFolder();
        long size = 0;
        long maxFolderName = 0;
        for (Long folderName : folderList) {
            File folder = new File(rootFolder.getAbsolutePath() + File.separator + folderName);
            long tempSize = FileUtil.getFileSize(folder);
            if (tempSize > size) {
                size = tempSize;
                maxFolderName = folderName;
            }
        }
        return maxFolderName;
    }
}
