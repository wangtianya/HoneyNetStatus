/*
 *.
 */
package cn.wangtianya.learn.ripple;

import com.wangtianya.learn.wiget.DataBindingBaseAdapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.wangtianya.learn.BR;
import cn.wangtianya.learn.R;
import cn.wangtianya.learn.databinding.ActivityRippleListitemBinding;

public class RippleInListItemActivity extends Activity {

    ActivityRippleListitemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ripple_listitem);

        ObservableArrayList<String> loves = new ObservableArrayList<>();
        loves.add("aaaaaa");
        loves.add("aaaaaa");
        loves.add("aaaaaa");
        loves.add("aaaaaa");
        loves.add("aaaaaa");


        binding.listView.setAdapter(new DataBindingBaseAdapter<>( R.layout.activity_ripple_listitem_adapter_item,
                loves, BR.str ));
    }

    class LoveAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View retView = convertView != null ? convertView : DataBindingUtil.inflate(LayoutInflater.from
                    (RippleInListItemActivity.this), R.layout.activity_ripple_listitem_adapter_item, null, false).getRoot();

            retView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return retView;
        }
    }
}
