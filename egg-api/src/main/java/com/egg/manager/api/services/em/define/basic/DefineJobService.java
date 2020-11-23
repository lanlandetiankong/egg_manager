package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineJobMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineJobDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefineJobVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineJobService extends IService<DefineJobEntity>, MyBaseMysqlService<DefineJobEntity, DefineJobMapper, DefineJobVo> {


    /**
     * 分页查询 职务定义 列表
     * @param loginUserInfo  当前登录用户
     * @param result
     * @param queryFieldArr
     * @param vpage
     * @param sortMap
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryFieldArr queryFieldArr, AntdvPage<DefineJobEntity> vpage,
                                     AntdvSortMap sortMap);

    /**
     * 分页查询 职务定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo  当前登录用户
     * @param result
     * @param queryFieldArr
     * @param vpage
     * @param sortMap
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryFieldArr queryFieldArr, AntdvPage<DefineJobDto> vpage,
                                  AntdvSortMap sortMap);

    /**
     * 职务账号-新增
     * @param loginUserInfo 当前登录用户
     * @param defineJobVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineJobVo defineJobVo) throws Exception;

    /**
     * 职务账号-更新
     * @param loginUserInfo 当前登录用户
     * @param defineJobVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineJobVo defineJobVo) throws Exception;

}
