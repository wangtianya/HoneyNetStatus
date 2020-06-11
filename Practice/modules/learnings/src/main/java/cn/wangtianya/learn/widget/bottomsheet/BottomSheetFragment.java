package cn.wangtianya.learn.widget.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangtianya.architecure.tree.TreeFragment;

import cn.wangtianya.learn.databinding.LayoutBottomSheetBinding;

public class BottomSheetFragment extends TreeFragment {
    LayoutBottomSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ThreeLayerSheetBehavior behavior = ThreeLayerSheetBehavior.from(binding.frontContainer);
        behavior.setPeekHeight((int) (getActivity().getResources().getDisplayMetrics().density * 350));
        behavior.setHideHeight((int) (getActivity().getResources().getDisplayMetrics().density * 50));

        behavior.setHideable(true);

        behavior.setBottomSheetCallback(new ThreeLayerSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.e("onSlide", "" + v);
            }
        });


        behavior.setForceScrolledChild(binding.scroll);
        binding.webview.loadUrl("https://job.csdn.net/");


        binding.getRoot().setOnClickListener(view -> {
            behavior.setState(ThreeLayerSheetBehavior.STATE_COLLAPSED);
        });
        return binding.getRoot();
    }
}
