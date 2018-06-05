package cn.wangtianya.yaa.binding.widget;

/**
 * Created by wangtianya on 2018/4/20.
 */

public abstract class BindingAdapterItemModel {
    public int layoutId;
    public int variableId;

    public void setResId(int layoutId, int variableId) {
        this.layoutId = layoutId;
        this.variableId = variableId;
    }
}
