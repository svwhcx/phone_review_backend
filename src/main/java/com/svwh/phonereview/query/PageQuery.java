package com.svwh.phonereview.query;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public class PageQuery {

    private Integer pageNum;

    private Integer pageSize;

    private Integer start;


    public PageQuery() {
    }

    public PageQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return (pageNum - 1) * pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public <T> Page<T> buildMybatisPage(){
        Integer pageNum = ObjectUtil.defaultIfNull(getPageNum(),1);
        Integer pageSize = ObjectUtil.defaultIfNull(getPageSize(),Integer.MAX_VALUE);
        if (pageNum <= 0){
            pageNum = 1;
        }
        return new Page<>(pageNum,pageSize);
    }

}
