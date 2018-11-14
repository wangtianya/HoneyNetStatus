/*
 *.
 */
package cn.wangtianya.learn.mvvm;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class MVVMActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivityItem("DataBinding Basic1", DataBindingActivity.class);
        addActivityItem("DataBinding Basic2", DataBinding2Activity.class);
        addActivityItem("DataBinding Basic3", DataBinding3Activity.class);
        addActivityItem("标准Model Test", DataBindingMubanActivity.class);
    }
}
