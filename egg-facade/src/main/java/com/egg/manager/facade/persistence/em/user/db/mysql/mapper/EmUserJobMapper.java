package com.egg.manager.facade.persistence.em.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserJobEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserJobDto;
import com.egg.manager.facade.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserJobMapper extends MyEggMapper<EmUserJobEntity> {

    /**
     * [分页搜索查询] - 用户职务
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmUserJobDto> selectQueryPage(Page<EmUserJobDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 取得用户拥有的所有职务id集合
     * @param userAccountId
     * @param filterEnable  是否只查询状态为可用的
     * @return
     */
    List<String> findAllJobIdByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("filterEnable") boolean filterEnable);


    /**
     * 根据用户id 修改指定职务关联 的可用状态
     * @param userAccountId
     * @param jobIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByUserAccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("jobIdList") List<String> jobIdList, @Param("stateVal") Short stateVal,
                                        @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);
}
