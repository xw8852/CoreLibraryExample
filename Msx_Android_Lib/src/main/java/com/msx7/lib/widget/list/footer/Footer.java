package com.msx7.lib.widget.list.footer;

import android.com.msx7.josn.msx_android_lib.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.msx7.lib.widget.list.common.Page;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiaowei on 2015/12/21.
 */
public class Footer implements IFooter {

    public static final int MODE_HIDE = 0X001;
    public static final int MODE_SHOW = 0X002;
    public static final int MODE_LOADING = 0X003;
    public static final int MODE_FINISH = 0X004;

    ViewGroup view;
    Page page;
    int mode;
    IMorePage listener;
    boolean auto;

    List<OnScrollListener> scrollListeners = new ArrayList<OnScrollListener>();

    @Override
    public Footer binderFooterView (ListView listView) {
        view = (ViewGroup) View.inflate(listView.getContext(), R.layout.layout_footer, null);
        listView.addFooterView(view);
        listView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged (AbsListView view, int scrollState) {
                for (OnScrollListener listener : scrollListeners) {
                    listener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll (AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (OnScrollListener listener : scrollListeners) {
                    listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
        add(footerListener);
        view.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (listener != null) listener.loadPage(page.cur + 1);
                setMode(MODE_LOADING);
            }
        });
        return this;
    }

    public void updateFinalPage (int page) {
        this.page.stop = page;
    }

    @Override
    public Footer startAuto (boolean auto) {
        this.auto = auto;
        return this;
    }

    OnScrollListener footerListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged (AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll (AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (auto &&
                    firstVisibleItem + visibleItemCount >= totalItemCount && hasNext() && mode != MODE_LOADING) {
                if (listener != null) listener.loadPage(page.cur + 1);
                setMode(MODE_LOADING);
            }
        }
    };


    public Footer add (OnScrollListener listener) {
        scrollListeners.add(listener);
        return this;
    }

    @Override
    public Footer setListener (IMorePage morePage) {
        this.listener = morePage;
        return this;
    }

    public Footer update (Page page) {
        this.page = page;
        if (!hasNext()) {
            setMode(MODE_HIDE);
        } else {
            setMode(MODE_SHOW);
        }
        return this;
    }

    public Page getPage () {
        return page;
    }

    void setMode (int mode) {
        this.mode = mode;
        switch (mode) {
            case MODE_HIDE:
                view.setVisibility(View.GONE);
                break;
            case MODE_SHOW:
                view.setVisibility(View.VISIBLE);
                view.getChildAt(0).setVisibility(View.VISIBLE);
                view.getChildAt(1).setVisibility(View.GONE);
                break;
            case MODE_LOADING:
                view.setVisibility(View.VISIBLE);
                view.getChildAt(0).setVisibility(View.GONE);
                view.getChildAt(1).setVisibility(View.VISIBLE);
                break;
            case MODE_FINISH:
                view.setVisibility(View.GONE);
                break;
        }
    }


    public boolean hasNext () {
        if(page.total<=0)return false;
        if (page.stop != -1) {
            return page.stop > page.cur;
        }
        return page.total % page.avg == 0 && page.total > 0;
    }


}
