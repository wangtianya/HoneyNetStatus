package cn.wangtianya.learn.widget.dialog;

import com.wangtianya.learn.common.ItemFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class DialogFragment extends ItemFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addClickItem("基础dialog", (v) -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setTitle("爱你哟");
            dialog.show();
        });
    }
}
