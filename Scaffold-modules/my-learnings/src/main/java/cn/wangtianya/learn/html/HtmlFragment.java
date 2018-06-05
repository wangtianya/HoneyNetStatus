/*
 *.
 */
package cn.wangtianya.learn.html;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by tianya on 2017/7/28.
 */

public class HtmlFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("自定义Tag解析", HtmlParseFragment.class);
    }
}
