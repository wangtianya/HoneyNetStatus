
package com.wangtianya.yaa.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.wangtianya.yaa.core.context.YaaContext;
import com.wangtianya.yaa.core.util.YaaLog;

import android.os.Environment;

/**
 * <!-- 在SDCard中创建于删除文件权限 --> <uses-permission
 * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" /> <!--
 * 往SDCard写入数据权限 --> <uses-permission
 * android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 * @author wangtianya
 */
public class SDCardHelper {

    private static YaaLog log = YaaLog.getLogger();

    public static File getSdcardDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(Environment.getExternalStorageDirectory() + File.separator + getAppName());
        }
        return null;
    }

    public static File getSdcardDir(String name) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(Environment.getExternalStorageDirectory() + File.separator + getAppName() + File.separator + name);
        }
        return null;
    }

    public static File getAppDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return YaaContext.getContext().getFilesDir();
        }
        return null;
    }

    public static File getAppDir(String name) {
        File file = new File(getAppDir() + File.separator + name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getCacheDir() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return getSdcardDir("caches");
        }
        return YaaContext.getContext().getCacheDir();
    }

    private static String getAppName() {
        String packname = YaaContext.getContext().getPackageName();
        return packname.substring(packname.lastIndexOf(".") + 1, packname.length());
    }
}
