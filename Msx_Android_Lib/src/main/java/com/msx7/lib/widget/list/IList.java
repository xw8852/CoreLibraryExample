package com.msx7.lib.widget.list;

import android.view.LayoutInflater;
import android.view.View;

import com.msx7.lib.widget.list.footer.IMorePage;
import com.msx7.lib.widget.list.fresh.IFreshListener;


/**
 * Created by xiaowei on 2015/12/18.
 */
public interface IList<T> extends IMorePage,IFreshListener {

    /**
     * 绑定数据
     * @param t
     * @param item
     */
    View BinderData(T t, View item, LayoutInflater inflater, int position);

    /**
     * 当没有数据时，显示的默认页
     * @return
     */
    View getDefaultView();
}
