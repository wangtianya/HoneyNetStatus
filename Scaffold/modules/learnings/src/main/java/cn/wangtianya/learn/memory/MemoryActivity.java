/*
 *.
 */
package cn.wangtianya.learn.memory;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class MemoryActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivityItem("内存泄露", LeakActivity.class);

        addActivityItem("内存溢出", OOMActivity.class);
    }
}
