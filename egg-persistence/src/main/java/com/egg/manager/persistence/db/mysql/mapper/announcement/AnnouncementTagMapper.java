package com.egg.manager.persistence.db.mysql.mapper.announcement;

import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementTagDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface AnnouncementTagMapper extends MyEggMapper<AnnouncementTag> {

    /**
     * [分页搜索查询] - 公告标签
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<AnnouncementTagDto> selectQueryPage(Page<AnnouncementTagDto> page, @Param("queryFieldList") List<QueryFormFieldBean> queryFieldBeanList, @Param("sortFieldList") List<AntdvSortBean> sortBeans);

}
