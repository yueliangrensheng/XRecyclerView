package com.yazao.lib.weight.xrecyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yazao.lib.toast.XToast;
import com.yazao.weight.recyclerview.adapter.WBaseAdapter;
import com.yazao.weight.recyclerview.decoration.DividerItemDecoration;
import com.yazao.weight.recyclerview.decoration.SpacingItemDecoration;
import com.yazao.weight.recyclerview.holder.XViewHolder;
import com.yazao.weight.recyclerview.layoutmanager.XLinearLayoutManager;
import com.yazao.weight.recyclerview.listener.OnItemClickListener;
import com.yazao.weight.recyclerview.listener.OnItemLongClickListener;
import com.yazao.lib.weight.xrecyclerview.demo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

        //init
        XToast.init(this);

        List<DataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            dataBeanList.add(new DataBean("title" + i, R.mipmap.ic_launcher));
        }
        mainAdapter = new MainAdapter(getBaseContext(), R.layout.item_recycler_view_layout, dataBeanList);//Vertical
//        mainAdapter = new MainAdapter(getBaseContext(), R.layout.item_recycler_view_layout_horizontal, dataBeanList);//horizontal
        mainAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Object bean) {

                XToast.show("click position = " + position + ", bean = " + ((DataBean) bean).title);
            }
        });

        mainAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View v, int position, Object bean) {
                XToast.show("long click position = " + position + ", bean = " + ((DataBean) bean).title);
                return true;
            }
        });

        int spanCount = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);//多元素

//        LinearLayoutManager layoutManager = new XLinearLayoutManager(this);//Vertical
//        LinearLayoutManager layoutManager = new XLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);//horizontal
        recyclerView.setLayoutManager(layoutManager);

//        if (recyclerView.getItemDecorationCount() <= 0) {//RecyclerView嵌套RecyclerView时，防止 RecyclerView 滑动时，内部RecyclerView多次加载 addItemDecoration 造成item间距多次累积。
//        }

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL, 0, 0, 10, android.R.color.transparent));//Vertical
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL, 0, 0, 10, android.R.color.transparent));//horizontal


        int leftSpanCount = 5;//Item 左间距
        int topSpanCount = 5;//Item 上间距
        int rightSpanCount = 5;//Item 右间距
        int bottomSpanCount = 5;//Item 下间距

        int lastTopSpanCount = 30;//Item 第一行元素
        int lastLeftSpanCount = 30;//Item 最左边元素
        int lastRightSpanCount = 30;//Item 最右边元素
        int lastBottomSpanCount = 60;//Item 最后一行元素

        int leftOrRightSpanCount = 30;
        int topOrBottomSpanCount = 15;

        // SpacingItemDecoration 单元素
//        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(
//                leftOrRightSpanCount, topOrBottomSpanCount,
//                lastTopSpanCount, lastBottomSpanCount);

        // SpacingItemDecoration 多元素
        SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(spanCount,
                leftSpanCount,topSpanCount,rightSpanCount,bottomSpanCount,
                lastTopSpanCount,lastBottomSpanCount,lastLeftSpanCount,lastRightSpanCount);


        spacingItemDecoration.setOneSide(false);
        recyclerView.addItemDecoration(spacingItemDecoration);


        recyclerView.setAdapter(mainAdapter);
    }

    private class MainAdapter extends WBaseAdapter<DataBean> {

        public MainAdapter(Context context, int layoutId, List<DataBean> datas) {
            super(context, layoutId, datas);
        }

        public MainAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(final XViewHolder holder, int position, DataBean bean) {

            if (bean == null) {
                return;
            }

            holder.setText(R.id.title, bean.getTitle());
            holder.setImageResource(R.id.icon_header, bean.getIconRes());


        }
    }

    private class DataBean {
        private String title;
        private int iconRes;

        public DataBean(String title, int iconRes) {
            this.title = title;
            this.iconRes = iconRes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", iconRes=" + iconRes +
                    '}';
        }
    }


    @Override
    protected void onDestroy() {
        if (mainAdapter != null) {
            mainAdapter.clear();
        }
        super.onDestroy();
    }
}
