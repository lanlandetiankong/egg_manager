package com.egg.manager.api.services.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * @author zhoucj
 * @description:基本实现接口
 * @date 2020/10/20
 */
public interface MyBaseMysqlService<T extends Model<T>, M extends BaseMapper<T>, V extends MyBaseMysqlVo>
        extends IService<T> {
    /**
     * 取得前端传递的分页配置
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    QueryWrapper<T> doGetPageQueryWrapper(UserAccount loginUser, MyCommonResult<V> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                          List<AntdvSortBean> sortBeans);

    /**
     * 将条件封装类集合设置到QueryWrapper
     * @param queryWrapper
     * @param queryFieldBeanList
     */
    void dealSetConditionsMapToEntityWrapper(QueryWrapper queryWrapper, List<QueryFormFieldBean> queryFieldBeanList);


    /**
     * 更新Entity之前调用
     * @param loginUser 当前登录用户
     * @param t
     * @param uuidFlag
     * @return
     */
    T doBeforeCreate(UserAccount loginUser, T t, boolean uuidFlag);

    /**
     * 更新Entity之前调用
     * @param loginUser 当前登录用户
     * @param t
     * @return
     */
    T doBeforeUpdate(UserAccount loginUser, T t);


    /**
     * 根据id删除(删除前调用)
     * @param loginUser 当前登录用户
     * @param tClass
     * @param idVal
     * @return
     */
    T doBeforeDeleteOneById(UserAccount loginUser, Class<T> tClass, String idVal);


    /**
     * 用userAccountToken 取得 UserAccountXlsModel
     * @param userAccountToken
     * @param isRequired       是否必须取得 用户身份信息(获取失败时将抛出MyAuthenticationExpiredException异常)
     * @return UserAccountXlsModel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    UserAccount dealUserAccountTokenGetEntity(UserAccountToken userAccountToken, boolean isRequired) throws InvocationTargetException, IllegalAccessException;


    /**
     * 取得 mybatisplus-分页查询Pagination
     * @param paginationBean 分页bean
     * @return
     */
    Page dealAntvPageToPagination(AntdvPaginationBean paginationBean);


    /**
     * 判断UserAccount是否为空
     * @param userAccount
     * @return
     */
    boolean checkUserAccountIsBlank(UserAccount userAccount);
}
