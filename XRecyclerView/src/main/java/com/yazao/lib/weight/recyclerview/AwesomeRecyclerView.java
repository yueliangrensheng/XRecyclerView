package com.yazao.lib.weight.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 14/03/2018 6:27 PM
 */

public class AwesomeRecyclerView extends RecyclerView {

    float mLastX;
    float mLastY;

    public AwesomeRecyclerView(@NonNull Context context) {
        super(context);
    }

    public AwesomeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getRawX();
                float y = ev.getRawY();

                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
