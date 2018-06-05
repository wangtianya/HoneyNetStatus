
package com.qjuzi.yaa.context;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * activity 栈管理
 * 统一管理，便于结束程序时的清理(完全退出)
 * <p/>
 * create, 2014/11/02, Daya, i(a)wangtianya.cn
 */
@Deprecated
public class ActivityStack {

    public static ActivityStack stack = new ActivityStack();
    public List<Activity> activityList = new ArrayList<Activity>();

    private ActivityStack() {

    }

    public static ActivityStack getActivityStack() {
        return stack;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 完全退出
     */
    public void exit() {
        while (activityList.size() > 0) {
            activityList.get(activityList.size() - 1).finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}