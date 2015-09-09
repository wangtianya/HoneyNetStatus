
package com.wangtianya.abase.core.util;

import com.wangtianya.abase.core.context.ABaseContext;

import android.widget.Toast;

/**
 * Created by tianya on 15/4/21.
 */
public class AToast {

    public static void show(String msg){
        Toast.makeText(ABaseContext.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg){
        Toast.makeText(ABaseContext.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
