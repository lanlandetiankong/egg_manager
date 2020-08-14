package com.egg.manager.persistence.excel.export.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountXlsOutModel extends BaseRowModel implements Serializable {
    @ExcelIgnore
    private String fid;

    @ExcelProperty("用户名")
    private String userName;
    @ExcelProperty("账号")
    private String account;
    @ExcelIgnore
    private String nickName;
    @ExcelIgnore
    private String avatarUrl;
    @ExcelIgnore
    private String password;
    @ExcelProperty("手机号")
    private String phone;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("性别")
    private String sexStr;
    @ExcelIgnore
    private String userTypeStr;

    @ExcelIgnore
    private String remark;
    @ExcelIgnore
    private Short state;
    @ExcelIgnore
    private String lockedStr;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分")
    @ExcelProperty("创建时间")
    private Date createTime;
    @ExcelIgnore
    private Date updateTime;
    @ExcelIgnore
    private String createUserId;
    @ExcelIgnore
    private String lastModifyerId;


}
