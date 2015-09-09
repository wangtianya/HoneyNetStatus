
package com.wangtianya.abase.persistence.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.wangtianya.abase.core.context.ABaseContext;
import com.wangtianya.abase.core.context.ABaseLog;

import android.os.Environment;

/**
 * <!-- 在SDCard中创建于删除文件权限 --> <uses-permission
 * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" /> <!--
 * 往SDCard写入数据权限 --> <uses-permission
 * android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 * @author wangtianya
 */
public class FileUtil {

    private static ABaseLog log = ABaseLog.getLogger();

    public static File getSdcardDir() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(Environment.getExternalStorageDirectory() + File.separator + getAppName());
        }
        return null;
    }

    public static File getSdcardDir(String name) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(Environment.getExternalStorageDirectory() + File.separator + getAppName() + File.separator + name);
        }
        return null;
    }

    public static File getAppDir() {
        if ((!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
            return ABaseContext.getContext().getFilesDir();
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
        return ABaseContext.getContext().getCacheDir();
    }

    private static String getAppName() {
        String packname = ABaseContext.getContext().getPackageName();
        return packname.substring(packname.lastIndexOf(".") + 1, packname.length());
    }















    /**
     * 删除文件夹
     *
     * @param dirf
     */
    public static void deleteDir(File dirf) {
        if (dirf.isDirectory()) {
            File[] childs = dirf.listFiles();
            for (int i = 0; i < childs.length; i++) {
                deleteDir(childs[i]);
            }
        }
        dirf.delete();
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void write(InputStream in, File file) {
        file.mkdirs();
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int readSize;
            while ((readSize = in.read(buffer)) > 0) {
                out.write(buffer, 0, readSize);
            }
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.e("write");
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void write(String in, File file, boolean append) {
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file, append);
            fw.write(in);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     *
     * @param file
     *
     * @return
     */
    public static String readString(File file) {
        if (!file.exists()) {
            return "";
        }
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String s;
            while ((s = br.readLine()) != null) {
                buffer.append(s);
            }
            br.close();
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
