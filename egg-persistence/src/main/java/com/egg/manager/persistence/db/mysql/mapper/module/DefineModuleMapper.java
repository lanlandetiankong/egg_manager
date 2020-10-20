package com.egg.manager.persistence.db.mysql.mapper.module;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie
 * @since 2019-09-12
 */
public interface DefineModuleMapper extends BaseMapper<DefineModule> {


    /**
     * [分页搜索查询] - 模块定义
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineModuleDto> selectQueryPage(Page<DefineModuleDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);
}
