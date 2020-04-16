package com.egg.manager.persistence.excel.introduce.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/16
 * \* Time: 21:34
 * \* Description:
 * \
 */
@Data
public class UserAccountXlsInModel {
    @ExcelIgnore
    private String fid ;


    @ExcelIgnore
    private String userName ;
    @ExcelProperty(index = 0)
    private String account ;
    @ExcelProperty(index = 1)
    private String nickName ;
    @ExcelIgnore
    private String avatarUrl ;
    @ExcelIgnore
    private String password ;

    @ExcelProperty(index = 2)
    private String phone ;
    @ExcelProperty(index = 3)
    private String email ;
    @ExcelProperty(index = 4)
    private String sexStr ;
    @ExcelIgnore
    private String userTypeStr ;

    @ExcelProperty(index = 5)
    private String remark ;
    @ExcelIgnore
    private Integer state ;
    @ExcelIgnore
    private String lockedStr ;

    @ExcelIgnore
    private Date createTime ;
    @ExcelIgnore
    private Date updateTime ;
    @ExcelIgnore
    private String createUserId ;
    @ExcelIgnore
    private String lastModifyerId;

}
