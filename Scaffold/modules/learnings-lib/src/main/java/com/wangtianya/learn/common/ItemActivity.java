package com.wangtianya.learn.common;

import com.qjuzi.architecure.base.context.ContextCache;
import com.wangtianya.learn.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.URISyntaxException;

/**
 * Created by tianya on 2017/4/27.
 */

public class ItemActivity extends Activity {

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_items);
        mainLayout = findViewById(R.id.llItems);
    }

    public void addActivityItem(String tittle, Class<? extends Activity> clazz) {
        mainLayout.addView(ButtonItemFactory.newActivityPageItem(tittle, clazz));
    }

    public void addFragmentItem(String tittle, Class<? extends Fragment> clazz) {
        mainLayout.addView(ButtonItemFactory.newFragmentPageItem(tittle, clazz));
    }

    public void addClickItem(String title, View.OnClickListener onClickListener) {
        mainLayout.addView(ButtonItemFactory.newClickItem(title, onClickListener));
    }

    public void addOpenApi(String tittle, String uri) {
        addClickItem(tittle, v -> {
            try {
                Intent intent = Intent.parseUri(uri,0);
                ContextCache.getContext().startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Toast.makeText(ContextCache.getContext(), "OPEN　API　启动错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
