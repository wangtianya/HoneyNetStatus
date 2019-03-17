package cn.wangtianya.learn.other.openapi;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by tianya on 2017/7/28.
 */

public class OpenApiFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addOpenApi("QRCode", "baidumap://map/component?comName=map.android.baidu.qrcode&target=scan_page&mode=NORMAL_MODE");
        addOpenApi("室内图", "baidumap://map/place/search?scene_search=1&query=军人专用候车区&building_id=16775196246402579752&building_name=北京南站&region=北京市");
    }
}
