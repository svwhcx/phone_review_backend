package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.PhoneModelBo;
import com.svwh.phonereview.domain.entity.PhoneModel;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:55
 */
public interface PhoneModelService {

    /**
     * 分页查询手机型号列表
     * @param bo 过滤条件
     * @param pageQuery 分页参数
     * @return
     */
    PageVo<PhoneModelVo> queryPage(PhoneModelBo bo, PageQuery pageQuery);

    /**
     * 管理员新增一个手机型号
     * @param bo
     */
    void add(PhoneModelBo bo);

    /**
     * 管理员修改一个手机型号
     * @param bo
     */
    void update(PhoneModelBo bo);

    /**
     * 管理员删除一个手机型号
     * @param phoneModelId
     */
    void delete(Long phoneModelId);

    /**
     * 获取一个手机型号的详细信息
     * @param modelId 手机型号的id
     * @return
     */
    PhoneModelVo getPhoneModel(Long modelId);

    /**
     * 获取热门的手机型号列表
     * @return
     */
    List<PhoneModelVo> getHotPhoneModel();


    /**
     * 获取用户收藏的手机列表
     * @param bo 过滤参数
     * @param pageQuery 分页参数
     * @return 用户的收藏列表
     */
    PageVo<PhoneModelVo> userQueryPage(PhoneModelBo bo, PageQuery pageQuery);
}
