package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.BrandBo;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.entity.PhoneModel;
import com.svwh.phonereview.domain.vo.BrandVo;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 13:50
 */
public interface BrandService {

    /**
     *
     * @param brandVo
     * @param pageQuery
     * @return
     */
    PageVo<BrandVo> queryPage(BrandBo bo, PageQuery pageQuery);

    /**
     * 管理员添加一个品牌
     * @param bo
     */
    void add(BrandBo bo);

    /**
     * 管理员更新一个品牌
     * @param bo
     */
    void update(BrandBo bo);

    /**
     * 管理员更新一个品牌的状态
     * @param bo
     */
    void updateStatus(BrandBo bo);

    /**
     * 管理员删除一个品牌
     * @param brandId
     */
    void delete(Long brandId);

    /**
     * 获取全部的品牌列表
     * @return
     */
    List<BrandVo> list();

    /**
     * 获取指定品牌下的所有机型
     * @param brandId 品牌id
     * @return
     */
    List<PhoneModelVo> listModels(Long brandId);
}
