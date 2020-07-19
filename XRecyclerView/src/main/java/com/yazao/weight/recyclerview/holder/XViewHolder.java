package com.yazao.weight.recyclerview.holder;

import android.content.Context;
import android.text.SpannableString;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 类描述：RecyclerView 通用的 ViewHolder
 *
 * @author zhaishaoping
 * @data 24/01/2018 1:28 PM
 */

public class XViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;//存储 item view 内部的 子view
    private View mItemView;
    private Context mContext;

    public XViewHolder(Context context, View itemView) {
        super(itemView);

        this.mContext = context;
        this.mItemView = itemView;
        mViews = new SparseArray<>();
    }


    public static XViewHolder getViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutId) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        XViewHolder holder = new XViewHolder(context, itemView);
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

    public XViewHolder setText(@IdRes int viewId, String textValue) {
        TextView textView = getView(viewId);
        textView.setText(textValue);
        return this;
    }

    public XViewHolder setText(@IdRes int viewId, SpannableString spannableString) {
        TextView textView = getView(viewId);
        textView.setText(spannableString);
        return this;
    }

    public XViewHolder setText(@IdRes int viewId, @StringRes int resId) {
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    public XViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public XViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public XViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public XViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public XViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public XViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


    public XViewHolder clear() {
        if (mViews != null && mViews.size() > 0) {
            mViews.clear();
        }
        return this;
    }
}
