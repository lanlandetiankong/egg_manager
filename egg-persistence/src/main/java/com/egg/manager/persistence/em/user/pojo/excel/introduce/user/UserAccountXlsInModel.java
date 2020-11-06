package com.egg.manager.persistence.em.user.pojo.excel.introduce.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.egg.manager.persistence.em.user.pojo.excel.MyBaseXls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountXlsInModel extends MyBaseXls {
    @ExcelIgnore
    private String fid;

    /**
     * 用户名
     */
    @ExcelIgnore
    private String userName;
    /**
     * 账号
     */
    @ExcelProperty(index = 0)
    private String account;
    /**
     * 昵称
     */
    @ExcelProperty(index = 1)
    private String nickName;
    /**
     * 头像地址
     */
    @ExcelIgnore
    private String avatarUrl;
    /**
     * 密码
     */
    @ExcelIgnore
    private String password;
    /**
     * 手机号
     */
    @ExcelProperty(index = 2)
    private String phone;
    /**
     * 邮箱
     */
    @ExcelProperty(index = 3)
    private String email;
    /**
     * 性别_名称
     */
    @ExcelProperty(index = 4)
    private String sexStr;
    /**
     * 用户类型_名称
     */
    @ExcelIgnore
    private String userTypeStr;
    /**
     * 备注
     */
    @ExcelProperty(index = 5)
    private String remark;
    /**
     * 状态
     */
    @ExcelIgnore
    private Short state;
    /**
     * 是否锁定_名称
     */
    @ExcelIgnore
    private String lockedStr;
    /**
     * 创建时间
     */
    @ExcelIgnore
    private Date createTime;
    /**
     * 最后更新时间
     */
    @ExcelIgnore
    private Date updateTime;
    /**
     * 创建人_id
     */
    @ExcelIgnore
    private String createUserId;
    /**
     * 最后修改人_id
     */
    @ExcelIgnore
    private String lastModifyerId;
    /**
     * 删除时间
     */
    @ExcelIgnore
    private short isDeleted;
    /**
     * 数据删除时间
     */
    @ExcelIgnore
    private Date deletedTime;

}
