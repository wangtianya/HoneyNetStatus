
package cn.wangtianya.learn.widget.ripple;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;

/**
 * Created by tianya on 2017/7/28.
 */

public class RippleFragment extends ItemFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragmentItem("ListView 子控件里2", RippleInListItemFragment.class);
    }
}
