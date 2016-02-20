package com.msx7.lib.widget.list.impl;

import android.support.v4.widget.SwipeRefreshLayout;

import com.msx7.lib.widget.list.fresh.IFresh;
import com.msx7.lib.widget.list.fresh.IFreshListener;


/**
 * Created by xiaowei on 2015/12/22.
 */
public class SwipRefresh implements IFresh {

    SwipeRefreshLayout refreshLayout;
    IFreshListener listener;

    public SwipRefresh(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) listener.fresh();
            }
        });
    }

    /**
     * 强迫执行下拉刷新 包括触发监听
     */
    @Override
    public void forceToFresh() {
        refreshLayout.setRefreshing(true);
        if (listener != null) listener.fresh();
    }

    /**
     * 结束下拉刷新 仅仅指UI表现
     */
    @Override
    public void finishFresh() {
        refreshLayout.setRefreshing(false);
    }

    /**
     * 监听下拉刷新
     *
     * @param listener
     */
    @Override
    public void setFreshListener(IFreshListener listener) {
        this.listener = listener;
    }
}
