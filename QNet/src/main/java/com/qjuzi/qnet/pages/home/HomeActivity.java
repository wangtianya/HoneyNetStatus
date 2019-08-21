package com.qjuzi.qnet.pages.home;

import com.qjuzi.architecure.tree.TreeActivity;
import com.qjuzi.architecure.tree.event.OnViewCreated;
import com.qjuzi.qnet.ActivityMainBinding;
import com.qjuzi.qnet.pages.home.model.HomeModel;
import com.qjuzi.qnet.pages.home.presenter.DelayTaskPresenter;
import com.qjuzi.qnet.pages.home.presenter.HomeMainPresenter;
import com.qjuzi.qnet.pages.home.presenter.TestLifecyclePresenter;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

public class HomeActivity extends TreeActivity {
    public ActivityMainBinding binding;
    public HomeModel model;

    public HomeMainPresenter mainPresenter;
    public DelayTaskPresenter delayTaskPresenter;
    public TestLifecyclePresenter testLifecyclePresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }

    @OnViewCreated
    public void onViewCreated() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

}
