package com.yazao.lib.weight.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：RecyclerView 分割线
 *
 * @author zhaishaoping
 * @data 05/03/2018 2:04 PM
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int marginLeftDividerLine;//分割线 - 左边距
    private int marginRightDividerLine;//分割线 - 右边距
    private int heightDividerLine;//分割线 - 高度
    private int colorDividerLine;//分割线 - 颜色
    private Context context;
    private int mOrientation;
    private Rect mBounds = new Rect();

    public DividerItemDecoration(Context context, int orientation) {
        this.context = context;
        mOrientation = orientation;
    }

    public DividerItemDecoration(Context context, int orientation, int marginLeftDividerLine, int marginRightDividerLine, int heightDividerLine, int colorDividerLine) {
        this.context = context;
        mOrientation = orientation;
        this.marginLeftDividerLine = marginLeftDividerLine;
        this.marginRightDividerLine = marginRightDividerLine;
        this.heightDividerLine = heightDividerLine;
        this.colorDividerLine = colorDividerLine;
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (mOrientation == VERTICAL) {
            outRect.set(marginLeftDividerLine, 0, marginRightDividerLine, heightDividerLine);
        } else {
            outRect.set(0, 0, heightDividerLine, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (parent.getLayoutManager() == null) {
            return;
        }

        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);

        }


    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {

    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);

            int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            int top = bottom - heightDividerLine;

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(colorDividerLine);
            int left = parent.getPaddingLeft() + marginLeftDividerLine;
            int right = parent.getWidth() - parent.getPaddingRight() - marginRightDividerLine;

//            int left;
//            int right;
//            if (parent.getClipToPadding()) {
//                left = parent.getPaddingLeft() + marginLeftDividerLine;
//                right = parent.getWidth() - parent.getPaddingRight() - marginRightDividerLine;
//                canvas.clipRect(left, parent.getPaddingTop(), right,
//                        parent.getHeight() - parent.getPaddingBottom());
//            } else {
//                left = parent.getPaddingLeft() + marginLeftDividerLine;
//                right = parent.getWidth();
//            }

            canvas.drawRect(new Rect(left, top, right, bottom), paint);
        }

        canvas.restore();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

}
