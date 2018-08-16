
package com.qjuzi.yaa.core.util;

import com.qjuzi.yaa.context.YaaContext;

import android.widget.Toast;

/**
 * Created by tianya on 15/4/21.
 */
public class YaaToast {

    public static void show(String msg){
        Toast.makeText(YaaContext.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg){
        Toast.makeText(YaaContext.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
