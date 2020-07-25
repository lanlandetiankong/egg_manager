package com.egg.manager.persistence.vo.log;

import lombok.*;

import java.util.Date;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-13
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperationLogVo {

    private static final long serialVersionUID = 1L;

    private Integer fid;
    private String logDescription;
    private String actionArgs;
    private String userAccountId;
    private String className;
    private String methodName;
    private String modelName;

    private String action;
    private Integer isSuccess;
    private String message;
    private String ipAddr ;
    private String type;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;

}
