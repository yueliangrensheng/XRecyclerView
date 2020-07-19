package com.yazao.weight.recyclerview.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：RecyclerView 分割线
 *
 * <pre>
 *     在系统 androidx.recyclerview.widget.DividerItemDecoration 基础上修改
 * </pre>
 *
 * @author zhaishaoping
 * @data 05/03/2018 2:04 PM
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int marginLeftDividerLine = 0;//分割线 - 左边距\顶部边距（VERTICAL \ HORIZONTAL）
    private int marginRightDividerLine = 0;//分割线 - 右边距\底部边距（VERTICAL \ HORIZONTAL）
    private int heightDividerLine = 10;//分割线 - 高度\宽度（VERTICAL \ HORIZONTAL）
    private @ColorRes
    int colorDividerLine = android.R.color.darker_gray;//分割线 - 颜色
    private Drawable mDivider;//分割线 - drawable

    private Context context;
    private int mOrientation = VERTICAL;
    private Rect mBounds = new Rect();

    private static final String TAG = "DividerItem";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 通过系统属性'@android:attr/listDivider'来设置分割线
     *
     * @param context
     * @param orientation
     */
    public DividerItemDecoration(Context context, int orientation) {
        this.context = context;
        mOrientation = orientation;
        initDividerDrawable();
        setOrientation(orientation);
    }

    private void initDividerDrawable() {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation
     * @param marginLeftDividerLine  左边距\上边距 （VERTICAL \ HORIZONTAL）
     * @param marginRightDividerLine 右边距\下边距（VERTICAL \ HORIZONTAL）
     * @param heightDividerLine      分割线高度
     * @param colorDividerLine       分割线颜色
     */
    public DividerItemDecoration(Context context, int orientation, int marginLeftDividerLine, int marginRightDividerLine, int heightDividerLine, @ColorInt int colorDividerLine) {
        this.context = context;
        mOrientation = orientation;
        this.marginLeftDividerLine = marginLeftDividerLine;
        this.marginRightDividerLine = marginRightDividerLine;
        this.heightDividerLine = heightDividerLine;
        this.colorDividerLine = colorDividerLine;
        setOrientation(orientation);
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

    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    public Drawable getDrawable() {
        return mDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

//        if (mDivider == null) {
//            outRect.set(0, 0, 0, 0);
//            return;
//        }

        // 这里根据 heightDividerLine 是否为0 来判断 采用 系统属性的mDivider 还是 通过Paint绘制 divider
        if (mOrientation == VERTICAL) {
//            outRect.set(marginLeftDividerLine, 0, marginRightDividerLine, heightDividerLine == 0 ? mDivider.getIntrinsicHeight() : heightDividerLine);
            outRect.set(0, 0, 0, heightDividerLine == 0 ? mDivider.getIntrinsicHeight() : heightDividerLine);
        } else {
//            outRect.set(0, marginLeftDividerLine, heightDividerLine == 0 ? mDivider.getIntrinsicWidth() : heightDividerLine, marginRightDividerLine);
            outRect.set(0, 0, heightDividerLine == 0 ? mDivider.getIntrinsicWidth() : heightDividerLine, 0);
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
        canvas.save();
        int top;
        int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(this.context.getResources().getColor(colorDividerLine));

        top += marginLeftDividerLine;
        bottom -= marginRightDividerLine;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            if (i == childCount - 1) {//最后一行不绘制
                return;
            }
            View child = parent.getChildAt(i);
            //Returns the bounds of the view including its decoration and margins.
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - (heightDividerLine == 0 ? mDivider.getIntrinsicWidth() : heightDividerLine);

            canvas.drawRect(new Rect(left, top, right, bottom), paint);
        }
        canvas.restore();
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();

        int left;
        int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) { //getClipToPadding(): 默认值是true
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(this.context.getResources().getColor(colorDividerLine));

        left += marginLeftDividerLine;
        right -= marginRightDividerLine;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == childCount - 1) {//最后一行不绘制
                return;
            }
            View child = parent.getChildAt(i);
            //Returns the bounds of the view including its decoration and margins.
            parent.getDecoratedBoundsWithMargins(child, mBounds);

            int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            int top = bottom - (heightDividerLine == 0 ? mDivider.getIntrinsicHeight() : heightDividerLine);

            canvas.drawRect(new Rect(left, top, right, bottom), paint);
        }

        canvas.restore();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

}
