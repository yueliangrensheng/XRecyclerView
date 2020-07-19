package com.yazao.weight.recyclerview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yazao.weight.recyclerview.holder.XViewHolder;

import java.util.List;

/**
 * 类描述：RecyclerView 通用的 适配器（多个不同的Item布局）
 *
 * @author zhaishaoping
 * @data 24/01/2018 6:12 PM
 */

public abstract class WBaseMultiItemTypeAdapter<T> extends WBaseAdapter<T> {

    //ItemViewType
    public static final int ITEM_VIEW_TYPE_HEAD = 100;
    public static final int ITEM_VIEW_TYPE_FOOT = 101;
    public static final int ITEM_VIEW_TYPE_ONE = 1;
    public static final int ITEM_VIEW_TYPE_TWO = 2;
    public static final int ITEM_VIEW_TYPE_THREE = 3;
    public static final int ITEM_VIEW_TYPE_FOUR = 4;

    protected MultiItemTypeInterface mMultiItemTypeInterface;

    public WBaseMultiItemTypeAdapter(Context context, List<T> datas, MultiItemTypeInterface multiItemTypeInterface) {
        super(context, -1, datas);
        mMultiItemTypeInterface = multiItemTypeInterface;
    }

    @Override
    public int getItemViewType(int position) {
        // 根据bean 返回不同的 itemType
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

        return mMultiItemTypeInterface.getItemViewType(position, bean);
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据不同的 viewType 来生成不同的 layoutId。//ViewHolder 是通用的。
        int layoutId = mMultiItemTypeInterface.getLayoutId(viewType);
        XViewHolder holder = XViewHolder.getViewHolder(mContext, parent, layoutId);
        return holder;
    }
}
