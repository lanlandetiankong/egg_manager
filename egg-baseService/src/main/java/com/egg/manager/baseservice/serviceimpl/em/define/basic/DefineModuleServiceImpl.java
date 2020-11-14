package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.DefineModuleService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineModuleEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineModuleMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineModuleDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineModuleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineModuleVo;
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
@Service(interfaceClass = DefineModuleService.class)
public class DefineModuleServiceImpl extends MyBaseMysqlServiceImpl<DefineModuleMapper, DefineModuleEntity, DefineModuleVo> implements DefineModuleService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;


    @Autowired
    private DefineModuleMapper defineModuleMapper;


    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineModuleEntity> paginationBean,
                                            List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<DefineModuleEntity> defineModuleEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = defineModuleMapper.selectCount(defineModuleEntityWrapper);
        result.settingPage(paginationBean, Long.valueOf(total));
        IPage iPage = defineModuleMapper.selectPage(page, defineModuleEntityWrapper);
        List<DefineModuleEntity> defineModuleEntities = iPage.getRecords();
        result.putResultList(DefineModuleTransfer.transferEntityToVoList(defineModuleEntities));
        return result;
    }


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineModuleDto> paginationBean,
                                         List<AntdvSortBean> sortBeans) {
        Page<DefineModuleDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineModuleDto> defineModuleDtoList = defineModuleMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.settingPage(paginationBean, mpPagination.getTotal());
        result.putResultList(DefineModuleTransfer.transferDtoToVoList(defineModuleDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineModuleVo defineModuleVo) throws Exception {
        DefineModuleEntity defineModuleEntity = DefineModuleTransfer.transferVoToEntity(defineModuleVo);
        defineModuleEntity = super.doBeforeCreate(loginUserInfo, defineModuleEntity, true);
        return defineModuleMapper.insert(defineModuleEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineModuleVo defineModuleVo) throws Exception {
        Integer changeCount = 0;
        DefineModuleEntity defineModuleEntity = DefineModuleTransfer.transferVoToEntity(defineModuleVo);
        defineModuleEntity = super.doBeforeUpdate(loginUserInfo, defineModuleEntity);
        changeCount = defineModuleMapper.updateById(defineModuleEntity);
        return changeCount;
    }

}
