package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.common.constant.FavoriteConstant;
import com.svwh.phonereview.domain.bo.PhoneModelBo;
import com.svwh.phonereview.domain.entity.Brand;
import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.entity.PhoneModel;
import com.svwh.phonereview.domain.vo.BrandVo;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import com.svwh.phonereview.mapper.BrandMapper;
import com.svwh.phonereview.mapper.FavoriteMapper;
import com.svwh.phonereview.mapper.PhoneModelMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.PhoneModelService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:57
 */
@Service
@RequiredArgsConstructor
public class PhoneModelServiceImpl implements PhoneModelService {

    private final PhoneModelMapper phoneModelMapper;
    private final FavoriteMapper favoriteMapper;
    private final BrandMapper brandMapper;

    @Override
    public PageVo<PhoneModelVo> queryPage(PhoneModelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PhoneModel> phLqw = Wrappers.lambdaQuery();

        if (bo.getBrandId() != null){
            phLqw.eq(PhoneModel::getBrandId,bo.getBrandId());
        }
        if (StringUtils.isNotBlank(bo.getKeyword())){
            phLqw.like(PhoneModel::getName,bo.getKeyword())
                    .or()
                    .like(PhoneModel::getDescription,bo.getKeyword());
        }
        PageVo<PhoneModelVo> pagevo = phoneModelMapper.selectVoPage(pageQuery.buildMybatisPage(),phLqw);
        if (pagevo.getRecords() == null || pagevo.getRecords().isEmpty()){
            return pagevo;
        }
        List<Long> brandIds = pagevo.getRecords().stream().map(PhoneModelVo::getBrandId).toList();
        LambdaQueryWrapper<Brand> bLqw = Wrappers.lambdaQuery();
        bLqw.in(Brand::getId,brandIds)
                .select(Brand::getId,Brand::getName,Brand::getLogo);
        List<BrandVo> brands = brandMapper.selectVoList(bLqw);
        Map<Long,BrandVo> brandMap = brands.stream().collect(Collectors.toMap(BrandVo::getId, Function.identity()));
        pagevo.getRecords().forEach(item -> item.setBrand(brandMap.get(item.getBrandId())));
        return pagevo;
    }

    @Override
    public void add(PhoneModelBo bo) {
        bo.setCreateTime(LocalDateTime.now());
        phoneModelMapper.insert(MapstructUtils.convert(bo, PhoneModel.class));
    }

    @Override
    public void update(PhoneModelBo bo) {
        phoneModelMapper.updateById(MapstructUtils.convert(bo, PhoneModel.class));
    }

    @Override
    public void delete(Long phoneModelId) {
        phoneModelMapper.deleteById(phoneModelId);
    }

    @Override
    public PhoneModelVo getPhoneModel(Long modelId) {
        return phoneModelMapper.selectVoById(modelId);
    }

    @Override
    public List<PhoneModelVo> getHotPhoneModel() {
        return new ArrayList<>();
    }

    @Override
    public PageVo<PhoneModelVo> userQueryPage(PhoneModelBo bo, PageQuery pageQuery) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        // 可以考虑是否进行参数校验
        LambdaQueryWrapper<Favorite> fLqw = Wrappers.lambdaQuery();
        fLqw.eq(Favorite::getUserId,userId)
                .eq(Favorite::getType, FavoriteConstant.FAVORITE_PHONE_MODEL)
                .orderByDesc(Favorite::getCreateTime);
        Page<Favorite> favoritePage = favoriteMapper.selectPage(pageQuery.buildMybatisPage(), fLqw);
        PageVo<PhoneModelVo> pageVo = new PageVo<>();
        pageVo.setTotal(favoritePage.getTotal());
        pageVo.setPageNum(favoritePage.getCurrent());
        if (favoritePage.getRecords() == null || favoritePage.getRecords().isEmpty()){
            return pageVo;
        }
        List<Long> postIds = favoritePage.getRecords().stream().map(Favorite::getTargetId).toList();
        List<PhoneModelVo> phoneModelVos = phoneModelMapper.selectVoBatchIds(postIds);
        Map<Long, PhoneModelVo> phoneModelVoMap = phoneModelVos.stream().collect(Collectors.toMap(PhoneModelVo::getId, Function.identity()));
        List<PhoneModelVo> postsVoList = new ArrayList<>();
        favoritePage.getRecords().forEach(item -> postsVoList.add(phoneModelVoMap.get(item.getTargetId())));
        pageVo.setRecords(postsVoList);
        return pageVo;
    }
}
