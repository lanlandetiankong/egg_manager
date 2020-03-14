package com.egg.manager.dto.log;

import com.egg.manager.entity.user.UserAccount;
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
public class OperationLogDto {
    private String fid;

    private String userAccountId;       //登录的用户id
    private String className;   //类名称
    private String methodName;  //方法名称

    /**
     * @OperLog
     */
    private String action;  //操作
    private String modelName;   //模块名称
    private String logDescription;  //日志描述

    private String actionArgs;  //方法参数->json


    private Integer isSuccess;  //是否成功 1:成功 2异常
    private String message; //异常堆栈信息
    private String ipAddr ;

    private String result ;     //返回结果-json
    private String exception ;     //返回结果-json


    private String signatureLong ;   //请求的方法完整内容
    private String aspectKind ;
    private String aspectNotifyType ;   //aop通知方式
    /**
     * request
     */
    private String tokenBean;   //发起请求的token ->json
    private String headers ;    //发起请求的header ->json
    private String requestUri ;     //发起请求的uri
    private String requestUrl ;     //发起请求的路径

    /**
     * method
     */
    private String returnTypeName;  //返回值类型
    private String declaredAnnotations ;    //定义的注解->json


    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;

}
