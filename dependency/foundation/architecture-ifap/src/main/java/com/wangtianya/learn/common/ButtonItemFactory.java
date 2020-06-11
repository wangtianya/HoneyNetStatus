/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package com.wangtianya.learn.common;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.wangtianya.architecure.tree.Tree;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tianya on 2017/4/27.
 */

public class ButtonItemFactory {

    public static Button newActivityPageItem(String tittle, final Class<? extends Activity> clazz) {
        Button button = new Button(Tree.getContext());
        button.setAllCaps(false);
        button.setText(tittle);
        button.setLines(1);
        button.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        button.setOnClickListener(v -> Tree.getContext().startActivity(new Intent(Tree.getContext(), clazz)));
        return button;
    }


    public static Button newFragmentPageItem(String tittle, final Class<? extends Fragment> clazz) {
        Button button = new Button(Tree.getContext());
        button.setAllCaps(false);
        button.setText(tittle);
        button.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        button.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        button.setLines(1);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Tree.getContext(), ItemFragmentContainerActivity.class);
            intent.putExtra("className", clazz.getName());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            Tree.getContext().startActivity(intent);
        });

        return button;
    }


    public static  Button newClickItem(String tittle, View.OnClickListener onClickListener) {
        Button button = new Button(Tree.getContext());
        button.setAllCaps(false);
        button.setText(tittle);
        button.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        button.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        button.setLines(1);
        button.setOnClickListener(onClickListener);
        return button;
    }
}
