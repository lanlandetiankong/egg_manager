package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.define.basic.DefineJobService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineJobMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineJobDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineJobTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineJobVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineJobService.class)
public class DefineJobServiceImpl extends MyBaseMysqlServiceImpl<DefineJobMapper, DefineJobEntity, DefineJobVo> implements DefineJobService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefineJobMapper defineJobMapper;


    @Override
    public MyCommonResult<DefineJobVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<DefineJobEntity> paginationBean,
                                                              List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<DefineJobEntity> queryWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = defineJobMapper.selectCount(queryWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = defineJobMapper.selectPage(page, queryWrapper);
        List<DefineJobEntity> defineJobEntities = iPage.getRecords();
        result.setResultList(DefineJobTransfer.transferEntityToVoList(defineJobEntities));
        return result;
    }

    @Override
    public MyCommonResult<DefineJobVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineJobDto> paginationBean,
                                                           List<AntdvSortBean> sortBeans) {
        Page<DefineJobDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineJobDto> defineDepartmentDtoList = defineJobMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineJobTransfer.transferDtoToVoList(defineDepartmentDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccountEntity loginUser, DefineJobVo defineJobVo) throws Exception {
        DefineJobEntity defineJobEntity = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJobEntity = super.doBeforeCreate(loginUser, defineJobEntity, true);
        return defineJobMapper.insert(defineJobEntity);
    }


    @Override
    public Integer dealUpdate(UserAccountEntity loginUser, DefineJobVo defineJobVo) throws Exception {
        Integer changeCount = 0;
        DefineJobEntity defineJobEntity = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJobEntity = super.doBeforeUpdate(loginUser, defineJobEntity);
        changeCount = defineJobMapper.updateById(defineJobEntity);
        return changeCount;
    }

}
