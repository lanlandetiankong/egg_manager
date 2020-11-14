package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.DefineJobService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineJobMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineJobDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineJobTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineJobVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
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
    public WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<DefineJobEntity> paginationBean,
                                            List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<DefineJobEntity> queryWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = defineJobMapper.selectCount(queryWrapper);
        result.settingPage(paginationBean, Long.valueOf(total));
        IPage iPage = defineJobMapper.selectPage(page, queryWrapper);
        List<DefineJobEntity> defineJobEntities = iPage.getRecords();
        result.putResultList(DefineJobTransfer.transferEntityToVoList(defineJobEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineJobDto> paginationBean,
                                         List<AntdvSortBean> sortBeans) {
        Page<DefineJobDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineJobDto> defineDepartmentDtoList = defineJobMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.settingPage(paginationBean, mpPagination.getTotal());
        result.putResultList(DefineJobTransfer.transferDtoToVoList(defineDepartmentDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineJobVo defineJobVo) throws Exception {
        DefineJobEntity defineJobEntity = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJobEntity = super.doBeforeCreate(loginUserInfo, defineJobEntity, true);
        return defineJobMapper.insert(defineJobEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineJobVo defineJobVo) throws Exception {
        Integer changeCount = 0;
        DefineJobEntity defineJobEntity = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJobEntity = super.doBeforeUpdate(loginUserInfo, defineJobEntity);
        changeCount = defineJobMapper.updateById(defineJobEntity);
        return changeCount;
    }

}
