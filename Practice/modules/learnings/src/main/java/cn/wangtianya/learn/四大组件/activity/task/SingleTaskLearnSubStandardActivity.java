/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package cn.wangtianya.learn.四大组件.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.wangtianya.learn.common.ItemActivity;

public class SingleTaskLearnSubStandardActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addActivityItem("launch Standard", SingleTaskLearnSubStandardActivity.class);
        addClickItem("launch Standard newTask", v -> {
            Intent intent = new Intent(this, SingleTaskLearnSubStandardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        });

        addActivityItem("launch Standard1", SingleTaskLearnSubStandard1Activity.class);
        addClickItem("launch Standard1 newTask", v -> {
            Intent intent = new Intent(this, SingleTaskLearnSubStandard1Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        });

        addActivityItem("launch SingleTask", SingleTaskLearnActivity.class);

        addClickItem("taskId: " + getTaskId(), view -> {
            Toast.makeText(this, "task", Toast.LENGTH_LONG).show();
        });
    }
}
