
package com.qjuzi.tools.core.util;

import com.qjuzi.architecure.tree.context.Tree;

import android.widget.Toast;

/**
 * Created by tianya on 15/4/21.
 */
public class YaaToast {

    public static void show(String msg) {
        Toast.makeText(Tree.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(Tree.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
