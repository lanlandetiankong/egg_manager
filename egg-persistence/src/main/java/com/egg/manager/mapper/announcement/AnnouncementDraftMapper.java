package com.egg.manager.mapper.announcement;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.announcement.AnnouncementDraft;
import com.egg.manager.entity.user.UserAccount;
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


    //批量 伪删除
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;

    /**
     * 批量 发布 公告草稿
     * (只是修改 AnnouncementDraft 的值，具体发布到Announcement 需要自行另外操作)
     * @param delIds
     * @param loginUser
     * @return
     */
    int batchPublishByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;
}
