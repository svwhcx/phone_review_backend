package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.domain.bo.BrandBo;
import com.svwh.phonereview.domain.bo.PhoneModelBo;
import com.svwh.phonereview.domain.entity.Brand;
import com.svwh.phonereview.domain.vo.BrandVo;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import com.svwh.phonereview.mapper.BrandMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.BrandService;
import com.svwh.phonereview.service.PhoneModelService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 13:50
 */
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;
    private final PhoneModelService phoneModelService;

    @Override
    public PageVo<BrandVo> queryPage(BrandBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Brand> bLqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(bo.getName())){
            bLqw.like(Brand::getName,bo.getName());
        }
        bLqw.orderByDesc(Brand::getCreateTime);
        return brandMapper.selectVoPage(pageQuery.buildMybatisPage(),bLqw);
    }

    @Override
    public void add(BrandBo bo) {
        bo.setCreateTime(LocalDateTime.now());
        Brand brand = MapstructUtils.convert(bo, Brand.class);
        brandMapper.insert(brand);
    }

    @Override
    public void update(BrandBo bo) {
        brandMapper.updateById(MapstructUtils.convert(bo, Brand.class));
    }

    @Override
    public void updateStatus(BrandBo bo) {
        brandMapper.updateById(MapstructUtils.convert(bo, Brand.class));
    }

    @Override
    public void delete(Long brandId) {
        // 删除一个品牌，需要删除对应的帖子和手机型号
        brandMapper.deleteById(brandId);
    }

    @Override
    public List<BrandVo> list() {
        return brandMapper.selectVoList();
    }

    @Override
    public List<PhoneModelVo> listModels(Long brandId) {
        PhoneModelBo bo = new PhoneModelBo();
        bo.setBrandId(brandId);
        return phoneModelService.queryPage(bo,new PageQuery()).getRecords();
    }

    @Override
    public BrandVo getById(Long brandId) {
        return brandMapper.selectVoById(brandId);
    }

}
