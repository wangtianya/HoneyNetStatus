package com.wangtianya.learn.common;

import java.net.URISyntaxException;

import com.qjuzi.yaa.context.ContextCache;
import com.wangtianya.learn.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by tianya on 2017/4/27.
 */

public class ItemFragment extends Fragment {

    private LinearLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.ll_items, null);
        mainLayout = mView.findViewById(R.id.llItems);
        return mView;
    }

    protected void addFragmentItem(String tittle, Class<? extends Fragment> clazz) {
        mainLayout.addView(new ButtonFragmentItem(tittle, clazz));
    }

    protected void addItem(String title, View.OnClickListener onClickListener) {
        mainLayout.addView(YaUtil.newButton(title, 28, onClickListener));
    }

    protected void addOpenApi(String tittle, String uri) {
        addItem(tittle, v -> {
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
