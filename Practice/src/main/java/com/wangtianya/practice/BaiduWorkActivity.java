package com.wangtianya.practice;

import com.wangtianya.learn.common.ItemActivity;
import com.wedcel.dragexpandgrid.DragGridTestFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import cn.wangtianya.learn.other.openapi.OpenApiFragment;
import cn.wangtianya.learn.other.temp.TempTestFragment;
import cn.wangtianya.learn.other.temp.utag.UniverseTagFragment;

public class BaiduWorkActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragmentItem("OpenApi", OpenApiFragment.class);
        addFragmentItem("Universe Tag", UniverseTagFragment.class);
        addFragmentItem("DragGridTestFragment", DragGridTestFragment.class);
        addFragmentItem("Temp Test", TempTestFragment.class);


        addOpenApi("希尔顿no 5000", "baidumap://map/place/search?query=希尔顿&location=4846323,"
                + "12947466&coord_type=bd09mc&src=webapp-aladdin.poi-general-hotel"
                + ".title&bdlog={\"baiduid\":\"94FDFF12056B059CAA6F99631BA2962D\"}&open_from=baiduapp");


        addOpenApi("希尔顿5000", "baidumap://map/place/search?query=希尔顿&location=4846323,"
                + "12947466&bounds=&radius=5000&coord_type=bd09mc&src=webapp-aladdin.poi-general-hotel"
                + ".title&bdlog={\"baiduid\":\"94FDFF12056B059CAA6F99631BA2962D\"}&open_from=baiduapp");


        addOpenApi("希尔顿50000", "baidumap://map/place/search?query=希尔顿&location=4846323,"
                + "12947466&bounds=&radius=50000&coord_type=bd09mc&src=webapp-aladdin.poi-general-hotel"
                + ".title&bdlog={\"baiduid\":\"94FDFF12056B059CAA6F99631BA2962D\"}&open_from=baiduapp");

        addDiliver();
    }
}
