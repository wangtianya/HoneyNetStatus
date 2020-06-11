package cn.wangtianya.learn.四大组件;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.wangtianya.learn.四大组件.activity.ActivityLearnMainFragment;
import cn.wangtianya.learn.四大组件.service.ServiceLearnFragment;

/**
 * Created by wangtianya on 2018/3/7.
 */

public class FourBasicComponentMainFragment extends ItemFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        addFragmentItem("Activity", ActivityLearnMainFragment.class);
        addFragmentItem("service", ServiceLearnFragment.class);


    }
}
