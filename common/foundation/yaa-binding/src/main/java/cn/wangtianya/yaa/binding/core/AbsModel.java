package cn.wangtianya.yaa.binding.core;

import android.databinding.BaseObservable;

public abstract class AbsModel<S extends AbsStore> extends BaseObservable {

    public S store;

    public void setAbsPresenter(S store) {
        this.store = store;
    }

}
