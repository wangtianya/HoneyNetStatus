/*
 *.
 */
package cn.wangtianya.learn.ui.event;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import cn.wangtianya.learn.R;

/**
 * Created by tianya on 2017/7/28.
 */

public class TouchDispathFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dispatch, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getView().findViewById(R.id.layer0).setTag("layer0");
        getView().findViewById(R.id.layer0).setOnTouchListener(onTouchListener);

        getView().findViewById(R.id.layer1).setTag("layer1");
        getView().findViewById(R.id.layer1).setOnTouchListener(onTouchListener);

        getView().findViewById(R.id.layer2).setTag("layer2");
        getView().findViewById(R.id.layer2).setOnTouchListener(onTouchListener);

        getView().findViewById(R.id.layer3).setTag("layer3");
        getView().findViewById(R.id.layer3).setOnTouchListener(onTouchListener);
    }


    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            String myId = v.getTag().toString();
            Log.e(myId, event.getAction() + "");

            if ("layer2".equals(myId)) {
                return true;
            }
            return false;
        }
    };
}
