package com.xz.xadapter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xz
 * @date 2017/7/27 0027
 * 增加了纯净Adapter，的数据操作
 * <p>
 * 因为有些时候可是使用的map集合，所以改用继承来写数据操作，不合并到 RvPureAdapte中
 */

public abstract class XRvPureDataAdapter<T> extends XRvPureAdapter {
    /**
     * 一般数据
     */
    protected List<T> mDatas;

    public List<T> getDatas() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    /**
     * 一般使用 不带刷新
     */
    public void setData(@NonNull T data) {
        List<T> lists = new ArrayList<>();
        lists.add(data);
        setDatas(lists, true);
    }

    /**
     * 一般使用 不带刷新
     */
    public void setDatas(@NonNull List<T> datas) {
        setDatas(datas, false);
    }


    /**
     * 数据替换 ，带刷新
     *
     * @param datas     数据
     * @param isRefresh 是否刷新
     */
    public void setDatas(@NonNull List<T> datas, boolean isRefresh) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas.clear();
        }
        this.mDatas.addAll(datas);
        if (isRefresh) {
            notifyDataSetChanged();
        }
    }

    /**
     * 增加一个data
     *
     * @param data      数据
     * @param isRefresh 是否刷新
     */
    public void addData(@NonNull T data, boolean isRefresh) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
            this.mDatas.add(data);
            if (isRefresh) {
                notifyDataSetChanged();
            }
        } else {
            this.mDatas.add(data);
            if (isRefresh) {
                notifyItemRangeInserted(mDatas.size() - 1, 1);
            }
        }
    }

    /**
     * 数据集合增加数据 带刷新
     *
     * @param data      数据
     * @param isRefresh 是否刷新
     */
    public void addDatas(@NonNull List<T> data, boolean isRefresh) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
            this.mDatas.addAll(data);
            if (isRefresh) {
                notifyDataSetChanged();
            }
        } else {
            this.mDatas.addAll(data);
            if (isRefresh) {
                notifyItemRangeInserted(mDatas.size() - 1, data.size());
            }
        }
    }


    /**
     * 移除data
     *
     * @param data 对象
     * @return 是否移除成功
     */
    public boolean removeData(@NonNull T data) {
        return this.mDatas != null && this.mDatas.remove(data);
    }

    /**
     * 移除所有数据
     */
    public void removeDataAll() {
        if (mDatas != null) {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }


    /**
     * 移除data
     *
     * @param index 下标
     * @return
     */
    public boolean removeData(int index) {
        if (mDatas != null && index >= 0) {
            if (index < mDatas.size()) {
                mDatas.remove(index);
                notifyItemRangeRemoved(index, 1);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 得到单个item
     *
     * @param position item的下标
     * @return 集合中的某个对象
     */
    public T getItem(int position) {
        if (mDatas != null && !mDatas.isEmpty()) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }
}
