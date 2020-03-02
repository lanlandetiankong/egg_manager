package com.egg.manager.serviceimpl.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.dto.login.LoginAccountDTO;
import com.egg.manager.entity.define.DefineJob;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.define.DefineJobMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.define.DefineJobService;
import com.egg.manager.vo.define.DefineJobVo;
import com.egg.manager.vo.user.UserAccountVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
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
public class DefineJobServiceImpl extends ServiceImpl<DefineJobMapper,DefineJob> implements DefineJobService {


    @Autowired
    private DefineJobMapper defineJobMapper;
    @Autowired
    private CommonFuncService commonFuncService;

    /**
     * 分页查询 职务列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineJobPages(MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                      List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<DefineJob> defineJobEntityWrapper = new EntityWrapper<DefineJob>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 defineJobEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(defineJobEntityWrapper,queryFormFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                defineJobEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = defineJobMapper.selectCount(defineJobEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineJob> defineJobs = defineJobMapper.selectPage(rowBounds,defineJobEntityWrapper) ;
        result.setResultList(DefineJobVo.transferEntityToVoList(defineJobs));
    }


    /**
     * 职务-新增
     * @param defineJobVo
     * @throws Exception
     */
    @Override
    public Integer dealAddDefineJob(DefineJobVo defineJobVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineJob defineJob = DefineJobVo.transferVoToEntity(defineJobVo);
        defineJob.setFid(MyUUIDUtil.renderSimpleUUID());
        defineJob.setState(BaseStateEnum.ENABLED.getValue());
        defineJob.setCreateTime(now);
        defineJob.setUpdateTime(now);
        if(loginUser != null){
            defineJob.setCreateUser(loginUser.getFid());
            defineJob.setLastModifyer(loginUser.getFid());
        }
        Integer addCount = defineJobMapper.insert(defineJob) ;
        return addCount ;
    }


    /**
     * 职务-更新
     * @param defineJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateDefineJob(DefineJobVo defineJobVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineJobVo.setUpdateTime(now);
        DefineJob defineJob = DefineJobVo.transferVoToEntity(defineJobVo);
        if(loginUser != null){
            defineJob.setLastModifyer(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = defineJobMapper.updateAllColumnById(defineJob) ;
        }   else {
            changeCount = defineJobMapper.updateById(defineJob) ;
        }
        return changeCount ;
    }



    /**
     * 职务-删除
     * @param delIds 要删除的职务id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineJobByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineJobMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 职务-删除
     * @param delId 要删除的职务id
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineJob(String delId,UserAccount loginUser) throws Exception{
        DefineJob defineJob = DefineJob.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineJob.setLastModifyer(loginUser.getFid());
        }
        Integer delCount = defineJobMapper.updateById(defineJob);
        return delCount ;
    }



}
