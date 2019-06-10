package com.wangtianya.practice;

import com.qjuzi.architecure.base.context.ContextCache;
import com.wangtianya.learn.common.ItemActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startPractice();
    }

    private void startBaiduWork() {
        Intent intent = new Intent(ContextCache.getContext(), BaiduWorkActivity.class);
        ContextCache.getContext().startActivity(intent);
    }

    private void startPractice() {
        Intent intent = new Intent(ContextCache.getContext(), PracticeActivity.class);
        ContextCache.getContext().startActivity(intent);
    }
}
