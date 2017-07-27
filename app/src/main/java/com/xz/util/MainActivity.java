package com.xz.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.xz.util.adapter.R;
import com.xz.util.adapter.RvDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private int mIndex = 1;
    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        findViewById(R.id.m_bt1).setOnClickListener(this);
        findViewById(R.id.m_bt2).setOnClickListener(this);
        findViewById(R.id.m_bt3).setOnClickListener(this);
        //

        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    list.add(mIndex + "-" + i + (i * 2) + (i + 3));
                    mIndex++;
                }
                mMainAdapter.addData(list, true);
                mSmartRefreshLayout.finishLoadmore(500);
            }
        });

        //
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mMainAdapter = new MainAdapter(this, R.layout.item_str);

        TextView head = new TextView(this);
        head.setText("header1");
        mMainAdapter.addHeaderView(head);

        //
        TextView foot = new TextView(this);
        foot.setText("footer1");
        mMainAdapter.addFootView(foot);
        //

        mMainAdapter.setOnItemClickListener(new RvDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtil.show("点击了" + "string:" + mMainAdapter.getItem(position) + "position" + position);
            }
        });

        mMainAdapter.setOnItemLongClickListener(new RvDataAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtil.show("长按了" + "string:" + mMainAdapter.getItem(position) + "position" + position);
                return false;
            }
        });

        mRecyclerView.setAdapter(mMainAdapter);
        initData();
    }


    private void initData() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 100; i++) {
                    list.add(mIndex + "-" + i + (i * 2) + (i + 3));
                    mIndex++;
                }
                mMainAdapter.setData(list, true);
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_bt1:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.m_bt2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.m_bt3:
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        mRecyclerView.setAdapter(mMainAdapter);
    }
}
