package com.egg.manager.entity.log;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.*;

import java.io.Serializable;

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
@TableName("em_operation_log")
public class OperationLog extends Model<OperationLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fid")
    private String fid;

    @TableField("user_account_id")
    private String userAccountId;       //登录的用户id
    @TableField("class_name")
    private String className;   //类名称
    @TableField("method_name")
    private String methodName;  //方法名称

    /**
     * @OperLog
     */
    private String action;  //操作
    @TableField("model_name")
    private String modelName;   //模块名称
    @TableField("log_description")
    private String logDescription;  //日志描述

    @TableField("action_args")
    private String actionArgs;  //方法参数->json


    @TableField("is_success")
    private Integer isSuccess;  //是否成功 1:成功 2异常
    private String message; //异常堆栈信息
    @TableField(value="ip_addr")
    private String ipAddr ;

    private String result ;     //返回结果-json
    private String exception ;     //返回结果-json


    @TableField("signature_long")
    private String signatureLong ;   //请求的方法完整内容
    @TableField("aspect_kind")
    private String aspectKind ;
    @TableField("aspect_notify_type")
    private String aspectNotifyType ;   //aop通知方式
    /**
     * request
     */
    @TableField("token_bean")
    private String tokenBean;   //发起请求的token ->json
    private String headers ;    //发起请求的header ->json
    @TableField("request_uri")
    private String requestUri ;     //发起请求的uri
    @TableField("request_url")
    private String requestUrl ;     //发起请求的路径

    /**
     * method
     */
    @TableField("return_type_name")
    private String returnTypeName;  //返回值类型
    @TableField("declared_annotations")
    private String declaredAnnotations ;    //定义的注解->json


    private String remark ;
    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;
    @TableField(value = "create_user_id")
    private String createUserId ;
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;







    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


    public OperationLog(String fid) {
        this.fid = fid;
    }
}
