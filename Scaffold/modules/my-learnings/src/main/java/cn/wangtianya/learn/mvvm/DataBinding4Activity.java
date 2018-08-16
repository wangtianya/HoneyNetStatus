/*
 *.
 */
package cn.wangtianya.learn.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import cn.wangtianya.learn.R;

public class DataBinding4Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_layout);
        LinearLayout l = (LinearLayout) findViewById(R.id.llLayout);
    }



}
