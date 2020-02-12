package com.egg.manager.service.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserJob;
import com.egg.manager.vo.user.UserJobVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.catalina.User;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserJobService extends IService<UserJob> {

    /**
     * 分页查询 用户职务列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    void dealGetUserJobPages(MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                List<AntdvSortBean> sortBeans);


    /**
     * 用户职务-新增
     * @param UserJobVo
     * @throws Exception
     */
    Integer dealAddUserJob(UserJobVo UserJobVo,UserAccount loginUser) throws Exception;

    /**
     * 用户职务-更新
     * @param UserJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateUserJob(UserJobVo UserJobVo,UserAccount loginUser,boolean updateAll) throws Exception;

    /**
     * 用户职务-删除
     * @param delIds 要删除的用户职务id 集合
     * @throws Exception
     */
    Integer dealDelUserJobByArr(String[] delIds, UserAccount loginUser) throws Exception ;

    /**
     * 用户职务-删除
     * @param delId 要删除的用户职务id
     * @throws Exception
     */
    Integer dealDelUserJob(String delId,UserAccount loginUser) throws Exception ;
}
