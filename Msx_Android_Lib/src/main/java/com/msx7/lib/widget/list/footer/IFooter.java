package com.msx7.lib.widget.list.footer;

import android.widget.ListView;

import com.msx7.lib.widget.list.common.Page;


/**
 * Created by xiaowei on 2015/12/18.
 */
public interface IFooter {

    IFooter setListener(IMorePage morePage);

    IFooter binderFooterView(ListView listView);

    IFooter update(Page page);
    /**
     * listview 滑动到最底部的时候，自动加载下一页
     * @param auto
     * @return
     */
    IFooter startAuto(boolean auto);
}
