/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;
import cn.wangtianya.learn.R;

/**
 * Created by tianya on 2017/5/8.
 */

public class DialogOne extends Dialog {

    public DialogOne(@NonNull Context context) {
        super(context, R.style.BMDialog);
    }


    public static void setConfig(int gravity, int pixels) {

    }

    public static void clearConfig() {

    }

    public void init(int gravity, int pixels) {
        setContentView(R.layout.dialog_one);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM | gravity;
        layoutParams.width = pixels;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
