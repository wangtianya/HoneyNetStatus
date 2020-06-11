package com.wangtianya.qnet.common.widget;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wangtianya.architecure.tree.TreeActivity;

import cn.wangtianya.scaffold.lib.R;

/**
 * Created by wangtianya on 2018/3/6.
 */
public class TreeFragmentContainerActivity extends TreeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        String className = getIntent().getStringExtra("className");
        try {
            Fragment fragment = (Fragment) Class.forName(className).getConstructor().newInstance();
            fragment.setArguments(getIntent().getBundleExtra("bundle"));
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flContainer, fragment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startFragment(Context context, String pageName) {
        Intent intent = new Intent(context, TreeFragmentContainerActivity.class);
        intent.putExtra("className", pageName);
        context.startActivity(intent);
    }

    public static void startFragment(Context context, String pageName, Bundle bundle) {
        Intent intent = new Intent(context, TreeFragmentContainerActivity.class);
        intent.putExtra("className", pageName);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }
}