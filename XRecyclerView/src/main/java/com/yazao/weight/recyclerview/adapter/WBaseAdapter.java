package com.yazao.weight.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.yazao.weight.recyclerview.holder.XViewHolder;
import com.yazao.weight.recyclerview.listener.OnItemClickListener;
import com.yazao.weight.recyclerview.listener.OnItemLongClickListener;

/**
 * 类描述：RecyclerView 通用的 适配器 (单个布局)
 *
 * <p>
 * 泛型T -- Item的数据bean
 * </p>
 * <pre>
 *
 *      //1. init adapter
 *      MainAdapter mainAdapter = new MainAdapter(getBaseContext(), R.layout.item_recycler_view_layout, dataBeanList);
 *      //2.1 item click
 *      mainAdapter.setOnItemClickListener(new OnItemClickListener() {
 *             @Override
 *             public void onItemClick(View v, int position, Object bean) {
 *
 *                 XToast.show("click position = " + position + ", bean = " + ((DataBean) bean).title);
 *             }
 *      });
 *
 *         //2.2 item long click
 *         mainAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
 *             @Override
 *             public boolean onItemLongClick(View v, int position, Object bean) {
 *                 XToast.show("long click position = " + position + ", bean = " + ((DataBean) bean).title);
 *                 return true;
 *             }
 *         });
 *
 *         //3. LayoutManager
 *         LinearLayoutManager layoutManager = new XLinearLayoutManager(this);
 *         recyclerView.setLayoutManager(layoutManager);
 *
 *         //4.1 addItemDecoration
 *         recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL, 100, 10, 10, R.color.colorAccent));
 *         //4.2
 *         recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL, 100, 10, 10, R.color.colorAccent));
 *
 *         //5. set adapter
 *         recyclerView.setAdapter(mainAdapter);
 *
 *         //6.in Activity's onDestroy()
 *         @Override
 *          protected void onDestroy() {
 *              if (mainAdapter != null) {
 *                   mainAdapter.clear();
 *              }
 *              super.onDestroy();
 *          }
 *
 * </pre>
 *
 * @author zhaishaoping
 * @data 24/01/2018 1:28 PM
 */
public abstract class WBaseAdapter<T extends Object> extends RecyclerView.Adapter<XViewHolder> {
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

    private XViewHolder holder;

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
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        XViewHolder holder = XViewHolder.getViewHolder(mContext, parent, mLayoutId);
        this.holder = holder;
        bindViewClickListener(holder);
        return holder;
    }

    //处理 item click 事件
    private void bindViewClickListener(final XViewHolder holder) {
        if (holder == null) {
            return;
        }

        final View rootView = holder.itemView;

        if (mOnItemClickListener != null) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) {
                        return;
                    }

                    T itemData = null;
                    if (mDatas != null && mDatas.size() > 0 && position < mDatas.size()) {
                        itemData = mDatas.get(position);
                    }

                    mOnItemClickListener.onItemClick(rootView, position, itemData);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) {
                        return false;
                    }

                    T itemData = null;
                    if (mDatas != null && mDatas.size() > 0 && position < mDatas.size()) {
                        itemData = mDatas.get(position);
                    }

                    return mOnItemLongClickListener.onItemLongClick(rootView, position, itemData);
                }

            });
        }
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, int position) {
        //主要用于数据、事件绑定，这里直接抽象出去，让用户去操作。

        if (mDatas == null || mDatas.size() <= 0) {
            return;
        }

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

    protected abstract void convert(XViewHolder holder, int position, T bean);

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

    public void setData(List<T> datas) {
        mDatas = datas;
        itemCount = mDatas == null ? 0 : mDatas.size();
        notifyDataSetChanged();
    }

    // https://www.jianshu.com/p/2eca433869e9
    public void notifyData(List<T> datas) {
        if (datas != null) {
            int previousSize = mDatas.size();
            mDatas.clear();
            notifyItemRangeRemoved(0, previousSize);
            mDatas.addAll(datas);
            notifyItemRangeInserted(0, datas.size());
        }
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


    public XViewHolder getViewHolder() {
        return holder;
    }

    public void clear() {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
        }

        XViewHolder holder = getViewHolder();
        if (holder != null) {
            holder.clear();
        }
    }
}
