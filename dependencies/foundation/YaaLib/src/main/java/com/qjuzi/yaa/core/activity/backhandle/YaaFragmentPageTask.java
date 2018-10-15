package com.qjuzi.yaa.core.activity.backhandle;

import android.app.Activity;

/**
 * 因为
 * Created by wangtianya on 2018/3/20.
 */

public class YaaFragmentPageTask extends Activity {

    private YaaBackHandleFragment mFragmentShouldAskOnBackPressed;

    /**
     * 默认反回true, 就是我这里ok了的意思, 你那里爱咋咋地
     */
    public interface ActivityOnBackPressedHandler {
        boolean onBackPressed();
    }

    public void setFragmentShouldAskOnBackPressed(YaaBackHandleFragment fragment) {
        this.mFragmentShouldAskOnBackPressed = fragment;
    }

    @Override
    public void onBackPressed() {
        if (mFragmentShouldAskOnBackPressed != null && !mFragmentShouldAskOnBackPressed.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
