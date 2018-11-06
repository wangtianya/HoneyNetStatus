package com.wangtianya.learn.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;

/**
 * Created by wangtianya on 2018/3/6.
 */

public class FragmentHelper {


    public static void addFragment(FragmentManager manager, @IdRes int id, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }
}
