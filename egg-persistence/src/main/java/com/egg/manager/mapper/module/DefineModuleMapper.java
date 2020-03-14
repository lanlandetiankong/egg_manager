package com.egg.manager.mapper.module;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.dto.module.DefineModuleDto;
import com.egg.manager.entity.module.DefineModule;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface DefineModuleMapper extends BaseMapper<DefineModule> {


    /**
     * [分页搜索查询] - 模块定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineModuleDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 批量 伪删除
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;
}
