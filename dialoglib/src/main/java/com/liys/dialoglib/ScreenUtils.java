package com.liys.dialoglib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * 获取屏幕信息 工具类
 * @author liys
 * @version 1.0  2017/07/29
 */
class ScreenUtils {
    /** 1.获取屏幕宽度(单位px) */
    public static int getWidthPixels(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    /** 2.获取屏幕高度(单位px) */
    public static int getHeightPixels(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /** 3.获取屏幕密度 （0.75 / 1.0 / 1.5） 参考网址;http://blog.sina.com.cn/s/blog_74c22b210100s0kw.html */
    public static float getHeightPixels(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }
    /** 4. 获取状态栏高度 (单位px)*/
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen","android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 是否竖屏
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        return mConfiguration.orientation == mConfiguration.ORIENTATION_PORTRAIT;
    }

    /**
     * 是否横屏
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        return mConfiguration.orientation == mConfiguration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 强制设置为竖屏
     * @param activity
     */
    public static void setPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 强制设置为横屏
     * @param activity
     */
    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
