/*
 *.
 */
package cn.wangtianya.learn.ui.uithread;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;

public class UIThreadFragment extends ItemFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragmentItem("非UI线程进行绘制", NotUIDrawFragment.class);
    }
}
