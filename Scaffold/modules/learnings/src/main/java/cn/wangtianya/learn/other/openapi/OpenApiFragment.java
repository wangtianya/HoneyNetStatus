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
    }
}
