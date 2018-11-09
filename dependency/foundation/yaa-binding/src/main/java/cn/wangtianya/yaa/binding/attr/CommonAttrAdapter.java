package cn.wangtianya.yaa.binding.attr;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by tianya on 2017/6/28.
 */

public class CommonAttrAdapter {

    @BindingAdapter("android:touchListener")
    public static void setTouchListener(View view, View.OnTouchListener touchListener) {
        view.setOnTouchListener(touchListener);
    }

    @BindingAdapter("android:background")
    public static void setSrc(LinearLayout view, int resId) {
        view.setBackgroundResource(resId);
    }
}
