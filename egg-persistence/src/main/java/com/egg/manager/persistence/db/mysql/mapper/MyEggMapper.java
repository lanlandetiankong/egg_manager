package com.egg.manager.persistence.db.mysql.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.egg.manager.persistence.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @Description: mybatisplus自定义增强扩展方法
 * @ClassName: MyEggMapper
 * @Author: zhoucj
 * @Date: 2020/10/26 17:10
 */
public interface  MyEggMapper<T> extends BaseMapper<T> {
    /**
     * 根据id伪删除
     * @return
     */
    int fakeDeleteById(@Param(EggMpSqlConst.COLUMN_FID) Serializable fid);

    /**
     * 批量逻辑删除/删除并填充实体类字段
     * @param entity 实体类
     * @return
     */
    int batchDeleteByIdsWithEntity(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList,@Param(Constants.ENTITY)T entity);

    /**
     * 批量逻辑删除/删除并填充修改人信息
     * @param loginUser 当前用户
     * @return
     */
    int batchDeleteByIdsWithModifyFill(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList,@Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccount loginUser);


    /**
     * 带Entity填充的逻辑删除/删除
     * @param entity
     * @return
     */
    int deleteByIdWithFill(T entity);

    /**
     * 带修改信息填充的逻辑删除/删除
     * @param fid 主键id
     * @param loginUser 当前登录用户
     * @return
     */
    int deleteByIdWithModifyFill(@Param(EggMpSqlConst.COLUMN_FID) Serializable fid,@Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccount loginUser);








    /**
     * 根据 ID 删除
     * @implNote 由于不支持同时修改其他参数，请勿调用该方法
     * @param id 主键ID
     */
    @Deprecated
    int deleteById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     * @implNote 由于不支持同时修改其他参数，请勿调用该方法
     * @param columnMap 表字段 map 对象
     */
    @Deprecated
    int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录
     * @implNote 由于不支持同时修改其他参数，请勿调用该方法
     * @param wrapper 实体对象封装操作类（可以为 null）
     */
    @Deprecated
    int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

    /**
     * 删除（根据ID 批量删除）
     * @implNote 由于不支持同时修改其他参数，请勿调用该方法
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    @Deprecated
    int deleteBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
}
