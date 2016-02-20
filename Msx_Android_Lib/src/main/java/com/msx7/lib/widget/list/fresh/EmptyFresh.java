package com.msx7.lib.widget.list.fresh;

/**
 * Created by xiaowei on 2016/1/13.
 */
public class EmptyFresh implements IFresh{
    /**
     * 结束下拉刷新 仅仅指UI表现
     */
    @Override
    public void finishFresh() {

    }

    /**
     * 强迫执行下拉刷新 包括触发监听
     */
    @Override
    public void forceToFresh() {

    }

    /**
     * 监听下拉刷新
     *
     * @param listener
     */
    @Override
    public void setFreshListener(IFreshListener listener) {

    }
}
