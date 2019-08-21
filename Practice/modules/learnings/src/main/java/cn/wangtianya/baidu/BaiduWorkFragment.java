/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */

/*
 *.
 */
package cn.wangtianya.baidu;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import cn.wangtianya.baidu.openapi.OpenApiFragment;
import cn.wangtianya.learn.other.temp.TempTestFragment;
import cn.wangtianya.learn.other.temp.utag.UniverseTagFragment;

public class BaiduWorkFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("OpenApi", OpenApiFragment.class);
        addFragmentItem("Universe Tag", UniverseTagFragment.class);
        addFragmentItem("Temp Test", TempTestFragment.class);
        addOpenApi("QRCode", "baidumap://map/line?src=com.oppo.voiceassist&name=12号线&region=深圳市");
    }
}
