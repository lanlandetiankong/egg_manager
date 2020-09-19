package com.egg.manager.baseService.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.user.UserDepartmentService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.db.mysql.mapper.user.UserDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserDepartmentTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserDepartmentVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Service(interfaceClass = UserDepartmentService.class)
public class UserDepartmentServiceImpl extends MyBaseMysqlServiceImpl<UserDepartmentMapper,UserDepartment,UserDepartmentVo> implements UserDepartmentService {

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private UserDepartmentMapper userDepartmentMapper ;
    @Reference
    private CommonFuncService commonFuncService ;
    @Reference
    private RedisHelper redisHelper ;



    /**
     * 取得当前用户关联的 UserDepartment
     * @return
     */
    @Override
    public List<UserDepartment> dealGetAllUserDepartmentByAccount(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        List<UserDepartment> userDepartment = dealGetAllUserDepartmentByAccountFromRedis(userAccount);
        if(userDepartment == null || userDepartment.isEmpty()) {
            userDepartment = dealGetAllUserDepartmentByAccountFromDb(userAccount);
        }
        return userDepartment;
    }
    /**
     * 从数据库是中取得当前用户关联的 UserDepartment
     * @return
     */
    @Override
    public List<UserDepartment> dealGetAllUserDepartmentByAccountFromDb(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        EntityWrapper<UserDepartment> userDepartmentEm = new EntityWrapper<UserDepartment>() ;
        userDepartmentEm.where("state={0}", BaseStateEnum.ENABLED.getValue())
                .and("user_account_id={0}",userAccount.getFid());
        userDepartmentEm.orderBy("update_time",false);
        List<UserDepartment> userDepartment = selectList(userDepartmentEm);
        return userDepartment;
    }

    /**
     * 从Redis中取得当前用户关联的 UserDepartment
     * @return
     */
    @Override
    public List<UserDepartment> dealGetAllUserDepartmentByAccountFromRedis(UserAccount userAccount) {
        if(checkUserAccountIsBlank(userAccount) == true) {
            return null ;
        }
        Object  userDepartmentListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserDepartmentKey(),userAccount.getFid()) ;
        String userDepartmentListJson = JSONObject.toJSONString(userDepartmentListObj);
        List<UserDepartment> userDepartment  = JSON.parseObject(userDepartmentListJson, new TypeReference<ArrayList<UserDepartment>>(){}) ;
        return userDepartment;
    }






    private boolean checkUserAccountIsBlank(UserAccount userAccount) {
        if(userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return true ;
        }
        return false;
    }







    /**
     * 分页查询 用户与部门关联 列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserDepartmentVo> dealGetUserDepartmentPages(UserAccount loginUser,MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                                       List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<UserDepartment> userDepartmentEntityWrapper = super.doGetPageQueryWrapper(loginUser,result,queryFormFieldBeanList,paginationBean,sortBeans);
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
        //取得 总数
        Integer total = userDepartmentMapper.selectCount(userDepartmentEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<UserDepartment> userDepartments = userDepartmentMapper.selectPage(rowBounds,userDepartmentEntityWrapper) ;
        result.setResultList(UserDepartmentTransfer.transferEntityToVoList(userDepartments));
        return result;
    }


    /**
     * 分页查询 用户与部门关联  Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserDepartmentVo> dealGetUserDepartmentDtoPages(MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                          List<AntdvSortBean> sortBeans){
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<UserDepartmentDto> userDepartmentDtoList = userDepartmentMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(UserDepartmentTransfer.transferDtoToVoList(userDepartmentDtoList));
        return result;
    }


    /**
     * 用户与部门关联 -新增
     * @param userDepartmentVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddUserDepartment(UserDepartmentVo userDepartmentVo, UserAccount loginUser) throws Exception{
        UserDepartment userDepartment = UserDepartmentTransfer.transferVoToEntity(userDepartmentVo);
        userDepartment = super.doBeforeCreate(loginUser,userDepartment,true);
        Integer addCount = userDepartmentMapper.insert(userDepartment) ;
        return addCount ;
    }


    /**
     * 用户与部门关联 -更新
     * @param userDepartmentVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateUserDepartment(UserDepartmentVo userDepartmentVo, UserAccount loginUser, boolean updateAll) throws Exception{
        Integer changeCount = 0;
        UserDepartment userDepartment = UserDepartmentTransfer.transferVoToEntity(userDepartmentVo);
        userDepartment = super.doBeforeUpdate(loginUser,userDepartment);
        if(updateAll){  //是否更新所有字段
            changeCount = userDepartmentMapper.updateAllColumnById(userDepartment) ;
        }   else {
            changeCount = userDepartmentMapper.updateById(userDepartment) ;
        }
        return changeCount ;
    }



    /**
     * 用户与部门关联 -删除
     * @param delIds 要删除的用户与部门关联 id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserDepartmentByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = userDepartmentMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 用户与部门关联 -删除
     * @param delId 要删除的用户与部门关联 id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserDepartment(String delId,UserAccount loginUser) throws Exception{
        UserDepartment userDepartment = super.doBeforeDeleteOneById(loginUser,UserDepartment.class,delId);
        Integer delCount = userDepartmentMapper.updateById(userDepartment);
        return delCount ;
    }


}
