package com.xz.xadapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.xz.xadapter.XRvPureAdapter;
import com.xz.xadapter.xutil.XRvViewHolder;


public abstract class XRvSingleItemAdapter<T> extends XRvPureAdapter {

    @LayoutRes
    private int mItemLayout;

    private T mData;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
        notifyDataSetChanged();
    }


    public XRvSingleItemAdapter(@LayoutRes int layout) {
        this.mItemLayout = layout;
    }

    public XRvSingleItemAdapter(@LayoutRes int layout, @NonNull T data) {
        this.mItemLayout = layout;
        this.mData = data;
    }

    @Override
    public int getItemLayout(int viewType) {
        return mItemLayout;
    }

    @Override
    public void onBindViewHolder(@NonNull XRvViewHolder xRvViewHolder, int i) {
        onBindView(xRvViewHolder, mData);
    }

    public abstract void onBindView(XRvViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return 1;
    }

}
