package com.egg.manager.serviceimpl.user;

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
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserJob;
import com.egg.manager.mapper.user.UserJobMapper;
import com.egg.manager.mapper.user.UserJobMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserJobService;
import com.egg.manager.service.user.UserJobService;
import com.egg.manager.vo.user.UserJobVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class UserJobServiceImpl extends ServiceImpl<UserJobMapper,UserJob> implements UserJobService {

    @Autowired
    private UserJobMapper userJobMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;



    /**
     * 分页查询 用户职务列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserJobPages(MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean){
        //解析 搜索条件
        EntityWrapper<UserJob> userJobEntityWrapper = new EntityWrapper<UserJob>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 userJobEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(userJobEntityWrapper,queryFormFieldBeanList) ;
        //取得 总数
        Integer total = userJobMapper.selectCount(userJobEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<UserJob> userJobs = userJobMapper.selectPage(rowBounds,userJobEntityWrapper) ;
        result.setResultList(UserJobVo.transferEntityToVoList(userJobs));
    }


    /**
     * 用户职务-新增
     * @param userJobVo
     * @throws Exception
     */
    @Override
    public Integer dealAddUserJob(UserJobVo userJobVo) throws Exception{
        Date now = new Date() ;
        UserJob userJob = UserJobVo.transferVoToEntity(userJobVo);
        userJob.setFid(MyUUIDUtil.renderSimpleUUID());
        userJob.setVersion(commonFuncService.defaultVersion);
        userJob.setState(BaseStateEnum.ENABLED.getValue());
        userJob.setCreateTime(now);
        userJob.setUpdateTime(now);
        Integer addCount = userJobMapper.insert(userJob) ;
        return addCount ;
    }


    /**
     * 用户职务-更新
     * @param userJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateUserJob(UserJobVo userJobVo,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        userJobVo.setUpdateTime(now);
        UserJob userJob = UserJobVo.transferVoToEntity(userJobVo);
        if(updateAll){  //是否更新所有字段
            changeCount = userJobMapper.updateAllColumnById(userJob) ;
        }   else {
            changeCount = userJobMapper.updateById(userJob) ;
        }
        return changeCount ;
    }



    /**
     * 用户职务-删除
     * @param delIds 要删除的用户职务id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelUserJobByArr(String[] delIds) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = userJobMapper.batchFakeDelByIds(delIdList);
        }
        return delCount ;
    }

    /**
     * 用户职务-删除
     * @param delId 要删除的用户职务id
     * @throws Exception
     */
    @Override
    public Integer dealDelUserJob(String delId) throws Exception{
        UserJob userJob = UserJob.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        Integer delCount = userJobMapper.updateById(userJob);
        return delCount ;
    }
}
