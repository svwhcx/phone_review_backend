package com.svwh.phonereview.query;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;


public class PageVo<T> {


    /**
     * 当前要查询的页数
     */
    private Long pageNum;

    /**
     * 系统过滤后一共多少条数据
     */
    private Long total;

    /**
     * 数据集合
     */
    private List<T> records;

    public PageVo() {
    }

    public PageVo(Long pageNum, Long total, List<T> records) {
        this.pageNum = pageNum;
        this.total = total;
        this.records = records;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> list) {
        this.records = list;
    }

    /**
     * 从MybatisPlus的分页插件中进行构建
     * @param iPage
     * @return
     * @param <T>
     */
    public static <T>  PageVo<T> build(IPage<T> iPage){
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setPageNum(iPage.getCurrent());
        pageVo.setTotal(iPage.getTotal());
        pageVo.setRecords(iPage.getRecords());
        return pageVo;
    }
}
