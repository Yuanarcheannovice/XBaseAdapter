package com.xz.xadapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xz.xadapter.xutil.XRvViewHolder;
import com.xz.xadapter.xutil.XRvWrapperUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xz
 * 状态Adapter
 */
public abstract class XRvStatusAdapter<T> extends XRvPureDataAdapter<T> {

    private boolean isLoading = false;

    private boolean isEmpty = false;

    private boolean isError = false;

    private String mStatusTip = "";

    private static final int ITEM_STATUS = 9999;


    /**
     * 设置状态文字 颜色
     * resouce
     */
    @ColorRes
    protected int getStatusTextColor() {
        return 0;
    }

    /**
     * 设置状态文字大小
     * dp
     */
    protected int getStatusTextSize() {
        return 0;
    }

    /**
     * 初始化一个实例给状态adapter,避免mData为null，或者空
     *
     * @return 获取实例
     */
    protected abstract T initStatusLayout();

    /**
     * 显示正在加载中
     */
    public void showLoading(boolean loading) {
        isLoading = loading;
        if (loading) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
                mDatas.add(initStatusLayout());
            }
            mStatusTip = "加载中...";
            notifyDataSetChanged();
        }
    }

    /**
     * 显示数据为空
     */
    public void showEmpty(boolean empty) {
        isEmpty = empty;
        if (empty) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
                mDatas.add(initStatusLayout());
            }
            mStatusTip = "暂无数据...";
            notifyDataSetChanged();
        }
    }

    /**
     * 显示加载错误
     */
    public void showError(boolean error) {
        isError = error;
        if (error) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
                mDatas.add(initStatusLayout());
            }
            mStatusTip = "加载失败,请检查网络!";
            notifyDataSetChanged();
        }
    }

    @Override
    public void setDatas(List<T> datas) {
        if (datas != null && datas.size() > 0) {
            resetStatus();
        }
        super.setDatas(datas);
        if (mDatas.size() == 0) {
            showEmpty(true);
        }
    }

    @Override
    public void setDatas(List<T> datas, boolean isRefresh) {
        if (datas != null && datas.size() > 0) {
            resetStatus();
        }
        super.setDatas(datas, isRefresh);
        if (mDatas.size() == 0) {
            showEmpty(true);
        }
    }

    @Override
    public void addData(T data, boolean isRefresh) {
        resetStatus();
        super.addData(data, isRefresh);
    }

    @Override
    public void addDatas(List<T> data, boolean isRefresh) {
        resetStatus();
        super.addDatas(data, isRefresh);
    }

    private void resetStatus() {
        isEmpty = false;
        isLoading = false;
        isError = false;
    }

    @Override
    public final int getItemViewType(int position) {
        if (isLoading || isEmpty || isError) {
            return ITEM_STATUS;
        }
        return getItemViewTypeToStaus(position);
    }

    public int getItemViewTypeToStaus(int position) {
        return 0;
    }

    @Override
    public final int getItemLayout(int viewType) {
        if (viewType == ITEM_STATUS) {
            return R.layout.item_adapter_other;
        }
        return getItemLayoutToStatus(viewType);
    }

    /**
     * 设置layout
     */
    @LayoutRes
    public abstract int getItemLayoutToStatus(int viewType);


    @Override
    public final void onBindViewHolder(@NonNull XRvViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_STATUS) {
            holder.setText(R.id.ie_tv, mStatusTip);
            if (getStatusTextColor() != 0) {
                holder.setTextColor(R.id.ie_tv, getStatusTextColor());
            }
            if (getStatusTextSize() > 0) {
                holder.setTextSize(R.id.ie_tv, getStatusTextSize());
            }
        } else {
            onBindViewHolderToStatus(holder, position);
        }
    }


    /**
     * 显示数据,绑定控件
     */
    protected abstract void onBindViewHolderToStatus(@NonNull XRvViewHolder holder, int position);


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        XRvWrapperUtils.onAttachedToRecyclerView(recyclerView, new XRvWrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isEmpty || isError || isLoading) {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull XRvViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isEmpty || isError || isLoading) {
            XRvWrapperUtils.setFullSpan(holder);
        }
    }

}
