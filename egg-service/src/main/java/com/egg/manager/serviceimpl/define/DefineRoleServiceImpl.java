package com.egg.manager.serviceimpl.define;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.define.DefineRoleMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.define.DefineRoleService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserRoleService;
import com.egg.manager.vo.define.DefineRoleVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class DefineRoleServiceImpl extends ServiceImpl<DefineRoleMapper,DefineRole> implements DefineRoleService {

    @Autowired
    private DefineRoleMapper defineRoleMapper;
    @Autowired
    private UserRoleService userRoleService ;
    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;
    @Autowired
    private RedisHelper redisHelper ;

    @Override
    public List<DefineRole> dealGetRolesByAccount(UserAccount userAccount) {
        if(userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return null ;
        }

        return null;
    }
    @Override
    public List<DefineRole> dealGetAllDefineRoles() {
        List<DefineRole> allDefineRoles = dealGetAllDefineRolesFromRedis(false);
        if(allDefineRoles == null || allDefineRoles.isEmpty()) {
            allDefineRoles = dealGetAllDefineRolesFromDb() ;
        }
        return allDefineRoles ;
    }

    @Override
    public List<DefineRole> dealGetAllDefineRolesFromDb() {
        EntityWrapper<DefineRole> defineRoleEntityWrapper = new EntityWrapper<DefineRole>() ;
        defineRoleEntityWrapper.where("state={0}",BaseStateEnum.ENABLED.getValue()) ;
        return selectList(defineRoleEntityWrapper);
    }

    /**
     * 从 redis 中取得所有 角色
     * @param refreshRedis  是否先刷新redis缓存
     * @return
     */
    @Override
    public List<DefineRole> dealGetAllDefineRolesFromRedis(boolean refreshRedis) {
        if(refreshRedis == true) {
            redisHelper.valuePut(redisPropsOfShiroCache.getDefineRoleAllKey(),dealGetAllDefineRolesFromDb()) ;
        }
        Object allDefineRoleObj = redisHelper.getValue(redisPropsOfShiroCache.getDefineRoleAllKey()) ;
        String allDefineRoleJson = JSONObject.toJSONString(allDefineRoleObj);
        List<DefineRole> allDefineRoles  = JSON.parseObject(allDefineRoleJson, new TypeReference<ArrayList<DefineRole>>(){}) ;
        return allDefineRoles ;
    }

    /**
     * 根据 用户账号 取得所有角色
     * @param userAccount
     * @return
     */
    @Override
    public List<DefineRole> dealGetRolesFormRedisByAccount(UserAccount userAccount) {
        List<UserRole> userRoles = userRoleService.dealGetAllUserRoleByAccount(userAccount);
        List<DefineRole> defineRoles = null ;
        if(userRoles == null || userRoles.isEmpty()) {
            return defineRoles;
        }   else {
            defineRoles = new ArrayList<DefineRole>() ;
            Set<String> roleIds = new HashSet<String>();
            for(UserRole userRole : userRoles){
                if(StringUtils.isNotBlank(userRole.getDefineRoleId())){
                    roleIds.add(userRole.getDefineRoleId());
                }
            }
            EntityWrapper<DefineRole> defineRoleEntityWrapper = new EntityWrapper<DefineRole>() ;
            defineRoleEntityWrapper.where("state={0}",BaseStateEnum.ENABLED.getValue())
                    .in(true,"define_role_id",roleIds);
            defineRoles = selectList(defineRoleEntityWrapper);
        }
        return defineRoles ;
    }







    @Autowired
    private CommonFuncService commonFuncService ;




    /**
     * 分页查询 角色
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineRolePages(MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean) {
        //解析 搜索条件
        EntityWrapper<DefineRole> defineRoleEntityWrapper = new EntityWrapper<DefineRole>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到defineRoleEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(defineRoleEntityWrapper,queryFieldBeanList) ;
        //取得 总数
        Integer total = defineRoleMapper.selectCount(defineRoleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineRole> defineRoles = defineRoleMapper.selectPage(rowBounds,defineRoleEntityWrapper) ;
        result.setResultList(DefineRoleVo.transferEntityToVoList(defineRoles));
    }




    /**
     * 角色定义-新增
     * @param defineRoleVo
     * @throws Exception
     */
    @Override
    public Integer dealAddDefineRole(DefineRoleVo defineRoleVo) throws Exception{
        Date now = new Date() ;
        DefineRole defineRole = DefineRoleVo.transferVoToEntity(defineRoleVo);
        defineRole.setFid(MyUUIDUtil.renderSimpleUUID());
        defineRole.setVersion(commonFuncService.defaultVersion);
        defineRole.setState(BaseStateEnum.ENABLED.getValue());
        defineRole.setCreateTime(now);
        defineRole.setUpdateTime(now);
        Integer addCount = defineRoleMapper.insert(defineRole) ;
        return addCount ;
    }


    /**
     * 角色定义-更新
     * @param defineRoleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateDefineRole(DefineRoleVo defineRoleVo,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineRoleVo.setUpdateTime(now);
        DefineRole defineRole = DefineRoleVo.transferVoToEntity(defineRoleVo);
        if(updateAll){  //是否更新所有字段
            changeCount = defineRoleMapper.updateAllColumnById(defineRole) ;
        }   else {
            changeCount = defineRoleMapper.updateById(defineRole) ;
        }
        return changeCount ;
    }

    /**
     * 角色定义-删除
     * @param delIds 要删除的角色id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineRoleByArr(String[] delIds) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineRoleMapper.batchFakeDelByIds(delIdList);
        }
        return delCount ;
    }

    /**
     * 角色定义-删除
     * @param delId 要删除的角色id
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineRole(String delId) throws Exception{
        DefineRole defineRole = DefineRole.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        Integer delCount = defineRoleMapper.updateById(defineRole);
        return delCount ;
    }
}
