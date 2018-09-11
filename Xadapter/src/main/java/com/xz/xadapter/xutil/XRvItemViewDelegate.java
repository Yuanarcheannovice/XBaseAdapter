package com.xz.xadapter.xutil;


/**
 * Created by zhy on 16/6/22.
 * 项目委托
 */
@Deprecated
public interface XRvItemViewDelegate<T>
{
    //获取项目视图布局ID
    int getItemViewLayoutId();

    //是用于视图类型的
    boolean isForViewType(T item, int position);

    //转换
    void convert(XRvViewHolder holder, T t, int position);

}
