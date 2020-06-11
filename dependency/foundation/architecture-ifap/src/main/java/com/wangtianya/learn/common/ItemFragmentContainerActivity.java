package com.wangtianya.learn.common;

import com.wangtianya.learn.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wangtianya on 2018/3/6.
 */

public class ItemFragmentContainerActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        String className = getIntent().getStringExtra("className");
        try {
            Fragment fragment = (Fragment) Class.forName(className).getConstructor().newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flContainer, fragment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
