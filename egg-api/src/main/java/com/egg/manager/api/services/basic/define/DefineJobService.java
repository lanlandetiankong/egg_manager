package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineJobMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineJobVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineJobService extends IService<DefineJob>, MyBaseMysqlService<DefineJob, DefineJobMapper, DefineJobVo> {


    /**
     * 分页查询 职务定义 列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineJobVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<DefineJob> paginationBean,
                                                       List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 职务定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineJobVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<DefineJobDto> paginationBean,
                                                    List<AntdvSortBean> sortBeans);

    /**
     * 职务账号-新增
     * @param loginUser   当前登录用户
     * @param defineJobVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, DefineJobVo defineJobVo) throws Exception;

    /**
     * 职务账号-更新
     * @param loginUser   当前登录用户
     * @param defineJobVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, DefineJobVo defineJobVo) throws Exception;

    /**
     * 职务账号-删除
     * @param loginUser 当前登录用户
     * @param delIds    要删除的职务id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception;

    /**
     * 职务账号-删除
     * @param loginUser 当前登录用户
     * @param delId     要删除的职务id
     * @return
     * @throws Exception
     */
    Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception;


}
