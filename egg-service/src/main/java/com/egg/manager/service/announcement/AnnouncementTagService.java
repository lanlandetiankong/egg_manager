package com.egg.manager.service.announcement;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.announcement.AnnouncementTagVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;

import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
public interface AnnouncementTagService extends IService<AnnouncementTag> {


    /**
     * 分页查询 公告标签 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetAnnouncementTagPages(MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                            List<AntdvSortBean> sortBeans);
    /**
     * 分页查询 公告标签 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetAnnouncementTagDtoPages(MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                     List<AntdvSortBean> sortBeans);

    /***
     * 查询可用的 公告标签 并转为map
     * @return
     */
    Map<String,AnnouncementTag> dealGetAllAnnouncementTagToMap();
    /**
     * 公告标签-新增
     * @param announcementTagVo
     * @throws Exception
     */
    Integer dealAddAnnouncementTag(AnnouncementTagVo announcementTagVo,UserAccount loginUser) throws Exception ;


    /**
     * 公告标签-更新
     * @param announcementTagVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateAnnouncementTag(AnnouncementTagVo announcementTagVo,UserAccount loginUser,boolean updateAll) throws Exception ;



    /**
     * 公告标签-删除
     * @param delIds 要删除的公告标签id 集合
     * @throws Exception
     */
    Integer dealDelAnnouncementTagByArr(String[] delIds,UserAccount loginUser) throws Exception ;


    /**
     * 公告标签-删除
     * @param delId 要删除的公告标签id
     * @throws Exception
     */
    Integer dealDelAnnouncementTag(String delId,UserAccount loginUser) throws Exception;


    /**
     * 取得的结果 转为 枚举类型
     * @param result
     */
    void dealResultListSetToEntitySelect(MyCommonResult result);
}
