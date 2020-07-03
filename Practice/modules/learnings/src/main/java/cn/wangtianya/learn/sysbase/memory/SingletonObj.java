
package cn.wangtianya.learn.sysbase.memory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;

public class SingletonObj {
    public final  ArrayList<Object> list = new ArrayList<>();
    public final  ArrayList<WeakReference<Activity>> weakList = new ArrayList<>();

    private static SingletonObj instance;
    public static SingletonObj getInstance() {
        if (instance == null) {
            synchronized(SingletonObj.class) {
                if (instance == null) {
                    instance = new SingletonObj();
                }
            }
        }
        return instance;
    }
    private SingletonObj() {}
}
