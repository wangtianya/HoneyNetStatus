/*
 *.
 */
package cn.wangtianya.learn.uithread;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import cn.wangtianya.learn.R;

public class NotUIDrawActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        test2();
    }

    // catch住，不会退出ac，不会蹦，但会有view绘制重叠，乱绘的情况
    private void test1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setContentView(R.layout.activity_not_uidraw);
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText("Love" + Thread.currentThread());
                } catch (Exception e) {
                    int i = 0;
                }

            }
        }).start();
    }

    //
    private void test2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_not_uidraw);
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("Love" + Thread.currentThread());
            }
        }).start();
    }
}
