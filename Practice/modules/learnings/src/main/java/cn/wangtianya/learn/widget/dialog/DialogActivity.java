package cn.wangtianya.learn.widget.dialog;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class DialogActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivityItem("Dialog Basic1", DialogOneActivity.class);
    }

}
