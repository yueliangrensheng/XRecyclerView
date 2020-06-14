package com.yazao.lib.weight.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 15/11/2017 2:57 PM
 */

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int spanSize = 1;// 每行/列 显示 Item 的数量
    private int leftSpanCount = 0;//Item 左间距 公倍数 的个数
    private int topSpanCount = 0;//Item 上间距 公倍数 的个数
    private int rightSpanCount = 0;//Item 右间距 公倍数 的个数
    private int bottomSpanCount = 0;//Item 下间距 公倍数 的个数
    private int lastTopSpanCount = 0;//Item 第一行元素 公倍数 的个数
    private int lastLeftSpanCount = 0;//Item 最左边元素 公倍数 的个数
    private int lastRightSpanCount = 0;//Item 最右边元素 公倍数 的个数
    private int lastBottomSpanCount = 0;//Item 最后一行元素 公倍数 的个数
    //头布局相关
    private boolean hasHead = false;//是否有头布局
    int headLeftSpanCount = 0;
    int headRightSpanCount = 0;

    /**
     * 每行/列 仅一个元素的 构造方法
     */
    public SimpleDividerItemDecoration(boolean hasHead, int headLeftSpanCount, int headRightSpanCount, int leftSpanCount, int topSpanCount, int rightSpanCount, int bottomSpanCount,
                                       int lastTopSpanCount, int lastBottomSpanCount) {
        this.hasHead = hasHead;
        this.headLeftSpanCount = headLeftSpanCount;
        this.headRightSpanCount = headRightSpanCount;
        this.leftSpanCount = leftSpanCount;
        this.topSpanCount = topSpanCount;
        this.rightSpanCount = rightSpanCount;
        this.bottomSpanCount = bottomSpanCount;
        this.lastTopSpanCount = lastTopSpanCount;
        this.lastBottomSpanCount = lastBottomSpanCount;

    }

    /**
     * 每行/列 多个元素的 构造方法
     */
    public SimpleDividerItemDecoration(boolean hasHead, int spanSize, int leftSpanCount, int topSpanCount,
                                       int rightSpanCount, int bottomSpanCount, int lastTopSpanCount,
                                       int lastBottomSpanCount, int lastLeftSpanCount, int lastRightSpanCount) {
        this.hasHead = hasHead;
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
        outRect.top = topSpanCount;
        outRect.bottom = bottomSpanCount;
        outRect.left = leftSpanCount;
        outRect.right = rightSpanCount;

        int position = parent.getChildLayoutPosition(view);

        if (spanSize > 1) {
            //每行/列 多个元素：spanSize

            if (position % spanSize == 0  && lastLeftSpanCount != 0 ) {//左边
                outRect.left = lastLeftSpanCount;
            }

            if (position % spanSize == spanSize - 1 && lastRightSpanCount != 0 ) {//右边
                outRect.right = lastRightSpanCount;
                outRect.left = 0 ;
            }

            if (position / spanSize == 0  && lastTopSpanCount != 0 ) {//第一行
                outRect.top = lastTopSpanCount;
            }

            if (position >= parent.getChildCount() - spanSize  && lastBottomSpanCount != 0 ) {//最后一行
                outRect.bottom = lastBottomSpanCount;
            }


        } else {
            //每行/列 仅一个元素

            if (position == 0 /* && lastTopSpanCount != 0 */) {//第一个元素
                outRect.top = lastTopSpanCount;
            }

            if (position == 0 && hasHead) {
                outRect.left = headLeftSpanCount;
                outRect.right = headRightSpanCount;
            }

            if (position == parent.getChildCount() - 1 /* && lastBottomSpanCount != 0 */) {//最后一行
                outRect.bottom = lastBottomSpanCount;
            }

        }

//        Log.i("position = " + position + ", width = " + view.getMeasuredWidth() + ", height = " + view.getMeasuredHeight()
//                + ", left = " + outRect.left + ", top = " + outRect.top + ", right = " + outRect.right + ", bottom = " + outRect.bottom
//        );
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);

//            Log.i("position = " + i + ", left = " + childView.getLeft() + ", right = " + childView.getRight() + ", width = " + childView.getMeasuredWidth() + ", height = " + childView.getMeasuredHeight() + ", width = " + childView.getMeasuredWidth());
        }
    }
}
