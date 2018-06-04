package com.wangtianya.learn.common;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tianya on 2017/4/27.
 */

public class ButtonItem extends Button {
    public ButtonItem(String tittle, final Class<? extends Activity> clazz) {
        super(AppModel.getContext());

        this.setText(tittle);
        this.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppModel.getContext().startActivity(new Intent(AppModel.getContext(), clazz));
            }
        });


    }
}
