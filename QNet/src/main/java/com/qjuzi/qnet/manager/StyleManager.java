/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.qnet.manager;

import com.qjuzi.architecure.base.context.ContextCache;
import com.qjuzi.qnet.R;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StyleManager {

    private static class Holder {
        private static StyleManager Instance;
    }

    public static StyleManager getInstance() {
        return Holder.Instance;
    }

    public void initStyle(Activity activity) {
        initStyle(activity, colorPrimaryDark(), colorPrimary());

    }
    private void initStyle(Activity activity, int colorStatusBar, int colorNav) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorStatusBar);
            window.setNavigationBarColor(colorNav);
        }
    }

    public int colorPrimary() {
        return ContextCache.getContext().getResources().getColor(R.color.colorPrimary);
    }

    public int colorPrimaryDark() {
        return ContextCache.getContext().getResources().getColor(R.color.colorPrimaryDark);
    }

    public int colorAccent() {
        return ContextCache.getContext().getResources().getColor(R.color.colorAccent);
    }

}
