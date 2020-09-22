package com.egg.manager.baseService.services.basic.serviceimpl.module;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.module.DefineModuleService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.module.DefineModuleMapper;
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mysql.transfer.module.DefineModuleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;
import javafx.scene.control.Pagination;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = DefineModuleService.class)
public class DefineModuleServiceImpl extends MyBaseMysqlServiceImpl<DefineModuleMapper, DefineModule, DefineModuleVo> implements DefineModuleService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;


    @Autowired
    private DefineModuleMapper defineModuleMapper;


    /**
     * 分页查询 模块 列表
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineModuleVo> dealGetDefineModulePages(UserAccount loginUser, MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                   List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<DefineModule> defineModuleEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        ;
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = defineModuleMapper.selectCount(defineModuleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = defineModuleMapper.selectPage(page, defineModuleEntityWrapper);
        List<DefineModule> defineModules = iPage.getRecords();
        result.setResultList(DefineModuleTransfer.transferEntityToVoList(defineModules));
        return result;
    }


    /**
     * 分页查询 模块 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineModuleVo> dealGetDefineModuleDtoPages(UserAccount loginUser, MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                      List<AntdvSortBean> sortBeans) {
        Page<DefineModuleDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineModuleDto> defineModuleDtoList = defineModuleMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineModuleTransfer.transferDtoToVoList(defineModuleDtoList));
        return result;
    }


    /**
     * 模块定义-新增
     *
     * @param defineModuleVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealAddDefineModule(UserAccount loginUser, DefineModuleVo defineModuleVo) throws Exception {
        DefineModule defineModule = DefineModuleTransfer.transferVoToEntity(defineModuleVo);
        defineModule = super.doBeforeCreate(loginUser, defineModule, true);
        return defineModuleMapper.insert(defineModule);
    }


    /**
     * 模块定义-更新
     *
     * @param defineModuleVo
     * @param updateAll      是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealUpdateDefineModule(UserAccount loginUser, DefineModuleVo defineModuleVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        DefineModule defineModule = DefineModuleTransfer.transferVoToEntity(defineModuleVo);
        defineModule = super.doBeforeUpdate(loginUser, defineModule);
        if (updateAll) {  //是否更新所有字段
            changeCount = defineModuleMapper.updateById(defineModule);
        } else {
            changeCount = defineModuleMapper.updateById(defineModule);
        }
        return changeCount;
    }

    /**
     * 模块定义-删除
     *
     * @param delIds 要删除的模块id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelDefineModuleByArr(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = defineModuleMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 模块定义-删除
     *
     * @param delId 要删除的模块id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelDefineModule(UserAccount loginUser, String delId) throws Exception {
        DefineModule defineModule = super.doBeforeDeleteOneById(loginUser, DefineModule.class, delId);
        return defineModuleMapper.updateById(defineModule);
    }
}
