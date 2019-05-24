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


        addOpenApi("deepLinkBug", "baidumap://map/place/search?src=com.oppo"
                + ".voiceassist&coord_type=bd09ll&query=清华大学&location=22.598699,113.976006&region=深圳市&radius=1000");

        addOpenApi("QRCode", "baidumap://map/place/search?query=世界之窗&region=深圳市&location=22.54074603311,113.97939921156&radius=1000&bounds=22.532400731737,113.97041615646,22.549090824185,113.98838226666");

        addOpenApi("希尔顿5000", "baidumap://map/place/search?query=希尔顿&location=4846323,"
                + "12947466&bounds=&radius=5000&coord_type=bd09mc&src=webapp-aladdin.poi-general-hotel"
                + ".title&bdlog={\"baiduid\":\"94FDFF12056B059CAA6F99631BA2962D\"}&open_from=baiduapp");


        addOpenApi("希尔顿50000", "baidumap://map/place/search?query=希尔顿&location=4846323,"
                + "12947466&bounds=&radius=50000&coord_type=bd09mc&src=webapp-aladdin.poi-general-hotel"
                + ".title&bdlog={\"baiduid\":\"94FDFF12056B059CAA6F99631BA2962D\"}&open_from=baiduapp");

    }
}
