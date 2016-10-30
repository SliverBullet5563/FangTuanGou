package com.vmt.tuangou.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by SilverBullet on 2016/10/30.
 *      数据清除管理器
 */
public class DataCleanManager {
    /***
     *  清除应用内部缓存
     * @param context
     */
    public static void cleanInternalCache(Context context){
        deletedFileByDirectory(context.getCacheDir());
    }

    /**
     *     清除数据库
     * @param context
     */
    public static void cleanDataBase(Context context){
        deletedFileByDirectory(new File("/data/data/"+
                context.getPackageName()+"/databases"));
    }

    /**
     *  清除sp缓存
     * @param context
     */
    public static void cleanSharedPreference(Context context){
        deletedFileByDirectory(new File("/data/data/"+
                context.getPackageName()+"/shared_prefs"));
    }

    /**
     *  按名字清除本应用的数据库
     * @param context
     * @param name
     */
    public static void cleanDataBaseByName(Context context, String name){
        context.deleteDatabase(name);
    }

    /**
     *  清除包下的缓存
     * @param context
     */
    public static void cleanFiles(Context context){
        deletedFileByDirectory(context.getCacheDir());
    }
    /**
     *  清除外部的cache缓存
     * @param context
     */
    public static void cleanExternalCache(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deletedFileByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     *  清除自定义缓存
     * @param filePath
     */
    public static void cleanCustomCache(String filePath){
        deletedFileByDirectory(new File(filePath));
    }

    /**
     *  清除本应用中缓存
     * @param context
     */
    public static void cleanApplicationCache(Context context, String... filePath){
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanFiles(context);
        cleanSharedPreference(context);
        if(filePath == null) {
            return;
        }
        for (String path : filePath) {
            cleanCustomCache(path);
        }

    }
    /**
     *  删除方法
     * @param directory
     */
    private static void deletedFileByDirectory(File directory) {
        if(directory != null && directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                file.delete();
            }
        }
    }

    public static long getFoldSize(File file){
        long size = 0;
        try{
            File[] files = file.listFiles();
            for(int i = 0; i < files.length; i++) {
                //下面是否还有文件
                if(files[i].isDirectory()) {
                    size = size + getFoldSize(files[i]);
                }else {
                    size = size + files.length;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return size;
    }
    public static void deleteFoldFile(String filePath, boolean deleteThisPath){
        if(!TextUtils.isEmpty(filePath)) {
            try{
                File file = new File(filePath);
                if(file.isDirectory()) {
                    File[] files = file.listFiles();
                    for(int i = 0; i < files.length; i++) {
                        deleteFoldFile(files[i].getAbsolutePath(),true);
                    }
                }
                if(deleteThisPath) {
                    if(!file.isDirectory()) {
                        file.delete();
                    }else {
                        if(file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{

            }
        }
    }
    /**
     * 格式化单位
     *
     * @param size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    public static String getCacheSize(File file){
        return getFormatSize(getFoldSize(file));
    }
}













