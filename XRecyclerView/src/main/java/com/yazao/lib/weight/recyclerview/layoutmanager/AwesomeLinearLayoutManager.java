package com.yazao.lib.weight.recyclerview.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 26/12/2017 5:09 PM
 */

public class AwesomeLinearLayoutManager extends LinearLayoutManager {

    public AwesomeLinearLayoutManager(Context context) {
        super(context);
    }

    public AwesomeLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public AwesomeLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //遇到这类错误java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder
        // https://www.jianshu.com/p/2eca433869e9
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
