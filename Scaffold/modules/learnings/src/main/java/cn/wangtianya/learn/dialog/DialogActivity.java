package cn.wangtianya.learn.dialog;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class DialogActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addItem("Dialog Basic1", DialogOneActivity.class);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
