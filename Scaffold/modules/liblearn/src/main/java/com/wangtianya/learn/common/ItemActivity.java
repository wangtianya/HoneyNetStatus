package com.wangtianya.learn.common;

import com.wangtianya.learn.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by tianya on 2017/4/27.
 */

public class ItemActivity extends Activity {

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_items);
        mainLayout = findViewById(R.id.llItems);
    }

    protected void addItem(String tittle, Class<? extends Activity> clazz) {
        mainLayout.addView(new ButtonItem(tittle, clazz));
    }

    protected void addFragmentItem(String tittle, Class<? extends Fragment> clazz) {
        mainLayout.addView(new ButtonFragmentItem(tittle, clazz));
    }
}
