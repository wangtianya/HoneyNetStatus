/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.memory;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class LeakActivity extends Activity {

    private ArrayList<Object> anyContentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingletonObj.getInstance().list.add(this);


        anyContentList.add(new byte[1100000]);

    }
}
