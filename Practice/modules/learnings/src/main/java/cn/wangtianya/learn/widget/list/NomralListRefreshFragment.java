
package cn.wangtianya.learn.widget.list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.wangtianya.learn.NormalListRefreshBinding;
;

public class NomralListRefreshFragment extends Fragment {

    public NormalListRefreshBinding binding;
    public int total = 5;
    public MyBaseAdapter adapter = new MyBaseAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = NormalListRefreshBinding.inflate(inflater);
        binding.setModel(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            textView.setText("insert text: " + position);

            total++;
            adapter.notifyDataSetChanged();
        });
    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return total;
        }

        @Override
        public Object getItem(int position) {
            return total;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            textView.setText("text: " + position);
            return textView;
        }
    }
}
