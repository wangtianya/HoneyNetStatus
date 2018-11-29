/*
 *.
 */
package cn.wangtianya.learn.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangtianya.learn.common.ItemFragment;

import cn.wangtianya.learn.widget.dialog.DialogActivity;
import cn.wangtianya.learn.widget.ripple.RippleFragment;
import cn.wangtianya.learn.widget.list.NomralListRefreshFragment;
import cn.wangtianya.learn.widget.list.TestBindingListViewRefreshFragment;

public class WidgetLearnFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("ListViewBinding刷新", TestBindingListViewRefreshFragment.class);
        addFragmentItem("ListViewBindingNormal", NomralListRefreshFragment.class);
        addActivityItem("Dialog相关", DialogActivity.class);


        addFragmentItem("Ripple效果", RippleFragment.class);
    }
}
