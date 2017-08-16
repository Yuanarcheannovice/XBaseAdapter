package com.xz.xadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.xz.xadapter.xutil.XRvItemViewDelegate;
import com.xz.xadapter.xutil.XRvViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * update by xz on 17/7/25.
 */
public abstract class XRvCommonAdapter<T> extends XRvMultiItemTypeAdapter<T> {

    /**
     * 不携带初始数据
     * @param layoutId Layout布局
     */
    public XRvCommonAdapter(final Context context, @LayoutRes final int layoutId) {
        super(context, new ArrayList<T>());

        addItemViewDelegate(new XRvItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(XRvViewHolder holder, T t, int position) {
                XRvCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    /**
     * 携带初始数据
     * @param layoutId Layout布局
     * @param datas 数据
     */
    public XRvCommonAdapter(final Context context, @LayoutRes final int layoutId, List<T> datas) {
        super(context, datas);

        addItemViewDelegate(new XRvItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(XRvViewHolder holder, T t, int position) {
                XRvCommonAdapter.this.convert(holder, t, position);
            }
        });
    }


    protected abstract void convert(XRvViewHolder holder, T t, int position);


}
