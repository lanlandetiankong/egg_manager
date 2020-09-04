package com.egg.manager.persistence.db.mysql.mapper.announcement;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 定义的职务 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface AnnouncementDraftMapper extends BaseMapper<AnnouncementDraft> {


    /**
     * [分页搜索查询] - 公告草稿
     *
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<AnnouncementDraftDto> selectQueryPage(Pagination page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

    /**
     * 批量 伪删除
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);


    /**
     * 批量 发布 公告草稿
     * (只是修改 AnnouncementDraft 的值，具体发布到Announcement 需要自行另外操作)
     *
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchPublishByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser);
}
