package com.yazao.lib.weight.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：RecyclerView 通用的 适配器 (单个布局)
 *
 * @author zhaishaoping
 * @data 24/01/2018 1:28 PM
 */
public abstract class WBaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    protected List<T> mDatas = new ArrayList<T>();
    protected int itemCount = 0;//Item个数
    protected boolean hasHeadLayout = false;
    protected boolean hasFootLayout = false;
    protected Object object;//特殊情况下，传递不同的 T 类型数据结构

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public WBaseAdapter() {
    }

    public WBaseAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public WBaseAdapter(Context context, int layoutId) {
        mContext = context;
        mLayoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = ViewHolder.getViewHolder(mContext, parent, mLayoutId);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //主要用于数据、事件绑定，这里直接抽象出去，让用户去操作。

        //如果有 head layout 的话，那么 这个bean是空的，可通过 WBaseAdapter.setObject() 来设置 头布局的数据结构
        Object bean = null;
        if (hasHeadLayout) {
            if (position == 0 || position >= mDatas.size()) {
            } else {
                bean = mDatas.get(position - 1);
            }

        } else {
            bean = mDatas.get(position);
        }
        convert(holder, position, (T) bean);
    }

    protected abstract void convert(ViewHolder holder, int position, T bean);

    @Override
    public int getItemCount() {
        itemCount = mDatas == null ? 0 : mDatas.size();
        if (hasHeadLayout) {
            itemCount += 1;
        } else if (hasFootLayout) {
            itemCount += 1;
        }

        return itemCount;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
        itemCount = mDatas == null ? 0 : mDatas.size();
        notifyDataSetChanged();
    }

    /**
     * 标记 添加 头布局
     */
    public void addHeadLayout() {
        hasHeadLayout = true;
        notifyDataSetChanged();
    }

    /**
     * 标记 添加 底部布局
     */
    public void addFootLayout() {
        hasFootLayout = true;
        notifyDataSetChanged();
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }
}
