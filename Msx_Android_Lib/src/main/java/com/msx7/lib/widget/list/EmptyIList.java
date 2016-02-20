package com.msx7.lib.widget.list;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by xiaowei on 2016/1/13.
 */
public class EmptyIList<T> implements IList<T> {
    /**
     * 绑定数据
     *
     * @param t
     * @param item
     * @param inflater
     * @param position
     */
    @Override
    public View BinderData(T t, View item, LayoutInflater inflater, int position) {
        return null;
    }

    /**
     * 当没有数据时，显示的默认页
     *
     * @return
     */
    @Override
    public View getDefaultView() {
        return null;
    }

    @Override
    public void fresh() {

    }

    /**
     * 加载更多页
     *
     * @param page
     */
    @Override
    public void loadPage(int page) {

    }
}
