package com.qjuzi.tools.core.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.qjuzi.tools.R;

/**
 * Created by wangtianya on 2018/3/6.
 */
public class YaaFragmentContainerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        String className = getIntent().getStringExtra("className");
        try {
            Object fragment = Class.forName(className).getConstructor().newInstance();
            if (fragment instanceof Fragment) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, (Fragment) fragment);
                transaction.commit();
            } else {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, (android.support.v4.app.Fragment) fragment);
                transaction.commit();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startFragment(Context context, Class pageClazz) {
        Intent intent = new Intent(context, YaaFragmentContainerActivity.class);
        intent.putExtra("className", pageClazz.getName());
        context.startActivity(intent);
    }
}