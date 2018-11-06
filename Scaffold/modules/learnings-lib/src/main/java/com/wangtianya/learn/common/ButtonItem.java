package com.wangtianya.learn.common;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tianya on 2017/4/27.
 */

@SuppressLint("ViewConstructor")
public class ButtonItem extends Button {

    public ButtonItem(String tittle, final Class<? extends Activity> clazz) {
        super(AppModel.getContext());
        this.setText(tittle);
        this.setLines(1);
        this.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        this.setOnClickListener(v -> AppModel.getContext().startActivity(new Intent(AppModel.getContext(), clazz)));
    }
}
