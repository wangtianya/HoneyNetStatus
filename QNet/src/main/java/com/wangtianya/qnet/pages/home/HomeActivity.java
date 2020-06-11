package com.wangtianya.qnet.pages.home;

import com.wangtianya.architecure.tree.TreeActivity;
import com.wangtianya.qnet.ActivityMainBinding;
import com.wangtianya.qnet.pages.home.model.HomeModel;
import com.wangtianya.qnet.pages.home.presenter.DelayTaskPresenter;
import com.wangtianya.qnet.pages.home.presenter.HomeMainPresenter;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

public class HomeActivity extends TreeActivity {
    public ActivityMainBinding binding;
    public HomeModel model;

    public HomeMainPresenter mainPresenter;
    public DelayTaskPresenter delayTaskPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }


}
