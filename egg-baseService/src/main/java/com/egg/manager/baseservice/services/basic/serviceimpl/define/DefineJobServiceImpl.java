package com.egg.manager.baseservice.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.define.DefineJobService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineJobMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineJobTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineJobVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineJobService.class)
public class DefineJobServiceImpl extends MyBaseMysqlServiceImpl<DefineJobMapper, DefineJob, DefineJobVo> implements DefineJobService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefineJobMapper defineJobMapper;


    @Override
    public MyCommonResult<DefineJobVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<DefineJob> paginationBean,
                                                              List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<DefineJob> queryWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = defineJobMapper.selectCount(queryWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = defineJobMapper.selectPage(page, queryWrapper);
        List<DefineJob> defineJobs = iPage.getRecords();
        result.setResultList(DefineJobTransfer.transferEntityToVoList(defineJobs));
        return result;
    }

    @Override
    public MyCommonResult<DefineJobVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineJobDto> paginationBean,
                                                           List<AntdvSortBean> sortBeans) {
        Page<DefineJobDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineJobDto> defineDepartmentDtoList = defineJobMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineJobTransfer.transferDtoToVoList(defineDepartmentDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, DefineJobVo defineJobVo) throws Exception {
        DefineJob defineJob = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJob = super.doBeforeCreate(loginUser, defineJob, true);
        return defineJobMapper.insert(defineJob);
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, DefineJobVo defineJobVo) throws Exception {
        Integer changeCount = 0;
        DefineJob defineJob = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJob = super.doBeforeUpdate(loginUser, defineJob);
        changeCount = defineJobMapper.updateById(defineJob);
        return changeCount;
    }


    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = defineJobMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        return defineJobMapper.fakeDeleteById(delId);
    }


}
