/*
 *.
 */
package cn.wangtianya.learn.sysbase.memory;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;

public class MemoryFragment extends ItemFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragmentItem("内存泄露", LeakFragment.class);

        addFragmentItem("内存溢出", OOMFragment.class);
    }
}
