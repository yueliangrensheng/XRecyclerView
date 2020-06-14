package com.yazao.lib.weight.recyclerview.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：RecyclerView 通用的 ViewHolder
 *
 * @author zhaishaoping
 * @data 24/01/2018 1:28 PM
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;//存储 item view 内部的 子view
    private View mItemView;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);

        this.mContext = context;
        this.mItemView = itemView;
        mViews = new SparseArray<>();
    }


    public static ViewHolder getViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutId) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }


    /**
     * 方法描述：通过 viewId 来获取控件View
     *
     * @return
     * @author zhaishaoping
     * @time 24/01/2018 5:14 PM
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public String getText(@IdRes int viewId) {
        TextView textView = getView(viewId);
        return textView.getText().toString();
    }

    public ViewHolder setText(@IdRes int viewId, String textValue) {
        TextView textView = getView(viewId);
        textView.setText(textValue);
        return this;
    }

    public ViewHolder setText(@IdRes int viewId, SpannableString spannableString) {
        TextView textView = getView(viewId);
        textView.setText(spannableString);
        return this;
    }

    public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }
}
