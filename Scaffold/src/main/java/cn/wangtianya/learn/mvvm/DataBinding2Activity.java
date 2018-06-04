/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.mvvm;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.wangtianya.learn.R;
import cn.wangtianya.learn.databinding.LayoutSearchBoxBinding;

public class DataBinding2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_layout);
        LinearLayout l = (LinearLayout) findViewById(R.id.llLayout);
        l.addView(new SearchBox(getApplicationContext()));
    }


    public static class SearchBox extends RelativeLayout {
        LayoutSearchBoxBinding binding;

        public SearchBox(Context context) {
            super(context);
            initView();
        }


        private void initView() {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout
                    .layout_search_box, null, false);
            this.addView(binding.getRoot());

            final Article article = new Article();
            article.tittle.set("网易的编辑都是XX");
            article.tittle.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable observable, int i) {
                    String a = ((ObservableField<String>) observable).get();
                }
            });
            binding.setArticle(article);
        }
    }

    public static class Article extends BaseObservable {


        public ObservableField<String> tittle = new ObservableField<>();


        public void onclick(View view) {
            this.tittle.set("Love" + new Random().nextFloat());
        }


        public void onclick1(View view) {
            LayoutSearchBoxBinding db = DataBindingUtil.getBinding((View) view.getParent());

            Article article = new Article();
            article.tittle.set("Love" + new Random().nextFloat());
            db.setArticle(article);
        }
    }

}
