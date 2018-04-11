package com.xz.xadapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xz.xadapter.xutil.XRvViewHolder;


/**
 * 一个简单封装了，Rl点击事件的类，
 *
 * @author xz
 * @date 2016/8/15 0015
 * <p>
 * 纯净版的adapter(没有head，footer，多layout控制 数据控制，只有点击事件)
 */
public abstract class XRvPureAdapter extends RecyclerView.Adapter<XRvViewHolder> {

    /**
     * 点击事件
     */
    protected OnItemClickListener mOnItemClickListener;
    /**
     * 长按事件
     */
    protected OnItemLongClickListener mOnItemLongClickListener;


    /**
     * Tv端或者键盘手机使用
     * item 焦点 被选中
     */
    protected OnItemFocusableListener mOnItemFocusableListener;

    @Override
    public XRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final XRvViewHolder viewHolder = XRvViewHolder.createViewHolder(parent.getContext(), parent, getItemLayout(viewType));
        if (mOnItemClickListener != null) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemLongClickListener.onItemLongClick(v, viewHolder, position);
                }
            });
        }
        if (mOnItemFocusableListener != null) {
            //todo 焦点事件，会触发两次，一次是item离开的时候，一次是item被进入的时候，
            viewHolder.getConvertView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    mOnItemFocusableListener.onItemFocusable(v,hasFocus,viewHolder,viewHolder.getAdapterPosition());
                }
            });
        }
        return viewHolder;
    }


    @LayoutRes
    public abstract int getItemLayout(int viewType);

    /**
     * 点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);
    }


    /**
     * 长按接口
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * Tv端或者键盘手机使用
     * 焦点选中接口
     */
    public interface OnItemFocusableListener {
        /**
         * @param view     被选中的view
         * @param hasFocus 是否有焦点
         * @param holder   holder
         * @param position 下标
         */
        void onItemFocusable(View view, boolean hasFocus, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * 点击事件
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 焦点选中时间
     */
    public void setOnItemFocusableListener(OnItemFocusableListener onItemFocusableListener) {
        this.mOnItemFocusableListener = onItemFocusableListener;
    }
}
