package cn.wangtianya.learn.C1_FourBasicComponents;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import cn.wangtianya.learn.C1_FourBasicComponents.service.ServiceLearnFragment;

/**
 * Created by wangtianya on 2018/3/7.
 */

public class FourBasicComponentMainFragment extends ItemFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        addFragmentItem("Activity", null);

        addFragmentItem("service", ServiceLearnFragment.class);


    }
}
