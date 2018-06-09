
package cn.wangtianya.learn.recycler;

import com.qjuzi.yaa.context.YaaContext;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.wangtianya.yaa.binding.custom.YaaListView;
import cn.wangtianya.yaa.binding.custom.YaaRecyclerView1;
import cn.wangtianya.yaa.binding.custom.YaaScrollView;

public class ThreeRecyclerFragment extends Fragment {

    YaaScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        scrollView = new YaaScrollView(getActivity());

        int h = getSreenHeight(YaaContext.getContext()) - getStatusBarHeight(YaaContext.getContext());

        scrollView.setHeight(h, 500);

        scrollView.addContent(getRecycler());

        return scrollView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.postDelayed(() -> scrollView.updateStatus(YaaScrollView.PageStatus.Mid, true), 800);
    }

    public RecyclerView getRecycler() {
        RecyclerView recyclerView = new YaaRecyclerView1(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                textView.setText("我的位置：" + viewType);
                textView.setBackgroundColor(Color.parseColor("#3385ff"));
                return new ViewHolder(textView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "potstion : " + position, Toast.LENGTH_LONG).show();
                        ;
                    }
                });
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public int getItemCount() {
                return 33;
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                TextView textView;

                public ViewHolder(View itemView) {
                    super(itemView);
                    this.textView = (TextView) itemView;
                }
            }
        });

        return recyclerView;
    }

    public ListView getList() {
        ListView listView = new YaaListView(getActivity());
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 33;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
                textView.setText("我的位置：" + position);
                textView.setBackgroundColor(Color.parseColor("#3385ff"));
                return textView;
            }
        });

        listView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return listView;
    }

    public static int getStatusBarHeight(Context context) {
        if (context instanceof Activity) {
            Rect rectangle = new Rect();
            Window window = ((Activity) context).getWindow();
            if (window != null && window.getDecorView() != null) {
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            }
            return rectangle.top;
        } else {
            int statusBarHeight1 = -1;
            //获取status_bar_height资源的ID
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
            }
            return statusBarHeight1;
        }
    }

    public static int getSreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
