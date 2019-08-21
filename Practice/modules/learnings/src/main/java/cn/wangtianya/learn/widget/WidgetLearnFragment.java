/*
 *.
 */
package cn.wangtianya.learn.widget;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import cn.wangtianya.learn.widget.dialog.DialogFragment;
import cn.wangtianya.learn.widget.list.NomralListRefreshFragment;
import cn.wangtianya.learn.widget.list.TestBindingListViewRefreshFragment;
import cn.wangtianya.learn.widget.ripple.RippleFragment;

public class WidgetLearnFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("ListViewBinding刷新", TestBindingListViewRefreshFragment.class);
        addFragmentItem("ListViewBindingNormal", NomralListRefreshFragment.class);
        addFragmentItem("Dialog相关", DialogFragment.class);


        addFragmentItem("Ripple效果", RippleFragment.class);
    }
}
