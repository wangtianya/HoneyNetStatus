/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.wangtianya.learn.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qjuzi.yaa.context.ContextCache;
import com.wangtianya.learn.wiget.FragmentContainerActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by tianya on 2017/4/27.
 */

public class ButtonItemFactory {

    public static Button newActivityPageItem(String tittle, final Class<? extends Activity> clazz) {
        Button button = new Button(ContextCache.getContext());
        button.setText(tittle);
        button.setLines(1);
        button.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        button.setOnClickListener(v -> ContextCache.getContext().startActivity(new Intent(ContextCache.getContext(), clazz)));
        return button;
    }


    public static Button newFragmentPageItem(String tittle, final Class<? extends Fragment> clazz) {
        Button button = new Button(ContextCache.getContext());
        button.setText(tittle);
        button.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        button.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        button.setLines(1);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(ContextCache.getContext(), FragmentContainerActivity.class);
            intent.putExtra("className", clazz.getName());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            ContextCache.getContext().startActivity(intent);
        });

        return button;
    }


    public static  Button newClickItem(String tittle, View.OnClickListener onClickListener) {
        Button button = new Button(ContextCache.getContext());
        button.setText(tittle);
        button.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        button.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        button.setLines(1);
        button.setOnClickListener(onClickListener);
        return button;
    }
}
