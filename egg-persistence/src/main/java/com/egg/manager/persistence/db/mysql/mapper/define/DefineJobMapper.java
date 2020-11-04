package com.egg.manager.persistence.db.mysql.mapper.define;

import com.egg.manager.persistence.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineJobDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineJobMapper extends MyEggMapper<DefineJob> {

    /**
     * [分页搜索查询] - 职务定义
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<DefineJobDto> selectQueryPage(Page<DefineJobDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 查询指定用户的 用户-职务 关联表
     * @param userAccountId
     * @param stateVal      指定state的值
     * @return
     */
    List<DefineJob> findAllJobByUserAcccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) Long userAccountId, @Param("stateVal") Short stateVal);

}
