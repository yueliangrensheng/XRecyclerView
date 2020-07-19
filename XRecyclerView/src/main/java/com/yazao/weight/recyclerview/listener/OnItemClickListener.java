package com.yazao.weight.recyclerview.listener;

import android.view.View;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 24/01/2018 8:36 PM
 */

public interface OnItemClickListener {
    /**
     * @param v
     * @param position
     * @param bean     item对应的数据bean
     */
    void onItemClick(View v, int position, Object bean);
}
