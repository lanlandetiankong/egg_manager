package com.egg.manager.mapper.announcement;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egg.manager.entity.announcement.Announcement;
import com.egg.manager.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AnnouncementMapper extends BaseMapper<Announcement> {

    //批量 伪删除
    int batchFakeDelByIds(@Param("delIds") List<String> delIds, @Param("loginUser") UserAccount loginUser) ;
}
