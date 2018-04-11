package com.xz.xadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xz.xadapter.xutil.XRvItemViewDelegate;
import com.xz.xadapter.xutil.XRvItemViewDelegateManager;
import com.xz.xadapter.xutil.XRvViewHolder;
import com.xz.xadapter.xutil.XRvWrapperUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 16/4/9.
 * update by xz on17/7/25
 * <p>
 * 此Adapter 拥有 添加Header，Footer，点击，长按，实现不同layout功能;
 */
public class XRvMultiItemTypeAdapter<T> extends XRvDataAdapter<T> {
    protected Context mContext;
    protected XRvItemViewDelegateManager mItemViewDelegateManager;


    /**
     * 直接使用集合
     *
     * @param context
     * @param datas
     */
    public XRvMultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = datas;
        mItemViewDelegateManager = new XRvItemViewDelegateManager();
    }


    /**
     * 根据position来是实现不同layout
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - mDatas.size());
        } else {
            position = position - getHeadersCount();
            //判断是否有不同View的操作，如果有，则返回
            if (!useItemViewDelegateManager()) {
                return super.getItemViewType(position);
            }
            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
        }
    }


    /**
     * 创建ViewHolder
     */
    @Override
    public XRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Header 和 Hooter 创建
        if (mHeaderViews.get(viewType) != null) {
            XRvViewHolder holder = XRvViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;

        } else if (mFootViews.get(viewType) != null) {
            XRvViewHolder holder = XRvViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        } else {
            //一般layout 创建
            XRvItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
            int layoutId = itemViewDelegate.getItemViewLayoutId();
            XRvViewHolder holder = XRvViewHolder.createViewHolder(mContext, parent, layoutId);
            onViewHolderCreated(holder, holder.getConvertView());
            setListener(parent, holder, viewType);
            return holder;
        }
    }

    /**
     * 创建一个item的view
     */
    protected void onViewHolderCreated(XRvViewHolder holder, View itemView) {

    }


    public void convert(XRvViewHolder holder, T t) {
        //这里需要减去头部的值，
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition() - getHeadersCount());
    }

    /**
     * 判断当前viewtype是否是header 和 footer
     *
     * @param viewType
     * @return
     */
    protected boolean isEnabled(int viewType) {
        if (viewType == BASE_ITEM_TYPE_HEADER || viewType == BASE_ITEM_TYPE_FOOTER) {
            return false;
        } else {
            return true;
        }
    }


    private void setListener(final ViewGroup parent, final XRvViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }
        if (mOnItemClickListener != null) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition() - getHeadersCount();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = viewHolder.getAdapterPosition() - getHeadersCount();
                    return mOnItemLongClickListener.onItemLongClick(v, viewHolder, position);
                }
            });
        }
        if (mOnItemFocusableListener != null) {
            viewHolder.getConvertView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    mOnItemFocusableListener.onItemFocusable(v,hasFocus,viewHolder,viewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(XRvViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        //如果当前下标没有对应Header，Footer,position则应该减去header的数量
        position = position - getHeadersCount();
        convert(holder, mDatas.get(position));
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        XRvWrapperUtils.onAttachedToRecyclerView(recyclerView, new XRvWrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    /**
     * 当Item进入这个页面的时候调用
     */
    @Override
    public void onViewAttachedToWindow(XRvViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            XRvWrapperUtils.setFullSpan(holder);
        }
    }


    /**
     * 添加一个item
     *
     * @param itemViewDelegate item视图
     */
    public XRvMultiItemTypeAdapter addItemViewDelegate(XRvItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 添加一个item
     *
     * @param viewType         view类型
     * @param itemViewDelegate item视图
     */
    public XRvMultiItemTypeAdapter addItemViewDelegate(int viewType, XRvItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    /**
     * 判断是否有 特殊ItemLayout
     */
    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }


    /**
     * 判断当前item下标是否小于Header数量
     * 如果小于，则可以对header处理
     *
     * @param position 当前需要显示的item下标
     */
    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    /**
     * 判断是否当前下标是否大于等于 HeaderView数量和mDatas数量
     * 如果大于等于，则可以显示footerView
     *
     * @param position 当前需要显示的item下标
     */
    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + mDatas.size();
    }

}
