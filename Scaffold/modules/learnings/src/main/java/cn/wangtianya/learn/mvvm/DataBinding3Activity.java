/*
 *.
 */
package cn.wangtianya.learn.mvvm;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.wangtianya.learn.R;
import cn.wangtianya.learn.databinding.LayoutPeopleBinding;

public class DataBinding3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_layout);

        LinearLayout l = (LinearLayout) findViewById(R.id.llLayout);
        l.addView(new PeopleLayout(getApplicationContext()));

    }


    public static class PeopleLayout extends RelativeLayout {
        LayoutPeopleBinding binding;

        public PeopleLayout(Context context) {
            super(context);
            initView();
        }

        private void initView() {
//            binding = LayoutPeopleBinding.bind(LayoutInflater.from(getContext()).inflate(R.layout.layout_people, null));
//            this.addView(binding.getRoot());
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_people, this, true);


            ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();
            user.put("name", "Google");
            user.put("age", 17);
            binding.setPeople(user);
        }
    }
}
