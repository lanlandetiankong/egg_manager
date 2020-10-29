package com.egg.manager.persistence.db.mysql.mapper.announcement;

import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface AnnouncementMapper extends MyEggMapper<Announcement> {

    /**
     * [分页搜索查询] - 公告
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<AnnouncementDto> selectQueryPage(Page<AnnouncementDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);
}
