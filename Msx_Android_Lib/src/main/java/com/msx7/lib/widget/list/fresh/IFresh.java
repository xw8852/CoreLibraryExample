package com.msx7.lib.widget.list.fresh;

/**
 * Created by xiaowei on 2015/12/22.
 */
public interface IFresh {

    /**
     * 强迫执行下拉刷新 包括触发监听
     */
    public void forceToFresh();

    /**
     * 结束下拉刷新 仅仅指UI表现
     */
    public void finishFresh();

    /**
     * 监听下拉刷新
     * @param listener
     */
    public void setFreshListener(IFreshListener listener);
}
