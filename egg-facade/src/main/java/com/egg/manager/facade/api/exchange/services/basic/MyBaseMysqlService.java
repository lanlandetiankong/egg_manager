package com.egg.manager.facade.api.exchange.services.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.facade.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;

import java.lang.reflect.InvocationTargetException;


/**
 * @author zhoucj
 * @description基本实现接口
 * @date 2020/10/20
 */
public interface MyBaseMysqlService<T extends Model<T>, M extends MyEggMapper<T>, V extends MyBaseMysqlVo>
        extends IService<T> {

    /**
     * 根据id进行逻辑删除
     * @param loginUser 当前登录用户
     * @param delId     要删除的id
     * @return
     * @throws Exception
     */
    Integer dealLogicDeleteById(EmUserAccountEntity loginUser, String delId) throws Exception;

    /**
     * 批量逻辑删除
     * @param loginUser 当前登录用户
     * @param delIds    要删除的id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchLogicDelete(EmUserAccountEntity loginUser, String[] delIds) throws Exception;


    /**
     * 取得前端传递的分页配置
     * @param loginUser 当前登录用户
     * @param result
     * @param queryPage 查询分页配置
     * @return
     */
    QueryWrapper<T> doGetPageQueryWrapper(EmUserAccountEntity loginUser, WebResult result, QueryPageBean queryPage);

    /**
     * 将条件封装类集合设置到QueryWrapper
     * @param queryWrapper
     * @param queryFieldArr 条件集合
     */
    void dealSetConditionsMapToEntityWrapper(QueryWrapper queryWrapper, QueryFieldArr queryFieldArr);


    /**
     * 更新Entity之前调用
     * @param loginUser 当前登录用户
     * @param t
     * @return
     */
    T doBeforeCreate(EmUserAccountEntity loginUser, T t);

    /**
     * 更新Entity之前调用
     * @param loginUser 当前登录用户
     * @param t
     * @return
     */
    T doBeforeUpdate(EmUserAccountEntity loginUser, T t);


    /**
     * 根据id删除(删除前调用)
     * @param loginUser 当前登录用户
     * @param tClass
     * @param idVal
     * @return
     */
    @Deprecated
    T doBeforeDeleteOneById(EmUserAccountEntity loginUser, Class<T> tClass, String idVal);


    /**
     * 用userAccountToken 取得 UserAccountXlsModel
     * @param userAccountToken
     * @param isRequired       是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    EmUserAccountEntity dealUserAccountTokenGetEntity(UserAccountToken userAccountToken, boolean isRequired) throws InvocationTargetException, IllegalAccessException;

    /**
     * 判断UserAccount是否为空
     * @param emUserAccountEntity
     * @return
     */
    boolean checkUserAccountIsBlank(EmUserAccountEntity emUserAccountEntity);
}
