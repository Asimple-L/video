package com.asimple.util;

import java.util.List;

/**
 * @author Asimple
 * @description 分页模型类
 **/
public class PageBean<T> {

    /**
     * 当前页码 page code
     */
    private int pc;
    /**
     * 总记录数 total record
     */
    private int tr;
    /**
     * //每页记录数
     */
    private int ps;
    /**
     * 当前页的记录
     */

    private List<T> beanList;
    /**
     *  用来保存条件
     */
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    /**
     * 记录总页数
     * @return
     */
    public int getTp() {
        //通过总记录数和每页记录数来计算总页数
        int tp = tr / ps;
        return tr%ps==0 ? tp:tp+1;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public List<T> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }
}
