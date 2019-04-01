/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package com.qjuzi.lib.binding.extend.common;

import android.util.SparseArray;

/**
 * Created by wangtianya on 2018/9/14.
 */

public abstract class BindingItemModel {
    public int index;
    public int layoutId = getLayoutId();
    public SparseArray<Object> variableMap = new SparseArray<>();

    public abstract int getLayoutId();
    public void setVariable(int brId, Object object) {
        variableMap.append(brId, object);
    }
}
