/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.architecure.mvvm;

import android.arch.lifecycle.LifecycleObserver;

public abstract class MVVMModel<S> implements LifecycleObserver {

    public S page;

    public void setComponent(S page) {
        this.page = page;
    }

}
