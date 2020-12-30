package com.egg.manager.api.services.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.EmDefineJobService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineJobMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineJobDto;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineJobTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineJobVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
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
@Service(interfaceClass = EmDefineJobService.class)
public class EmDefineJobServiceImpl extends MyBaseMysqlServiceImpl<EmDefineJobMapper, EmDefineJobEntity, EmDefineJobVo> implements EmDefineJobService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmDefineJobMapper emDefineJobMapper;


    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineJobEntity> queryPageBean) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(queryPageBean.getPageConf());
        //解析 搜索条件
        QueryWrapper<EmDefineJobEntity> queryWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryPageBean);
        //取得 总数
        Integer total = emDefineJobMapper.selectCount(queryWrapper);
        result.settingPage(queryPageBean.getPageConf(), Long.valueOf(total));
        IPage iPage = emDefineJobMapper.selectPage(page, queryWrapper);
        List<EmDefineJobEntity> defineJobEntities = iPage.getRecords();
        result.putGridList(EmDefineJobTransfer.transferEntityToVoList(defineJobEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineJobDto> queryPageBean) {
        Page<EmDefineJobDto> mpPagination = queryPageBean.toMpPage();
        List<EmDefineJobDto> defineDepartmentDtoList = emDefineJobMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean, mpPagination);
        result.putGridList(EmDefineJobTransfer.transferDtoToVoList(defineDepartmentDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineJobVo emDefineJobVo) throws Exception {
        EmDefineJobEntity emDefineJobEntity = EmDefineJobTransfer.transferVoToEntity(emDefineJobVo);
        emDefineJobEntity = super.doBeforeCreate(loginUserInfo, emDefineJobEntity);
        return emDefineJobMapper.insert(emDefineJobEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineJobVo emDefineJobVo) throws Exception {
        Integer changeCount = 0;
        EmDefineJobEntity emDefineJobEntity = EmDefineJobTransfer.transferVoToEntity(emDefineJobVo);
        emDefineJobEntity = super.doBeforeUpdate(loginUserInfo, emDefineJobEntity);
        changeCount = emDefineJobMapper.updateById(emDefineJobEntity);
        return changeCount;
    }

    @Override
    public List<EmDefineJobEntity> findAllByUserAcccountId(String userAccountId, Short stateVal) {
        return this.findAllByUserAcccountId(userAccountId, stateVal);
    }

}
