/*
 *.
 */
package cn.wangtianya.learn.temp;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class TempTestActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivityItem("强转空指针错误打印", NullPointerCastActivity.class);
    }

}
