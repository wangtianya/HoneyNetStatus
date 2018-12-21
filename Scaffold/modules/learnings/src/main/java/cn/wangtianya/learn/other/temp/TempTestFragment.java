/*
 *.
 */
package cn.wangtianya.learn.other.temp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangtianya.learn.common.ItemFragment;

import cn.wangtianya.learn.other.temp.utag.UniverseTagFragment;

public class TempTestFragment extends ItemFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("Universe Tag", UniverseTagFragment.class);
    }
}
