package com.egg.manager.api.services.basic.announcement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementTagVo;

import java.util.List;
import java.util.Map;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
public interface AnnouncementTagService extends IService<AnnouncementTag>,MyBaseMysqlService<AnnouncementTag,AnnouncementTagMapper,AnnouncementTagVo> {


    /**
     * 分页查询 公告标签 列表
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<AnnouncementTagVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTag> paginationBean,
                                                                  List<AntdvSortBean> sortBeans);
    /**
     * 分页查询 公告标签 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<AnnouncementTagVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTagDto> paginationBean,
                                                                     List<AntdvSortBean> sortBeans);

    /***
     * 查询可用的 公告标签 并转为map
     * @return
     */
    Map<String,AnnouncementTag> dealGetAllToMap();
    /**
     * 公告标签-新增
     * @param loginUser 当前登录用户
     * @param announcementTagVo
     * @throws Exception
     * @return
     */
    Integer dealCreate(UserAccount loginUser,AnnouncementTagVo announcementTagVo) throws Exception ;


    /**
     * 公告标签-更新
     * @param loginUser 当前登录用户
     * @param announcementTagVo
     * @throws Exception
     * @return
     */
    Integer dealUpdate(UserAccount loginUser,AnnouncementTagVo announcementTagVo) throws Exception ;



    /**
     * 公告标签-删除
     * @param loginUser 当前登录用户
     * @param delIds 要删除的公告标签id 集合
     * @throws Exception
     * @return
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception ;


    /**
     * 公告标签-删除
     * @param loginUser 当前登录用户
     * @param delId 要删除的公告标签id
     * @throws Exception
     * @return
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception;


    /**
     * 取得的结果 转为 枚举类型
     * @param result
     * @return
     */
    MyCommonResult dealResultListToEnums(MyCommonResult result);
}
