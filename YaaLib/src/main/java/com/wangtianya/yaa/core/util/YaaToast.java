
package com.wangtianya.yaa.core.util;

import com.wangtianya.yaa.core.context.YaaContext;

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
