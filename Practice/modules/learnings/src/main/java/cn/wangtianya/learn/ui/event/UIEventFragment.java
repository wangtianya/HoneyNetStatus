
package cn.wangtianya.learn.ui.event;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;

public class UIEventFragment extends ItemFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragmentItem("touch dispath", TouchDispathFragment.class);
    }
}
