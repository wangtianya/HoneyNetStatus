package cn.wangtianya.yaa.binding.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;

public abstract class AbsStore<P> {

    public Activity context;
    public P page;


    public AbsStore(P p) {
        page = p;

        if (p instanceof Fragment) {
            context = ((Fragment) p).getActivity();
        } else if (p instanceof Activity) {
            context = (Activity) p;
        } else if (p instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) p).getActivity();
        }

        for (Field field : getClass().getFields()) {
            if (android.databinding.ViewDataBinding.class.equals(field.getType().getSuperclass())) {
                inflateBinding(field);
                Log.d(AbsStore.class.getSimpleName(),"inject binding:  "  + field.getName());
            } else if (AbsPresenter.class.equals(field.getType().getSuperclass())) {
                injectPresenter(field);
                Log.d(AbsStore.class.getSimpleName(),"inject presenter:  "  + field.getName());
            } else if (AbsModel.class.equals(field.getType().getSuperclass())) {
                injectModel(field);
                Log.d(AbsStore.class.getSimpleName(),"inject model:  "  + field.getName());
            }
        }
    }

    private void inflateBinding(Field field) {
        try {
            Method method = field.getType().getMethod("inflate", android.view.LayoutInflater.class);
            Object obj = method.invoke(null, LayoutInflater.from(context));
            field.set(this, obj);
        } catch (Exception e) {
            Log.d(AbsStore.class.getSimpleName(),e.getMessage());
        }
    }

    private void injectPresenter(Field field) {
        try {
            AbsPresenter presenter = (AbsPresenter) field.getType().getConstructor().newInstance();
            presenter.setAbsPresenter(this);
            field.set(this, presenter);
        } catch (Exception e) {
            Log.d(AbsStore.class.getSimpleName(),e.getMessage());
        }
    }


    private void injectModel(Field field) {
        try {
            AbsModel model = (AbsModel) field.getType().getConstructor().newInstance();
            model.setAbsPresenter(this);
            field.set(this, model);
        } catch (Exception e) {
            Log.d(AbsStore.class.getSimpleName(),e.getMessage());
        }
    }

}
