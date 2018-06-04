package com.wangtianya.learn.common;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by wangtianya on 2018/3/6.
 */

public class YaUtil {

    public static void toast(String toastStr) {
        Toast.makeText(AppModel.getContext(), toastStr, Toast.LENGTH_SHORT).show();
    }

    public static void log(String tag, String log) {
        Log.e(tag, log);
    }

    public static Button newButton(String name, int dp, View.OnClickListener onClickListener) {
        Button button = new Button(AppModel.getContext());
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        button.setText(name);
        button.setOnClickListener(onClickListener);
        return button;
    }
}
