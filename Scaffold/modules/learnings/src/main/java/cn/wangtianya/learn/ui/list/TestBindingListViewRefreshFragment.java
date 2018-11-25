
package cn.wangtianya.learn.ui.list;

import android.app.Fragment;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import cn.wangtianya.learn.BR;
import cn.wangtianya.learn.TestBindingListViewBinding;

public class TestBindingListViewRefreshFragment extends Fragment {

    public TestBindingListViewBinding binding;
    public ObservableArrayList<TestListViewRefreshItemModel> items = new ObservableArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = TestBindingListViewBinding.inflate(inflater);
        binding.setModel(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ArrayList<TestListViewRefreshItemModel> tempList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TestListViewRefreshItemModel item = new TestListViewRefreshItemModel();
            item.title.set("第 " + i + " 个");
            item.setVariable(BR.model, item);
            tempList.add(item);
        }
        items.addAll(tempList);
        binding.listView.setFocusableInTouchMode(false);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            TestListViewRefreshItemModel item = new TestListViewRefreshItemModel();
            item.title.set("插入到12");
//            items.add(item);
            items.remove(position);
        });

    }
}
