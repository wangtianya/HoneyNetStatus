package com.qjuzi.yaa.core.activity.backhandle;

import com.qjuzi.yaa.core.activity.YaaFragment;

/**
 * Created by wangtianya on 2018/3/20.
 */

public class YaaBackHandleFragment extends YaaFragment implements YaaFragmentPageTask.ActivityOnBackPressedHandler {

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
