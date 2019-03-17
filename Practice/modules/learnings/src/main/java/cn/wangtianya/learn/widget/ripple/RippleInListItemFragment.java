/*
 *.
 */
package cn.wangtianya.learn.widget.ripple;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.wangtianya.learn.BR;
import cn.wangtianya.learn.R;
import cn.wangtianya.learn.databinding.ActivityRippleListitemBinding;

public class RippleInListItemFragment extends Fragment {

    ActivityRippleListitemBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityRippleListitemBinding.inflate(inflater);
        ObservableArrayList<String> loves = new ObservableArrayList<>();
        loves.add("aaaaaa");
        loves.add("aaaaaa");
        loves.add("aaaaaa");
        loves.add("aaaaaa");
        loves.add("aaaaaa");


        binding.listView.setAdapter(new DataBindingBaseAdapter<>( R.layout.activity_ripple_listitem_adapter_item,
                loves, BR.str ));
        return binding.getRoot();
    }
}
