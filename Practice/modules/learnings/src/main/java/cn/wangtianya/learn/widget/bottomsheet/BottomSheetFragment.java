package cn.wangtianya.learn.widget.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qjuzi.architecure.tree.TreeFragment;

import cn.wangtianya.learn.databinding.LayoutBottomSheetBinding;

public class BottomSheetFragment extends TreeFragment {
    LayoutBottomSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return binding.getRoot();
    }
}
