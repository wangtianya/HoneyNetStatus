
package cn.wangtianya.learn.sysbase.memory;

import android.app.Fragment;
import android.os.Bundle;

public class LeakFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingletonObj.getInstance().list.add(this);
    }
}
