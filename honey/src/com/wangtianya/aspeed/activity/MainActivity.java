
package com.wangtianya.aspeed.activity;

import com.wangtianya.yaa.core.activity.backhandle.YaaFragmentPageTask;
import com.wangtianya.aspeed.R;
import com.wangtianya.aspeed.core.ASContext;
import com.wangtianya.aspeed.event.PageSwitchEvent;
import com.wangtianya.aspeed.widget.slidingmenu.SlidingMenu;
import com.wangtianya.aspeed.widget.topbar.TopBarView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import de.greenrobot.event.EventBus;

/**
 * Created by tianya on 2015/8/31.
 */
public class MainActivity extends YaaFragmentPageTask {


    private SlidingMenu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ASContext.Caches.mainActivity = this;
        EventBus.getDefault().register(this);

        initViews();

        PageSwitchEvent.gotoPage(PageSwitchEvent.DELAY);
    }


    public void initViews() {
        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.activity_welcome);

        setLeftOnclick();
    }

    private void setLeftOnclick() {
//        mTopBar.setLeftButton(TopBarView.LeftButtonType.List, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                menu.showMenu();
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ASContext.Caches.topbar = null;
        ASContext.Caches.mainActivity = null;
    }

    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.toggle();
            return;
        }
        super.onBackPressed();
    }

    public void onEventMainThread(PageSwitchEvent event) {
        if (event.pageName.equals(PageSwitchEvent.DELAY)) {
            setLeftOnclick();
        }

//        mTopBar.setTitle(event.pageName);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (PageSwitchEvent.TransitionAnimation.Go.equals(event.transitionAnimation)) {
            ft.setCustomAnimations(R.animator.slide_go_right, R.animator.slide_go_left);
        } else if (PageSwitchEvent.TransitionAnimation.Back.equals(event.transitionAnimation)) {
            ft.setCustomAnimations(R.animator.slide_back_left, R.animator.slide_back_right);
        }
        setFragmentShouldAskOnBackPressed(event.fragment);
        ft.add(R.id.rlContainer, event.fragment);
        ft.disallowAddToBackStack();
        ft.commit();
    }
}
