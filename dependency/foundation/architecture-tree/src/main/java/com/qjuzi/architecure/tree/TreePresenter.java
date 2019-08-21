/*
 * Copyright (C) 2019 Godya. All Rights Reserved.
 */
package com.qjuzi.architecure.tree;

import android.arch.lifecycle.LifecycleObserver;

public abstract class TreePresenter<S> implements LifecycleObserver {

    public S page;

    public void setComponent(S page) {
        this.page = page;
    }

}
