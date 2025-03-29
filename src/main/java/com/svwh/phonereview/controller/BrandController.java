package com.svwh.phonereview.controller;

import com.svwh.phonereview.auth.annotation.IgnoreAuth;
import com.svwh.phonereview.domain.bo.BrandBo;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.entity.Brand;
import com.svwh.phonereview.domain.entity.PhoneModel;
import com.svwh.phonereview.domain.vo.BrandVo;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import com.svwh.phonereview.permission.annotation.CheckPermission;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 13:44
 */
@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    /**
     * 获取所有的品牌列表
     * @return
     */
    @GetMapping("/admin/all")
    public List<BrandVo> getAll(){
        return brandService.list();
    }

    /**
     * 管理员分页获取品牌列表
     * @param bo 过滤条件
     * @param pageQuery 分页参数
     * @return
     */
    @GetMapping("/admin/list")
    @CheckPermission
    public PageVo<BrandVo> queryPage(BrandBo bo, PageQuery pageQuery){
        return brandService.queryPage(bo,pageQuery);
    }

    /**
     * 管理员新增一个品牌
     * @param bo
     */
    @PostMapping("/admin")
    @CheckPermission
    public void add(@RequestBody BrandBo bo){
        brandService.add(bo);
    }


    /**
     * 管理员更新一个品牌的数据
     * @param brandId
     * @param bo
     */
    @PutMapping("/admin/{brandId}")
    public void update(@PathVariable Long brandId,@RequestBody BrandBo bo){
        bo.setId(brandId);
        brandService.update(bo);
    }

    /**
     * 管理员更新一个品牌的状态
     * @param brandId
     * @param bo
     */
    @PutMapping("/admin/{brandId}/status")
    public void updateStatus(@PathVariable Long brandId,@RequestBody BrandBo bo){
        bo.setId(brandId);
        brandService.updateStatus(bo);
    }


    /**
     * 管理员删除一个品牌
     * @param brandId
     */
    @DeleteMapping("/admin/{brandId}")
    public void delete(@PathVariable Long brandId){
        brandService.delete(brandId);
    }


    /**
     * -----------------------------------
     *             用户相关的
     * -----------------------------------
     */

    /**
     * 获取全部的手机品牌
     * @return
     */
    @GetMapping
    @IgnoreAuth
    public List<BrandVo> list(){
        return brandService.list();
    }

    /**
     * 获取某个品牌下的所有的手机型号
     * @param brandId 品牌id
     * @return
     */
    @GetMapping("/{brandId}/models")
    public List<PhoneModelVo> listModels(@PathVariable Long brandId){
        return brandService.listModels(brandId);
    }
}
