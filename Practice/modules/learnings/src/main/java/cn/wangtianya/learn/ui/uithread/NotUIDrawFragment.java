
package cn.wangtianya.learn.ui.uithread;

import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.wangtianya.learn.R;

public class NotUIDrawFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_not_uidraw, container);
    }

    // catch住，不会退出ac，不会蹦，但会有view绘制重叠，乱绘的情况
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void test1() {
        new Thread(() -> {
            try {

                TextView textView = (TextView) getView().findViewById(R.id.textView);
                textView.setText("Love" + Thread.currentThread());
            } catch (Exception e) {
                int i = 0;
            }

        }).start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void test2() {
        new Thread(() -> {
            TextView textView = (TextView) getView().findViewById(R.id.textView);
            textView.setText("Love" + Thread.currentThread());
        }).start();
    }
}
