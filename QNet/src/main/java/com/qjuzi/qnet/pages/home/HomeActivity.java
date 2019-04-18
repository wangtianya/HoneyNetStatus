package com.qjuzi.qnet.pages.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import com.qjuzi.architecure.mvvm.MVVMActivity;
import com.qjuzi.architecure.mvvm.OnViewCreated;
import com.qjuzi.qnet.ActivityMainBinding;
import com.qjuzi.qnet.pages.home.model.HomeModel;
import com.qjuzi.qnet.pages.home.presenter.DelayTaskPresenter;
import com.qjuzi.qnet.pages.home.presenter.HomeMainPresenter;
import com.qjuzi.qnet.pages.home.presenter.TestLifecyclePresenter;

public class HomeActivity extends MVVMActivity {
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
