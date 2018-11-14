/*
 *.
 */
package cn.wangtianya.learn.ripple;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

/**
 * Created by tianya on 2017/7/28.
 */

public class RippleActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivityItem("ListView 子控件里2", RippleInListItemActivity.class);
    }
}
