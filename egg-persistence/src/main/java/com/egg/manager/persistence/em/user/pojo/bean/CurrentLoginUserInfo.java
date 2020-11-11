package com.egg.manager.persistence.em.user.pojo.bean;

import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroupEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @ClassName: CurrentLoginUserInfo
 * @Author: zhoucj
 * @Date: 2020/11/11 14:11
 */
@Data
public class CurrentLoginUserInfo extends UserAccountEntity {

    private List<DefineGroupEntity> belongGroupList ;

    private List<DefineJobEntity> belongJobList ;

    public static CurrentLoginUserInfo transferFromEntity(UserAccountEntity entity) {
        CurrentLoginUserInfo currentLoginUserInfo = new CurrentLoginUserInfo();
        currentLoginUserInfo.setFid(entity.getFid());
        currentLoginUserInfo.setUserName(entity.getUserName());
        currentLoginUserInfo.setAccount(entity.getAccount());
        currentLoginUserInfo.setNickName(entity.getNickName());
        currentLoginUserInfo.setAvatarUrl(entity.getAvatarUrl());
        currentLoginUserInfo.setPassword(entity.getPassword());
        currentLoginUserInfo.setPhone(entity.getPhone());
        currentLoginUserInfo.setEmail(entity.getEmail());
        currentLoginUserInfo.setSex(entity.getSex());
        currentLoginUserInfo.setUserType(entity.getUserType());
        currentLoginUserInfo.setUserTypeNum(entity.getUserTypeNum());
        currentLoginUserInfo.setLocked(entity.getLocked());
        currentLoginUserInfo.setRemark(entity.getRemark());
        currentLoginUserInfo.setState(entity.getState());
        currentLoginUserInfo.setCreateTime(entity.getCreateTime());
        currentLoginUserInfo.setUpdateTime(entity.getUpdateTime());
        currentLoginUserInfo.setCreateUserId(entity.getCreateUserId());
        currentLoginUserInfo.setLastModifyerId(entity.getLastModifyerId());
        currentLoginUserInfo.setVersion(entity.getVersion());
        currentLoginUserInfo.setIsDeleted(entity.getIsDeleted());
        currentLoginUserInfo.setDeletedTime(entity.getDeletedTime());
        //currentLoginUserInfo.setBelongGroup();
        //currentLoginUserInfo.setBelongJob();
        //currentLoginUserInfo.setBelongTenant();
        return currentLoginUserInfo;
    }



}