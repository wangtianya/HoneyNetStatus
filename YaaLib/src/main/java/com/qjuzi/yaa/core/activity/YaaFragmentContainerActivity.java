package com.qjuzi.yaa.core.activity;

import com.qjuzi.yaa.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by wangtianya on 2018/3/6.
 */
public class YaaFragmentContainerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public static void startFragment(Context context, String pageName) {
        Intent intent = new Intent(context, YaaFragmentContainerActivity.class);
        intent.putExtra("className", pageName);
        context.startActivity(intent);
    }
}