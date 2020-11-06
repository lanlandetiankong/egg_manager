package com.egg.manager.persistence.em.user.pojo.excel.export.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.egg.manager.persistence.em.user.pojo.excel.MyBaseXls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountXlsOutModel extends MyBaseXls {
    @ExcelIgnore
    private String fid;
    /**
     * 用户名
     */
    @ExcelProperty("用户名")
    private String userName;
    /**
     * 账号
     */
    @ExcelProperty("账号")
    private String account;
    /**
     * 昵称
     */
    @ExcelIgnore
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
    @ExcelProperty("手机号")
    private String phone;
    /**
     * 邮箱
     */
    @ExcelProperty("邮箱")
    private String email;
    /**
     * 性别_名称
     */
    @ExcelProperty("性别")
    private String sexStr;
    /**
     * 用户类型_名称
     */
    @ExcelIgnore
    private String userTypeStr;
    /**
     * 备注
     */
    @ExcelIgnore
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
    @DateTimeFormat("yyyy年MM月dd日HH时mm分")
    @ExcelProperty("创建时间")
    private Date createTime;
    /**
     * 最后更新时间
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分")
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


}
