package com.xiaoxu.response;

import java.util.List;

/**
 * 存放分页信息
 *
 * @author xiaoxu
 */
public class PageBean<T> {
    /**
     * 总页数
     */
    private Integer totalPages;
    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 总数据数量
     */
    private Integer totalCounts;
    /**
     * 存放查询结果
     */
    private List<T> list;
    /**
     * 每页展示数据量
     */
    private Integer pageSize;

    @Override
    public String toString() {
        return "PageBean{" +
                "totalPages=" + totalPages +
                ", currentPage=" + currentPage +
                ", totalCounts=" + totalCounts +
                ", list=" + list +
                ", pageSize=" + pageSize +
                '}';
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
