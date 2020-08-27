package com.egg.manager.api.service.service.define;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.pojo.vo.define.DefineJobVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineJobService extends IService<DefineJob> {


    /**
     * 分页查询 职务定义 列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineJobPages(MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                               List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 职务定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineJobDtoPages(MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                               List<AntdvSortBean> sortBeans);

    /**
     * 职务账号-新增
     * @param defineJobVo
     * @throws Exception
     */
    Integer dealAddDefineJob(DefineJobVo defineJobVo,UserAccount loginUser) throws Exception ;

    /**
     * 职务账号-更新
     * @param defineJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefineJob(DefineJobVo defineJobVo,UserAccount loginUser, boolean updateAll) throws Exception ;

    /**
     * 职务账号-删除
     * @param delIds 要删除的职务id 集合
     * @throws Exception
     */
    Integer dealDelDefineJobByArr(String[] delIds,UserAccount loginUser) throws Exception ;

    /**
     * 职务账号-删除
     * @param delId 要删除的职务id
     * @throws Exception
     */
    Integer dealDelDefineJob(String delId,UserAccount loginUser) throws Exception ;





}
