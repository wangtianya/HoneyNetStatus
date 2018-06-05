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

        addItem("内存泄露", LeakActivity.class);

        addItem("内存溢出", OOMActivity.class);
    }
}
