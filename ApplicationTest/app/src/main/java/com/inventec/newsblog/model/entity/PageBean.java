package com.inventec.newsblog.model.entity;

import java.util.List;

/**
 * 通用分页数据实体类
 * Created by Test on 2017/3/1.
 */

public class PageBean<T> {
    private String allNum;//所有条目数
    private String allPages;//所有页数
    private String currentPage;//当前页
    private String maxResult;//每页最大条数
    private List<T> contentlist;//明细列表

    public String getAllNum() {
        return allNum;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public String getAllPages() {
        return allPages;
    }

    public void setAllPages(String allPages) {
        this.allPages = allPages;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(String maxResult) {
        this.maxResult = maxResult;
    }

    public List<T> getContentList() {
        return contentlist;
    }

    public void setContentList(List<T> contentList) {
        this.contentlist = contentList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{allNum:").append(allNum)
                .append(",allPages:").append(allPages)
                .append(",currentPage:").append(currentPage)
                .append(",maxResult:").append(maxResult)
                .append(",\ncontentlist:").append(contentlist +"}");

        return sb.toString();
    }
}
