package com.xz.util;

import com.xz.util.adapter.R;
import com.xz.util.adapter.RvPureDataAdapter;
import com.xz.util.adapter.util.RvViewHolder;

/**
 * Created by xz on 2017/7/27 0027.
 * 纯净版的使用
 * <p>
 * 暂时只能用于 linear
 */

public class Adapter extends RvPureDataAdapter<String> {
    @Override
    public int getItemLayout(int viewType) {
        return R.layout.item_str;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        holder.setText(R.id.tv, mDatas.get(position));
    }
}
