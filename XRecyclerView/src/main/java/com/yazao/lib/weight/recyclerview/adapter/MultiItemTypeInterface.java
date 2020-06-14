package com.yazao.lib.weight.recyclerview.adapter;

/**
 * 类描述：RecyclerView 通用的 适配器（多个不同的Item布局)
 *
 * @author zhaishaoping
 * @data 24/01/2018 6:16 PM
 */

public interface MultiItemTypeInterface<T> {

    // 根据bean 返回不同的 itemType
    int getItemViewType(int position, T bean);
    // 根据不同的 viewType 来生成不同的 layoutId。
    int getLayoutId(int viewType);
}
