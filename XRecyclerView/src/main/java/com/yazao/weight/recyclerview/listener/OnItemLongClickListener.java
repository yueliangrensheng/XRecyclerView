package com.yazao.weight.recyclerview.listener;

import android.view.View;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 24/01/2018 8:37 PM
 */

public interface OnItemLongClickListener {
    /**
     * @param v
     * @param position
     * @param bean     item对应的数据bean
     * @return
     */
    boolean onItemLongClick(View v, int position, Object bean);
}
