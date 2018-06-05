package cn.wangtianya.yaa.binding.core;

public abstract class AbsPresenter<S extends AbsStore> {

    public S store;

    public void setAbsPresenter(S store) {
        this.store = store;
    }

}
