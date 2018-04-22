package com.qjuzi.yaa.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wangtianya on 2018/4/23.
 */

public class IOHelper {
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

    public static String readString(InputStream is) {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
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
