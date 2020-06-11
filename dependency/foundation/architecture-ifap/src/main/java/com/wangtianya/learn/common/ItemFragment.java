package com.wangtianya.learn.common;

import java.net.URISyntaxException;

import com.wangtianya.architecure.tree.Tree;
import com.wangtianya.learn.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    public void addActivityItem(String tittle, Class<? extends Activity> clazz) {
        mainLayout.addView(ButtonItemFactory.newActivityPageItem(tittle, clazz));
    }

    public void addFragmentItem(String tittle, Class<? extends Fragment> clazz) {
        mainLayout.addView(ButtonItemFactory.newFragmentPageItem(tittle, clazz));
    }

    public void addClickItem(String title, View.OnClickListener onClickListener) {
        mainLayout.addView(ButtonItemFactory.newClickItem(title, onClickListener));
    }

    public void addDiliver() {
        TextView view = new TextView(Tree.getContext());
        view.setMinHeight(100);
        mainLayout.addView(view);
    }

    public void addOpenApi(String tittle, String uri) {
        addClickItem(tittle, v -> {
            try {
                Intent intent = Intent.parseUri(uri,0);
                Tree.getContext().startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Toast.makeText(Tree.getContext(), "OPEN　API　启动错误", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
