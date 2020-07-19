package com.yazao.weight.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：RecyclerView 悬浮Item
 *
 * @author zhaishaoping
 * @data 08/02/2018 9:10 PM
 */

public class SuspensionItemDecoration extends RecyclerView.ItemDecoration {


    /**
     * 所有的ItemDecorations绘制都是顺序执行，即：onDraw() < Item View < onDrawOver()
     * <p>
     * onDraw()可以用来绘制divider，但在此之前必须在getItemOffsets设置了padding范围，否则onDraw()的绘制是在ItemView的下面导致不可见；
     * <p>
     * onDrawOver()是绘制在最上层，所以可以用来绘制悬浮框等
     */


    private Context mContext;
    private int suspensionHeight;
    private boolean hasSuspension = false;//是否显示悬浮Item
    private OnItemDecorationActionListener mDecorationActionListener;

    /**
     * @param context
     * @param suspensionHeight          悬浮Item的高度
     * @param mDecorationActionListener 悬浮Item 数据处理接口
     */
    public SuspensionItemDecoration(Context context, int suspensionHeight, OnItemDecorationActionListener mDecorationActionListener) {
        this.mContext = context;
        this.suspensionHeight = suspensionHeight;
        this.mDecorationActionListener = mDecorationActionListener;
    }

    /**
     * @param context
     * @param suspensionHeight          悬浮Item的高度
     * @param hasSuspension             悬浮Item是否展示
     * @param mDecorationActionListener 悬浮Item 数据处理接口
     */
    public SuspensionItemDecoration(Context context, int suspensionHeight, boolean hasSuspension, OnItemDecorationActionListener mDecorationActionListener) {
        this.mContext = context;
        this.suspensionHeight = suspensionHeight;
        this.hasSuspension = hasSuspension;
        this.mDecorationActionListener = mDecorationActionListener;
    }

    //用来绘制每个 ItemView 的边距
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        //RecyclerView 在重置时 position 可能为 -1
        if (position > -1) {
            if (position == 0) {
                //第一个条目  需要悬浮Title
                outRect.set(0, suspensionHeight, 0, 0);
            } else if (position > 0) {
                //其他Item ：需要判断 position  与 position -1 是否处于同一类。如果不是同一类，需要悬浮Title
                if (mDecorationActionListener != null && !mDecorationActionListener.hasSameCategory(position)) {
                    outRect.set(0, suspensionHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }

            }
        }

    }


    //绘制ItemView 的分类Title
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (position == 0) {
                //第一个Item 需要绘制 分类的Title
                drawCategoryView(c, parent, child, position);
            } else {
                //和 上一条数据 分类不同，需要 分类的Title
                if (mDecorationActionListener != null && !mDecorationActionListener.hasSameCategory(position)) {
                    drawCategoryView(c, parent, child, position);
                }
            }
        }

    }

    /**
     * 绘制 分类 Title Item
     * <p>
     * =============================== 示例 start =============================
     * <p>
     * mDecorationActionListener.drawCategoryView（）： 接口方法示例 实现：
     * <p>
     * //绘制年份 item
     * //1.绘制背景
     * Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
     * paint.setColor(getResources().getColor(R.color.wy_color_98A2AF_alpha_12));
     * c.drawRect(new Rect(left, top, right, bottom), paint);
     * <p>
     * //2.绘制文本
     * String text = mDatas.get(position).getTime().substring(0, 4);
     * Rect bounds = new Rect();
     * paint.setColor(getResources().getColor(R.color.wy_color_9AA2AE));
     * paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.wy_28px_x));
     * paint.getTextBounds(text, 0, text.length(), bounds);
     * <p>
     * float x = 0;
     * float y = 0;
     * x = left + getResources().getDimensionPixelOffset(R.dimen.wy_40px_x);
     * y = bottom - (suspensionHeight / 2 - bounds.height() / 2);
     * c.drawText(text, x, y, paint);
     * <p>
     * =============================== 示例 end =============================
     *
     * @param canvas
     * @param parent
     * @param child
     * @param position
     */
    private void drawCategoryView(Canvas canvas, RecyclerView parent, View child, int position) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        int top = child.getTop() - params.topMargin - suspensionHeight;
        int bottom = child.getTop() - params.topMargin;

        if (mDecorationActionListener != null) {
            mDecorationActionListener.drawCategoryView(canvas, parent, position, params, suspensionHeight, left, top, right, bottom);
        }

    }


    //用来绘制最上面的悬浮框
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (hasSuspension) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int position = layoutManager.findFirstVisibleItemPosition();
            View child = parent.findViewHolderForLayoutPosition(position).itemView;

            if (mDecorationActionListener != null) {
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = parent.getPaddingTop();
                int bottom = parent.getPaddingTop() + suspensionHeight;

                mDecorationActionListener.drawCategoryView(c, parent, position, params, suspensionHeight, left, top, right, bottom);

            }

        }

    }


}
