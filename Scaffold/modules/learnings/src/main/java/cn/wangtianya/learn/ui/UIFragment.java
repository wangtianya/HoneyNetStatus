/*
 *.
 */
package cn.wangtianya.learn.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangtianya.learn.common.ItemFragment;

import cn.wangtianya.learn.html.HtmlParseFragment;
import cn.wangtianya.learn.ui.list.NomralListRefreshFragment;
import cn.wangtianya.learn.ui.list.TestBindingListViewRefreshFragment;

public class UIFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("ListViewBinding刷新", TestBindingListViewRefreshFragment.class);

        addFragmentItem("ListViewBindingNormal", NomralListRefreshFragment.class);
    }
}
