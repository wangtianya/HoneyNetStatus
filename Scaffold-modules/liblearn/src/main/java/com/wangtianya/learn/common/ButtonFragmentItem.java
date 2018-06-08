package com.wangtianya.learn.common;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.wangtianya.learn.wiget.FragmentContainerActivity;

import android.app.Fragment;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tianya on 2017/4/27.
 */
public class ButtonFragmentItem extends Button {


    public ButtonFragmentItem(String tittle, final Class<? extends Fragment> clazz) {
        super(AppModel.getContext());

        this.setText(tittle);
        this.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        this.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        this.setLines(1);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppModel.getContext(), FragmentContainerActivity.class);
                YaUtil.toast(clazz.getName());
                intent.putExtra("className", clazz.getName());
                AppModel.getContext().startActivity(intent);
            }
        });


    }
}
