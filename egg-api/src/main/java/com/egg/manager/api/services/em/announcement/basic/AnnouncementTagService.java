package com.egg.manager.api.services.em.announcement.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementTagMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementTagDto;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementTagVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface AnnouncementTagService extends IService<AnnouncementTagEntity>, MyBaseMysqlService<AnnouncementTagEntity, AnnouncementTagMapper, AnnouncementTagVo> {


    /**
     * 分页查询 公告标签 列表
     * @param loginUserInfo          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<AnnouncementTagVo> dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTagEntity> paginationBean,
                                                             List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 公告标签 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<AnnouncementTagVo> dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTagDto> paginationBean,
                                                          List<AntdvSortBean> sortBeans);

    /***
     * 查询可用的 公告标签 并转为map
     * @return
     */
    Map<Long, AnnouncementTagEntity> dealGetAllToMap();

    /**
     * 公告标签-新增
     * @param loginUserInfo         当前登录用户
     * @param announcementTagVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, AnnouncementTagVo announcementTagVo) throws Exception;


    /**
     * 公告标签-更新
     * @param loginUserInfo         当前登录用户
     * @param announcementTagVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, AnnouncementTagVo announcementTagVo) throws Exception;

    /**
     * 取得的结果 转为 枚举类型
     * @param result
     * @return
     */
    MyCommonResult dealResultListToEnums(MyCommonResult result);
}
