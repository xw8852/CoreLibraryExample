package com.msx7.lib.widget.list.common;

/**
 * Created by xiaowei on 2015/12/21.
 */
public class Page {
    /**
     * 当前页
     */
    public int cur;
    /**
     * 结束页
     */
    public int stop = -1;
    /**
     * 开始页
     */
    public int start = 0;
    /**
     * 平均每页展示数量
     */
    public int avg = 10;

    /**
     * 当无法确定总页数时，记录当前已有数据条数
     */
    public int total;

    /**
     * @param start 开始页
     * @param avg   平均每页展示数量
     * @param stop  结束页 无法确定则用-1
     */
    public Page(int start, int avg, int stop) {
        this.stop = stop;
        this.start = start;
        this.avg = avg;
        this.cur = start;
    }
}
