/*
 *.
 */
package cn.wangtianya.learn.sysbase.memory;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;

public class OOMFragment extends Fragment {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        ArrayList<Object> anyContentList = new ArrayList<>();
        anyContentList.add(new byte[1100000111]);
    }
}
