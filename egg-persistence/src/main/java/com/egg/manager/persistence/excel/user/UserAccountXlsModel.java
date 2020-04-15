package com.egg.manager.persistence.excel.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.egg.manager.common.base.enums.base.UserSexEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserAccountXlsModel extends BaseRowModel implements Serializable{
    @ExcelIgnore
    private String fid ;

    @ExcelProperty("用户名")
    private String userName ;
    @ExcelProperty("账号")
    private String account ;
    @ExcelIgnore
    private String nickName ;
    @ExcelIgnore
    private String avatarUrl ;
    @ExcelIgnore
    private String password ;
    @ExcelProperty("手机号")
    private String phone ;
    @ExcelProperty("邮箱")
    private String email ;
    @ExcelProperty("性别")
    private String sexStr ;
    @ExcelIgnore
    private String userTypeStr ;

    @ExcelIgnore
    private String remark ;
    @ExcelIgnore
    private Integer state ;
    @ExcelIgnore
    private String lockedStr ;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分")
    @ExcelProperty("创建时间")
    private Date createTime ;
    @ExcelIgnore
    private Date updateTime ;
    @ExcelIgnore
    private String createUserId ;
    @ExcelIgnore
    private String lastModifyerId;



    public  static UserAccountXlsModel voToXlsModel(UserAccount vo,UserAccountXlsModel userAccountXlsModel){
        userAccountXlsModel = userAccountXlsModel != null ? userAccountXlsModel : new UserAccountXlsModel() ;
        userAccountXlsModel.setFid(vo.getFid());
        userAccountXlsModel.setUserName(vo.getUserName());
        userAccountXlsModel.setAccount(vo.getAccount());
        userAccountXlsModel.setNickName(vo.getNickName());
        userAccountXlsModel.setAvatarUrl(vo.getAvatarUrl());
        userAccountXlsModel.setPassword(vo.getPassword());
        userAccountXlsModel.setPhone(vo.getPhone());
        userAccountXlsModel.setEmail(vo.getEmail());
        //性别
        userAccountXlsModel.setSexStr(UserSexEnum.dealGetNameByVal(vo.getSex()));
        //用户类型
        userAccountXlsModel.setUserTypeStr(UserAccountBaseTypeEnum.doGetEnumNameByValue(vo.getUserType(),""));
        userAccountXlsModel.setRemark(vo.getRemark());
        userAccountXlsModel.setState(vo.getState());
        userAccountXlsModel.setLockedStr(UserAccountStateEnum.doGetEnumInfoByValue(vo.getLocked()));
        userAccountXlsModel.setCreateTime(vo.getCreateTime());
        userAccountXlsModel.setUpdateTime(vo.getUpdateTime());
        userAccountXlsModel.setCreateUserId(vo.getCreateUserId());
        userAccountXlsModel.setLastModifyerId(vo.getLastModifyerId());
        //userAccountXlsModel.setCellStyleMap();
        return userAccountXlsModel;
    }

    public static List<UserAccountXlsModel> voListToXlsModels(List<UserAccount> voList){
        List<UserAccountXlsModel> list = new ArrayList<>();
        for (UserAccount vo : voList){
            list.add(voToXlsModel(vo,null));
        }
        return list ;
    }

}
