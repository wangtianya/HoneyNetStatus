/*
 * Copyright (C) 2019 Godya. All Rights Reserved.
 */
package com.qjuzi.architecure.tree;

import com.qjuzi.architecure.tree.event.OnViewCreated;
import com.qjuzi.architecure.tree.event.OnViewDestroyed;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TreeActivity extends Activity implements LifecycleOwner, LifecycleObserver {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    {
        autoCreateMembers();
    }

    private void autoCreateMembers() {
        TreeInjectUtil.autoCreateModel(this);
        TreeInjectUtil.autoCreatePresenter(this);
        getLifecycle().addObserver(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TreeInjectUtil.autoCreateBinding(this, this);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        TreeInjectUtil.notifyViewEvent(this, OnViewCreated.class); // Maybe here is not perfect.
    }


    @Override
    public void onStart() {
        super.onStart();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }



    @Override
    public void onStop() {
        super.onStop();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }


    @Override
    public void onDestroy() {
        TreeInjectUtil.notifyViewEvent(this, OnViewDestroyed.class);
        super.onDestroy();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
