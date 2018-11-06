package cn.wangtianya.learn.io;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by tianya on 2017/7/28.
 */

public class IOFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addItem("获取目录", (v)->{});
    }


    private void getDir() {

    }
}
