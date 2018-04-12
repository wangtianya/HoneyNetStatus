
package com.wangtianya.yaa.core.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class ScreenUtil {
    private static Display defaultDisplay;

    private static float mDensity = 0;
    private static float mScaledDensity = 0;

    public static float getDensity(Context context) {
        if (mDensity == 0) {
            mDensity = context.getResources().getDisplayMetrics().density;
        }
        return mDensity;
    }

    public static float getScaledDensity(Context context) {
        if (mScaledDensity == 0) {
            mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        }
        return mScaledDensity;
    }

    public static int dip2px(int dip, Context context) {
        return (int) (0.5F + getDensity(context) * dip);
    }

    public static int px2dip(int px, Context context) {
        return (int) (0.5F + px / getDensity(context));
    }


    // get screen width value int pixels
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    // get screen height value int pixels
    public static int getSreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static Display getDefaultDisplay(Context context) {
        if (defaultDisplay == null) {
            defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        }
        return defaultDisplay;
    }

    public static int getHeight(Context context) {
        return getDefaultDisplay(context).getHeight();
    }

    public static int getWidth(Context context) {
        return getDefaultDisplay(context).getWidth();
    }

    public static int percentHeight(float percent, Context context) {
        return (int) (percent * getHeight(context));
    }

    public static int percentWidth(float percent, Context context) {
        return (int) (percent * getWidth(context));
    }

    public static int getStatusBarHeight(Context context) {
        if (context instanceof Activity) {
            Rect rectangle = new Rect();
            Window window = ((Activity) context).getWindow();
            if (window != null && window.getDecorView() != null) {
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            }
            return rectangle.top;
        } else {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int STATUS_BAR_HEIGHT = (int) Math.ceil(25 * metrics.density);
            return STATUS_BAR_HEIGHT;
        }

    }
}
