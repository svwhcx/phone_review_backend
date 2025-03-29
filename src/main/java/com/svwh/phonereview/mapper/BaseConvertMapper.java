package com.svwh.phonereview.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.utils.MapstructUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 21:22
 */
public interface BaseConvertMapper<T,V> extends BaseMapper<T> {


    Log log = LogFactory.getLog(BaseConvertMapper.class);

    default Class<V> currentVoClass() {
        return (Class<V>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseConvertMapper.class, 1);
    }

    default Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseConvertMapper.class, 0);
    }

    default List<T> selectList() {
        return this.selectList(new QueryWrapper());
    }

    default boolean insertBatch(Collection<T> entityList) {
        Db.saveBatch(entityList);
        return true;
    }

    default boolean updateBatchById(Collection<T> entityList) {
        Db.updateBatchById(entityList);
        return true;
    }

    default boolean insertOrUpdateBatch(Collection<T> entityList) {
        Db.saveOrUpdateBatch(entityList);
        return true;
    }

    default boolean insertBatch(Collection<T> entityList, int batchSize) {
        Db.saveBatch(entityList, batchSize);
        return true;
    }

    default boolean updateBatchById(Collection<T> entityList, int batchSize) {
        Db.updateBatchById(entityList, batchSize);
        return true;
    }

    default boolean insertOrUpdateBatch(Collection<T> entityList, int batchSize) {
        Db.saveOrUpdateBatch(entityList, batchSize);
        return true;
    }

    default V selectVoById(Serializable id) {
        return this.selectVoById(id, this.currentVoClass());
    }

    default <C> C selectVoById(Serializable id, Class<C> voClass) {
        T obj = this.selectById(id);
        return ObjectUtil.isNull(obj) ? null : MapstructUtils.convert(obj, voClass);
    }

    default List<V> selectVoBatchIds(Collection<? extends Serializable> idList) {
        return this.selectVoBatchIds(idList, this.currentVoClass());
    }

    default <C> List<C> selectVoBatchIds(Collection<? extends Serializable> idList, Class<C> voClass) {
        List<T> list = this.selectBatchIds(idList);
        return (List)(CollUtil.isEmpty(list) ? CollUtil.newArrayList(new Object[0]) : MapstructUtils.convert(list, voClass));
    }

    default List<V> selectVoByMap(Map<String, Object> map) {
        return this.selectVoByMap(map, this.currentVoClass());
    }

    default <C> List<C> selectVoByMap(Map<String, Object> map, Class<C> voClass) {
        List<T> list = this.selectByMap(map);
        return (List)(CollUtil.isEmpty(list) ? CollUtil.newArrayList(new Object[0]) : MapstructUtils.convert(list, voClass));
    }

    default V selectVoOne(Wrapper<T> wrapper) {
        return this.selectVoOne(wrapper, this.currentVoClass());
    }

    default V selectVoOne(Wrapper<T> wrapper, boolean throwEx) {
        return this.selectVoOne(wrapper, this.currentVoClass(), throwEx);
    }

    default <C> C selectVoOne(Wrapper<T> wrapper, Class<C> voClass) {
        T obj = this.selectOne(wrapper);
        return ObjectUtil.isNull(obj) ? null : MapstructUtils.convert(obj, voClass);
    }

    default <C> C selectVoOne(Wrapper<T> wrapper, Class<C> voClass, boolean throwEx) {
        T obj = this.selectOne(wrapper, throwEx);
        return ObjectUtil.isNull(obj) ? null : MapstructUtils.convert(obj, voClass);
    }

    default List<V> selectVoList() {
        return this.selectVoList(new QueryWrapper(), this.currentVoClass());
    }

    default List<V> selectVoList(Wrapper<T> wrapper) {
        return this.selectVoList(wrapper, this.currentVoClass());
    }

    default <C> List<C> selectVoList(Wrapper<T> wrapper, Class<C> voClass) {
        List<T> list = this.selectList(wrapper);
        return (List)(CollUtil.isEmpty(list) ? CollUtil.newArrayList(new Object[0]) : MapstructUtils.convert(list, voClass));
    }

    default <P extends PageVo<T>> P selectDoPage(IPage<T> page, Wrapper<T> wrapper){
        return this.selectVoPage(page, wrapper, this.currentModelClass());
    }

    default <P extends PageVo<V>> P selectVoPage(IPage<T> page, Wrapper<T> wrapper) {
        return this.selectVoPage(page, wrapper, this.currentVoClass());
    }

    default <C,P extends PageVo<C>> P selectVoPage(IPage<T> page, Wrapper<T> wrapper, Class<C> voClass) {
        List<T> list = this.selectList(page, wrapper);
        page.setRecords(list);
        PageVo pageVo = new PageVo(page.getCurrent(), page.getTotal(),list);
        if (CollUtil.isEmpty(list)) {
            return (P) pageVo;
        } else {
            pageVo.setRecords(MapstructUtils.convert(list, voClass));
            return (P) pageVo;
        }
    }

    default <C> List<C> selectObjs(Wrapper<T> wrapper, Function<? super Object, C> mapper) {
        return (List)this.selectObjs(wrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }
}
