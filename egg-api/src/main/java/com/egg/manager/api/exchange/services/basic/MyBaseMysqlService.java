package com.egg.manager.api.exchange.services.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


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
    Integer dealLogicDeleteById(UserAccountEntity loginUser, String delId) throws Exception;

    /**
     * 批量逻辑删除
     * @param loginUser 当前登录用户
     * @param delIds    要删除的id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchLogicDelete(UserAccountEntity loginUser, String[] delIds) throws Exception;


    /**
     * 取得前端传递的分页配置
     * @param loginUser      当前登录用户
     * @param result
     * @param queryFieldList
     * @param vpage
     * @param sortMap
     * @return
     */
    QueryWrapper<T> doGetPageQueryWrapper(UserAccountEntity loginUser, WebResult result, List<QueryField> queryFieldList, AntdvPage vpage,
                                          AntdvSortMap sortMap);

    /**
     * 将条件封装类集合设置到QueryWrapper
     * @param queryWrapper
     * @param queryFieldList
     */
    void dealSetConditionsMapToEntityWrapper(QueryWrapper queryWrapper, List<QueryField> queryFieldList);


    /**
     * 更新Entity之前调用
     * @param loginUser 当前登录用户
     * @param t
     * @param uuidFlag
     * @return
     */
    T doBeforeCreate(UserAccountEntity loginUser, T t);

    /**
     * 更新Entity之前调用
     * @param loginUser 当前登录用户
     * @param t
     * @return
     */
    T doBeforeUpdate(UserAccountEntity loginUser, T t);


    /**
     * 根据id删除(删除前调用)
     * @param loginUser 当前登录用户
     * @param tClass
     * @param idVal
     * @return
     */
    @Deprecated
    T doBeforeDeleteOneById(UserAccountEntity loginUser, Class<T> tClass, String idVal);


    /**
     * 用userAccountToken 取得 UserAccountXlsModel
     * @param userAccountToken
     * @param isRequired       是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    UserAccountEntity dealUserAccountTokenGetEntity(UserAccountToken userAccountToken, boolean isRequired) throws InvocationTargetException, IllegalAccessException;


    /**
     * 取得 mybatisplus-分页查询Pagination
     * @param vpage 分页bean
     * @return
     */
    Page dealAntvPageToPagination(AntdvPage vpage);


    /**
     * 判断UserAccount是否为空
     * @param userAccountEntity
     * @return
     */
    boolean checkUserAccountIsBlank(UserAccountEntity userAccountEntity);
}
