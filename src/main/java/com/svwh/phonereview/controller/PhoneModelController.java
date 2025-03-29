package com.svwh.phonereview.controller;

import com.svwh.phonereview.domain.bo.PhoneModelBo;
import com.svwh.phonereview.domain.entity.PhoneModel;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import com.svwh.phonereview.permission.annotation.CheckPermission;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.PhoneModelService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 22:02
 */
@RestController
@RequestMapping("/phones")
@RequiredArgsConstructor
public class PhoneModelController {

    private final PhoneModelService phoneModelService;


    /**
     * 管理员分页获取
     * @param bo 过滤条件
     * @param pageQuery 分页参数
     * @return
     */
    @GetMapping("/admin/list")
    @CheckPermission
    public PageVo<PhoneModelVo> queryPage(PhoneModelBo bo, PageQuery pageQuery) {
        return phoneModelService.queryPage(bo, pageQuery);
    }

    /**
     * 管理员添加一个手机型号
     * @param bo 手机型号数据
     */
    @PostMapping("/admin/add")
    @CheckPermission
    public void add(@RequestBody PhoneModelBo bo) {
        phoneModelService.add(bo);
    }

    /**
     * 管理员更新一个手机型号
     * @param phoneModelId
     * @param bo
     */
    @PutMapping("/admin/{phoneModelId}")
    @CheckPermission
    public void update(@PathVariable Long phoneModelId,@RequestBody PhoneModelBo bo) {
        bo.setId(phoneModelId);
        phoneModelService.update(bo);
    }

    /**
     * 管理员删除一个型号
     * @param phoneModelId
     */
    @DeleteMapping("/admin/{phoneModelId}")
    @CheckPermission
    public void delete(@PathVariable Long phoneModelId) {
        phoneModelService.delete(phoneModelId);
    }


    /**
     * --------------------------------
     *       下面是用户相关的功能
     * --------------------------------
     */

    @GetMapping("/{modelId}")
    public PhoneModelVo getPhoneModel(@NotNull @PathVariable Long modelId){
        return phoneModelService.getPhoneModel(modelId);
    }


    /**
     * 获取热门的手机型号
     * @return
     */
    @GetMapping("/hot")
    public List<PhoneModelVo> getHotPhoneModel(){
        return phoneModelService.getHotPhoneModel();
    }

    /**
     * TODO
     * 搜索手机型号，注意有过滤条件
     */
    @GetMapping
    public PageVo<PhoneModelVo> search(PhoneModelBo bo, PageQuery pageQuery){
        return phoneModelService.queryPage(bo, pageQuery);
    }

    /**
     * 获取用户收藏的手机
     * @param pageQuery 分页参数
     * @return
     */
    @GetMapping("/user/favorites")
    public PageVo<PhoneModelVo> getUserQueryPage(PhoneModelBo bo, PageQuery pageQuery){
        return phoneModelService.userQueryPage(bo, pageQuery);
    }
}
