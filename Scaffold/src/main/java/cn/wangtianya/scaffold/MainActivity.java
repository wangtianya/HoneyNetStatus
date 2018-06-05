package cn.wangtianya.scaffold;

import com.wangtianya.learn.common.AppModel;
import com.wangtianya.learn.common.ItemActivity;
import com.wangtianya.learn.io.TempTest1Activity;

import android.content.Intent;
import android.os.Bundle;
import cn.wangtianya.learn.communication.CommunicationActivity;
import cn.wangtianya.learn.dialog.DialogActivity;
import cn.wangtianya.learn.event.EventActivity;
import cn.wangtianya.learn.html.HtmlActivity;
import cn.wangtianya.learn.memory.MemoryActivity;
import cn.wangtianya.learn.mvvm.MVVMActivity;
import cn.wangtianya.learn.ripple.RippleActivity;
import cn.wangtianya.learn.C1_FourBasicComponents.FourBasicComponentMainFragment;
import cn.wangtianya.learn.temp.TempTestActivity;
import cn.wangtianya.learn.uithread.UIThreadActivity;

public class MainActivity extends ItemActivity {
    public static final String diliver = "------------------------------------------------------------------------------------";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppModel.setContext(getApplication());


        addFragmentItem("四大组件", FourBasicComponentMainFragment.class);


        addItem("通信", CommunicationActivity.class);

        addItem(diliver, CommunicationActivity.class);

        addItem("Temp Test", TempTestActivity.class);
        addItem("UI线程相关", UIThreadActivity.class);
        addItem("MVVM相关", MVVMActivity.class);
        addItem("Dialog相关", DialogActivity.class);
        addItem("Memory相关", MemoryActivity.class);
        addItem("RippleActivity相关", RippleActivity.class);
        addFragmentItem("HtmlParse相关", HtmlActivity.class);

        addItem("Event相关", EventActivity.class);



        runOnUiThread(()->{startActivity(new Intent(this,TempTest1Activity.class));});
    }
}
