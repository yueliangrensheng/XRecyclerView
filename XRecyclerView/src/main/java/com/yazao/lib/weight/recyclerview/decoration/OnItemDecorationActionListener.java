package com.yazao.lib.weight.recyclerview.decoration;

import android.graphics.Canvas;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 08/02/2018 9:51 PM
 */

public interface OnItemDecorationActionListener {
    /**
     *  计算position 与 position - 1  是否处于同一类
     * @param position
     * @return
     */
    boolean hasSameCategory(int position);

    /** 绘制分类Item
     *  @param c
     * @param parent
     * @param position
     * @param layoutParams
     * @param suspensionHeight 分类Title Item的高度
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void drawCategoryView(Canvas c, RecyclerView parent, int position, RecyclerView.LayoutParams layoutParams, int suspensionHeight, int left, int top, int right, int bottom);

    /**
     *  绘制悬浮Item
     * @param c
     * @param parent
     * @param position
     * @param layoutParams
     * @param suspensionHeight
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void drawOverCategoryView(Canvas c, RecyclerView parent, int position, RecyclerView.LayoutParams layoutParams, int suspensionHeight, int left, int top, int right, int bottom);
}
