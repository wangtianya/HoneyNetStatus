package cn.wangtianya.baidu;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import cn.wangtianya.baidu.openapi.OpenApiFragment;
import cn.wangtianya.learn.widget.utag.UniverseTagFragment;

public class BaiduWorkFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("OpenApi", OpenApiFragment.class);
        addFragmentItem("Universe Tag", UniverseTagFragment.class);
        addDiliver();
    }
}
