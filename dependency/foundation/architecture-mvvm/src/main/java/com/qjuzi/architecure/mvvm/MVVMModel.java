/*
 * Copyright (C) 2019 Godya. All Rights Reserved.
 */
package com.qjuzi.architecure.mvvm;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;

public abstract class MVVMModel<S> extends ViewModel implements LifecycleObserver {

    public S page;

    public void setComponent(S page) {
        this.page = page;
    }

}
