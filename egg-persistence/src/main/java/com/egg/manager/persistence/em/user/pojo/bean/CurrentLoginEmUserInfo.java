package com.egg.manager.persistence.em.user.pojo.bean;

import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @ClassName: CurrentLoginEmUserInfo
 * @Author: zhoucj
 * @Date: 2020/11/11 14:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentLoginEmUserInfo extends EmUserAccountEntity {

    private static final long serialVersionUID = -1948150822329375750L;
    private List<EmDefineGroupEntity> belongGroupList;

    private List<EmDefineJobEntity> belongJobList;

    public static CurrentLoginEmUserInfo transferFromEntity(EmUserAccountEntity entity) {
        CurrentLoginEmUserInfo currentLoginUserInfo = new CurrentLoginEmUserInfo();
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