
package com.qjuzi.tools.other.deprecated.iocV1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.wangtianya.architecure.tree.Tree;
import com.qjuzi.tools.other.deprecated.iocV1.annotation.InjectExtra;
import com.qjuzi.tools.other.deprecated.iocV1.annotation.InjectResource;
import com.qjuzi.tools.other.deprecated.iocV1.annotation.InjectView;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

/**
 * 注入工具类，不再使用，现在用databinding
 */
@Deprecated
public class InjectUtil {
    /**
     * 切记初始化 YaaContext
     */
    public static void inject(Object obj) {
        if (obj == null) {
            return;
        }
        // 本类中的所有属性
        Field[] fields = getDeclaredFields(obj.getClass());
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) != null) {
                        continue;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // 注入view
                InjectView viewInject = field.getAnnotation(InjectView.class);
                if (viewInject != null) {
                    indectView(obj, field, viewInject);
                }

                // extra
                if (obj instanceof Activity || obj instanceof Fragment) {
                    InjectExtra extra = field.getAnnotation(InjectExtra.class);
                    if (extra != null) {
                        getExtras(obj, field, extra);
                    }
                }
                // Resource
                InjectResource resource = field.getAnnotation(InjectResource.class);
                if (resource != null) {
                    getResource(obj, field, resource);
                }

            }

        }
    }

    private static void getResource(Object obj, Field field, InjectResource resource) {
        Resources res = Tree.getContext().getResources();
        Object value = null;
        if (resource.color() != 0) {
            value = res.getColor(resource.color());
        } else if (resource.drawable() != 0) {
            value = res.getDrawable(resource.drawable());
        } else if (resource.string() != 0) {
            value = res.getString(resource.string());
        } else if (resource.dimen() != 0) {
            value = res.getDimensionPixelSize(resource.dimen());
        }
        if (value != null) {
            try {
                field.set(obj, value);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注入
     *
     * @param obj
     * @param field
     * @param injectview
     */
    public static void indectView(Object obj, Field field, InjectView injectview) {
        // view
        InjectView viewInject = field.getAnnotation(InjectView.class);
        View view = null;

        int layout = viewInject.layout();
        // layout中获取
        if (layout != 0) {
            view = LayoutInflater.from(Tree.getContext().getApplicationContext()).inflate(layout, null);
        } else {
            // 在其他view中的view
            String inView = viewInject.inView();
            if (!TextUtils.isEmpty(inView)) {
                try {
                    Field inViewField = obj.getClass().getDeclaredField(inView);
                    inViewField.setAccessible(true);
                    View parentView = (View) inViewField.get(obj);
                    if (parentView == null) {
                        indectView(obj, inViewField, inViewField.getAnnotation(InjectView.class));
                        parentView = (View) inViewField.get(obj);
                    }
                    view = parentView.findViewById(viewInject.id());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                // 在activity中的view
                if (obj instanceof Activity) {
                    Activity act = (Activity) obj;
                    view = act.findViewById(viewInject.id());
                }
                if (obj instanceof Dialog) {
                    Dialog act = (Dialog) obj;
                    view = act.findViewById(viewInject.id());
                }
                if (obj instanceof Fragment) {
                    Fragment act = (Fragment) obj;
                    view = act.getView().findViewById(viewInject.id());
                } else if (obj instanceof View) {
                    View vtemp = (View) obj;
                    view = vtemp.findViewById(viewInject.id());
                }
            }
        }

        try {
            field.set(obj, view);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 事件绑定
        String clickMethod = viewInject.click();
        if (!TextUtils.isEmpty(clickMethod)) {
            setViewClickListener(obj, field, clickMethod);
        }

        String longClickMethod = viewInject.longClick();
        if (!TextUtils.isEmpty(longClickMethod)) {
            setViewLongClickListener(obj, field, longClickMethod);
        }

        String itemClickMethod = viewInject.itemClick();
        if (!TextUtils.isEmpty(itemClickMethod)) {
            setItemClickListener(obj, field, itemClickMethod);
        }

        String itemLongClickMethod = viewInject.itemLongClick();
        if (!TextUtils.isEmpty(itemLongClickMethod)) {
            setItemLongClickListener(obj, field, itemLongClickMethod);
        }

    }

    public static void getExtras(Object activity, Field field, InjectExtra extra) {
        Bundle bundle = null;
        if (activity instanceof Activity) {
            Activity ac = (Activity) activity;
            bundle = ac.getIntent().getExtras();
        } else if (activity instanceof Fragment) {
            Fragment fg = (Fragment) activity;
            bundle = fg.getArguments();
        }
        if (bundle == null) {
            bundle = new Bundle();
        }

        try {
            Object obj = null;
            Class<?> clazz = field.getType();
            if (clazz.equals(Integer.class)) {
                if (!TextUtils.isEmpty(extra.def())) {
                    obj = bundle.getInt(extra.name(), Integer.parseInt(extra.def()));
                } else {
                    obj = bundle.getInt(extra.name(), 0);
                }
            } else if (clazz.equals(String.class)) {
                obj = bundle.getString(extra.name());
                if (obj == null) {
                    if (!TextUtils.isEmpty(extra.def())) {
                        obj = extra.def();
                    }
                }
            } else if (clazz.equals(Long.class)) {
                if (!TextUtils.isEmpty(extra.def())) {
                    obj = bundle.getLong(extra.name(), Long.parseLong(extra.def()));
                } else {
                    obj = bundle.getLong(extra.name(), 0);
                }
            } else if (clazz.equals(Float.class)) {
                if (!TextUtils.isEmpty(extra.def())) {
                    obj = bundle.getFloat(extra.name(), Float.parseFloat(extra.def()));
                } else {
                    obj = bundle.getFloat(extra.name(), 0);
                }
            } else if (clazz.equals(Double.class)) {
                if (!TextUtils.isEmpty(extra.def())) {
                    obj = bundle.getDouble(extra.name(), Double.parseDouble(extra.def()));
                } else {
                    obj = bundle.getDouble(extra.name(), 0);
                }
            } else if (clazz.equals(Boolean.class)) {
                if (!TextUtils.isEmpty(extra.def())) {
                    obj = bundle.getBoolean(extra.name(), Boolean.parseBoolean(extra.def()));
                } else {
                    obj = bundle.getBoolean(extra.name(), true);
                }

            } else if ((clazz.equals(JSONObject.class))) {
                String objstr = bundle.getString(extra.name());
                if (!TextUtils.isEmpty(objstr)) {
                    obj = new JSONObject(objstr);
                }
            } else if (clazz.equals(JSONArray.class)) {
                String objstr = bundle.getString(extra.name());
                if (!TextUtils.isEmpty(objstr)) {
                    obj = new JSONArray(objstr);
                }
            } else {
                String objstr = bundle.getString(extra.name());
                if (!TextUtils.isEmpty(objstr)) {
                    try {
                        obj = new Gson().fromJson(objstr, clazz);
                    } catch (Exception e) {
                    }
                }
            }
            if (obj != null) {
                field.set(activity, obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 点击控件
     * 2 控件触发EventListener
     * 3 EventListener obj 回调同名方法
     */
    private static void setViewClickListener(Object activity, Field field, String clickMethod) {
        try {
            Object obj = field.get(activity);
            if (obj instanceof View) {
                ((View) obj).setOnClickListener(new EventListener(activity).click(clickMethod));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setViewLongClickListener(Object activity, Field field, String clickMethod) {
        try {
            Object obj = field.get(activity);
            if (obj instanceof View) {
                ((View) obj).setOnLongClickListener(new EventListener(activity).longClick(clickMethod));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setItemClickListener(Object activity, Field field, String itemClickMethod) {
        try {
            Object obj = field.get(activity);
            if (obj instanceof AdapterView) {
                ((AdapterView<?>) obj).setOnItemClickListener(new EventListener(activity).itemClick(itemClickMethod));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setItemLongClickListener(Object activity, Field field, String itemClickMethod) {
        try {
            Object obj = field.get(activity);
            if (obj instanceof AdapterView) {
                ((AdapterView<?>) obj)
                        .setOnItemLongClickListener(new EventListener(activity).itemLongClick(itemClickMethod));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        List<Field> result = new ArrayList<Field>();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if ("serialVersionUID".equals(field.getName())) {// ingore
                    continue;
                }
                result.add(field);
            }
        } catch (Exception e) {
        }
        return result.toArray(new Field[0]);
    }

}
