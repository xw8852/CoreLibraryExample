package com.msx7.lib.widget.list;


import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.msx7.lib.widget.BaseAdapter;
import com.msx7.lib.widget.list.common.Page;
import com.msx7.lib.widget.list.footer.Footer;
import com.msx7.lib.widget.list.fresh.IFresh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowei on 2015/12/21.<br/>
 * 1、下拉刷新必须是实现IFresh接口，实现案例{@link com.msx7.lib.widget.list.impl.SwipRefresh}<br/>
 * 2、唯一构造函数，得到此类示例。 {@link #BinderListView(ListView, IFresh)}<br/>
 * 3、{@link #setIList(IList)} 设置监听<br/>
 * 4、{@link #binderFooterView(Page)} 添加加载更多，<br/>
 * page中几个重要的参数{@link Page#start} 起始页  {@link Page#avg} 每页加载的数量，<br/>
 * {@link Page#stop} 默认是-1，{@link Page#cur}和{@link Page#total} 这2个参数是自动计算的
 * <br/>
 * 5、通过{@link #getFooter()}·{@link Footer#update(Page)} 来更新footer的状态。
 */
public class BinderListView<T> {
    ListView mListView;
    IList<T> mListListener;
    BinderAdapter mAdapter;
    Footer footer;
    IFresh fresh;
    DataSetObserver adapterObserver = new DataSetObserver() {

        @Override
        public void onChanged () {
            super.onChanged();
            updateStatus();
        }


        @Override
        public void onInvalidated () {
            super.onInvalidated();
            updateStatus();
        }

    };

    public BinderListView (ListView listView, IFresh fresh) {
        this.mListView = listView;
        this.fresh = fresh;
        init();
    }


    void init () {
        mAdapter = new BinderAdapter(mListView.getContext(), new ArrayList<T>());
        mAdapter.registerDataSetObserver(adapterObserver);
        mListView.setAdapter(mAdapter);
        mListView.addOnLayoutChangeListener(listOnLayoutChangeListener);
    }


    View.OnLayoutChangeListener listOnLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange (View v, int left, int top, int right, int bottom,
                int oldLeft, int oldTop, int oldRight, int oldBottom) {

            if (mListView.getHeaderViewsCount() > 0 && headView != null) {
                ViewGroup.LayoutParams params = headView.getLayoutParams();
                if (params != null && params.height == (bottom - top) - 200) return;
                params = new AbsListView.LayoutParams(
                        right - left,
                        bottom - top - 200);
                headView.setLayoutParams(params);
            }
        }
    };

    public BinderAdapter getAdapter () {
        return mAdapter;
    }

    public void notifyDataSetChanged () {
        mAdapter.notifyDataSetChanged();
    }

    View headView;

    void updateStatus () {
        fresh.finishFresh();
        if (footer != null) {
            Page page = footer.getPage();
            page.total = mAdapter.getCount();
            if (page.total <= 0) {
                page.cur = page.start;
            }
            else{
                page.cur = page.total / page.avg;
            }
            if (page.total % page.avg > 0) {
                page.cur += 1;
            }
            //修正起始页
            page.cur = page.cur - (1 - page.start);
            footer.update(page);
        }
        if (mAdapter.getCount() == 0 && getIList() != null) {
            if (getIList().getDefaultView() == headView && headView != null) return;
            if (mListView.getHeaderViewsCount() > 0 && headView != null)
                mListView.removeHeaderView(headView);
            headView = getIList().getDefaultView();
            if (headView != null) {
//                int height = mListView.getMeasuredHeight();
//                AbsListView.LayoutParams params = new AbsListView.LayoutParams(
//                        AbsListView.LayoutParams.MATCH_PARENT,
//                        height);
//                headView.setLayoutParams(params);
                mListView.addHeaderView(headView, null, false);
            }
        } else
            if (headView != null) {
                mListView.removeHeaderView(headView);
                headView = null;
            }
    }

    /**
     * 设置ListView的数据源
     *
     * @param data
     */
    public void setData (List<T> data) {
        mAdapter.change(data);
    }

    /**
     * 给ListView 增加数据
     *
     * @param data
     */
    public void addData (List<T> data) {
        mAdapter.addMore(data);
    }

    public void addData (T t) {
        mAdapter.add(t);
    }

    public void addData (int index,T t) {
        mAdapter.add(index,t);
    }
    /**
     * 是否自动 触发加载更多
     *
     * @param auto {@link Footer#startAuto(boolean)}
     * @return
     */
    public BinderListView<T> startAuto (boolean auto) {
        footer.startAuto(auto);
        return this;
    }

    public void updateFinalPage(int page){
        getFooter().updateFinalPage(page);
    }

    public Footer getFooter () {
        return footer;
    }

    /**
     * @param page 包含page的初始信息{@link Page}
     */
    public BinderListView<T> binderFooterView (Page page) {
        if (getIList() == null)
            throw new IllegalAccessError("你必须先调用 setIList(IList<T> ilist) 函数");
        footer = new Footer().binderFooterView(mListView).setListener(getIList()).update(page);
        return this;
    }

    /**
     * 下拉刷新，或者 加载更多页时候，请求失败，调用此方法更新ListView的状态
     */
    public void loadFail () {
        fresh.finishFresh();
        updateStatus();
    }

    public IList<T> getIList () {
        return mListListener;
    }

    /**
     * @param ilist 必须设置的监听，包含下拉刷新，加载更多，给ListView绑定Item
     * @return
     */
    public BinderListView<T> setIList (IList<T> ilist) {
        this.mListListener = ilist;
        fresh.setFreshListener(ilist);
        return this;
    }

    /**
     * 强制执行刷新动作
     */
    public void forcefresh () {
        fresh.forceToFresh();
    }


    public class BinderAdapter extends BaseAdapter<T> {
        public BinderAdapter (Context context, List<T> data) {
            super(context, data);
        }

        /**
         * @param position
         * @param convertView
         * @param inflater    @return View
         * @throws
         * @Title: createView
         * @Description: 创建自定义的listView item
         */
        @Override
        public View createView (int position, View convertView, LayoutInflater inflater) {
            return getIList().BinderData(getItem(position), convertView, inflater, position);
        }
    }

}
