package com.xiaoxu.dataobject;

/**
 * Created on 2019/11/23 13:07
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
public class PageDao {
    private String search;
    private Integer start;
    private Integer pageSize;
    private Integer userId;

    public PageDao() {
    }

    public PageDao(String search, Integer start, Integer pageSize, Integer userId) {
        this.search = search;
        this.start = start;
        this.pageSize = pageSize;
        this.userId = userId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
