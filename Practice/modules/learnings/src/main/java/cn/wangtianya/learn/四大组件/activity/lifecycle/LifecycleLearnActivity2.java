/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.四大组件.activity.lifecycle;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;
import android.util.Log;

public class LifecycleLearnActivity2 extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onResume");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onAttachedToWindow");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onDestroy");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("LifecycleLearn", "LifecycleLearnActivity2:  onDetachedFromWindow");
    }




}
