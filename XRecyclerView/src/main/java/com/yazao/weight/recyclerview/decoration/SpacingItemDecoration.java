package com.yazao.weight.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.yazao.lib.xlog.Log;


/**
 * 类描述：RecyclerView Item间距
 *
 * @author zhaishaoping
 * @data 15/11/2017 2:57 PM
 */

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanSize = 1;// 每行/列 显示 Item 的数量
    private int leftSpanCount = 0;//Item 左间距
    private int topSpanCount = 0;//Item 上间距 
    private int rightSpanCount = 0;//Item 右间距 
    private int bottomSpanCount = 0;//Item 下间距 
    private int lastTopSpanCount = 0;//Item 第一行元素 
    private int lastLeftSpanCount = 0;//Item 最左边元素 
    private int lastRightSpanCount = 0;//Item 最右边元素 
    private int lastBottomSpanCount = 0;//Item 最后一行元素 
    //头布局相关
    private boolean hasHead = false;//是否有头布局
    int headLeftSpanCount = 0;
    int headRightSpanCount = 0;
    //是否只使用对应一边的值,即 左右、上下 四个方向 只选取 一组中的一个方向
    boolean isOneSide = false;


    /**
     * 每行/列 仅一个元素的 构造方法 (无header)
     */
    public SpacingItemDecoration(int leftOrRightSpanCount, int topOrBottomSpanCount, int lastTopSpanCount, int lastBottomSpanCount) {
        this(false, 0, 0,
                leftOrRightSpanCount, topOrBottomSpanCount, leftOrRightSpanCount, topOrBottomSpanCount,
                lastTopSpanCount, lastBottomSpanCount);
    }

    /**
     * 每行/列 仅一个元素的 构造方法（可判断有无header；左右间距和上下间距 分别相同）
     */
    public SpacingItemDecoration(boolean hasHead, int headLeftSpanCount, int headRightSpanCount,
                                 int leftOrRightSpanCount, int topOrBottomSpanCount, int lastTopSpanCount, int lastBottomSpanCount) {

        this(hasHead, headLeftSpanCount, headRightSpanCount,
                leftOrRightSpanCount, topOrBottomSpanCount, leftOrRightSpanCount, topOrBottomSpanCount,
                lastTopSpanCount, lastBottomSpanCount);
    }

    /**
     * 每行/列 仅一个元素的 构造方法（可判断有无header；分别设置各个方向的间距）
     */
    public SpacingItemDecoration(boolean hasHead, int headLeftSpanCount, int headRightSpanCount,
                                 int leftSpanCount, int topSpanCount, int rightSpanCount, int bottomSpanCount,
                                 int lastTopSpanCount, int lastBottomSpanCount) {
        this(hasHead, headLeftSpanCount, headRightSpanCount,
                1,
                leftSpanCount, topSpanCount, rightSpanCount, bottomSpanCount,
                lastTopSpanCount, lastBottomSpanCount, 0, 0);

    }

    /**
     * 每行/列 多个元素的 构造方法
     */
    public SpacingItemDecoration(boolean hasHead, int headLeftSpanCount, int headRightSpanCount,
                                 int spanSize,
                                 int leftSpanCount, int topSpanCount, int rightSpanCount, int bottomSpanCount,
                                 int lastTopSpanCount, int lastBottomSpanCount, int lastLeftSpanCount, int lastRightSpanCount) {
        this.hasHead = hasHead;
        this.headLeftSpanCount = headLeftSpanCount;
        this.headRightSpanCount = headRightSpanCount;
        this.spanSize = spanSize;
        this.leftSpanCount = leftSpanCount;
        this.topSpanCount = topSpanCount;
        this.rightSpanCount = rightSpanCount;
        this.bottomSpanCount = bottomSpanCount;
        this.lastTopSpanCount = lastTopSpanCount;
        this.lastLeftSpanCount = lastLeftSpanCount;
        this.lastRightSpanCount = lastRightSpanCount;
        this.lastBottomSpanCount = lastBottomSpanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (spanSize < 1) {
            spanSize = 1;
        }

        int position = parent.getChildLayoutPosition(view);

        Log.i("--- getItemOffsets " + " --- spanSize = " + spanSize + ", --- position = " + position);

        if (spanSize > 1) {
            //每行/列 多个元素：spanSize
            if (!isOneSide) {
            }

            outRect.left = leftSpanCount;
            outRect.top = topSpanCount;
            outRect.right = rightSpanCount;
            outRect.bottom = bottomSpanCount;

            if (position % spanSize == 0 && lastLeftSpanCount != 0) {//左边
                outRect.left = lastLeftSpanCount;
            }

            if (position % spanSize == spanSize - 1 && lastRightSpanCount != 0) {//右边
                outRect.right = lastRightSpanCount;
                outRect.left = 0;
            }

            if (position / spanSize == 0 && lastTopSpanCount != 0) {//第一行
                outRect.top = lastTopSpanCount;
            }

            if (position >= parent.getChildCount() - spanSize && lastBottomSpanCount != 0) {//最后一行
                outRect.bottom = lastBottomSpanCount;
            }


        } else {
            //每行/列 仅一个元素

            //单元素时候，如果 isOneSide = true,辣么 left = leftSpanCount,right = rightSpanCount,top = 0,bottom = bottomSpanCount;
            //          如果 isOneSide = false, 取各自的值
            if (isOneSide) {
                outRect.left = leftSpanCount;
                outRect.right = rightSpanCount;
                outRect.top = 0;
                outRect.bottom = bottomSpanCount;
            } else {
                outRect.left = leftSpanCount;
                outRect.top = topSpanCount;
                outRect.right = rightSpanCount;
                outRect.bottom = bottomSpanCount;
            }

            // 第一个元素
            if (hasHead) {
                if (position == 0 && hasHead) {//第一个元素 -- head
                    outRect.left = headLeftSpanCount;
                    outRect.right = headRightSpanCount;
                }
            } else {
                if (position == 0) {//第一个元素
                    outRect.top = lastTopSpanCount;
                }
            }

            // 最后一个元素
            if (position == parent.getAdapter().getItemCount() - 1) {//最后一行
                outRect.bottom = lastBottomSpanCount;
            }

        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childView = parent.getChildAt(i);

//            Log.i("position = " + i + ", left = " + childView.getLeft() + ", right = " + childView.getRight() + ", width = " + childView.getMeasuredWidth() + ", height = " + childView.getMeasuredHeight() + ", width = " + childView.getMeasuredWidth());
//        }
    }


    public SpacingItemDecoration setOneSide(boolean oneSide) {
        isOneSide = oneSide;
        return this;
    }
}
