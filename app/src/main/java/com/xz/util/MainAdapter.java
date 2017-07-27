package com.xz.util;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.xz.util.adapter.R;
import com.xz.util.adapter.RvCommonAdapter;
import com.xz.util.adapter.util.RvViewHolder;

/**
 * Created by xz on 2017/7/27 0027.
 */

public class MainAdapter extends RvCommonAdapter<String> {


    public MainAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void convert(RvViewHolder holder, String s, int position) {
        holder.setText(R.id.tv, mDatas.get(position));
    }
}
