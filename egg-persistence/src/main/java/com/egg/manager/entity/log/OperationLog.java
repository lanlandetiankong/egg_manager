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

    /**
     * 主键
     */
    @TableId(value = "fid", type = IdType.AUTO)
    private Integer fid;
    /**
     * 日志描述
     */
    @TableField("log_description")
    private String logDescription;
    /**
     * 方法参数
     */
    @TableField("action_args")
    private String actionArgs;
    /**
     * 用户主键
     */
    @TableField("user_account_id")
    private String userAccountId;
    /**
     * 类名称
     */
    @TableField("class_name")
    private String className;
    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;
    /**
     * 模块名称
     */
    @TableField("model_name")
    private String modelName;
    /**
     * 操作
     */
    private String action;
    /**
     * 是否成功 1:成功 2异常
     */
    @TableField("is_success")
    private Integer isSuccess;
    /**
     * 异常堆栈信息
     */
    private String message;
    @TableField(value="ip_addr")
    private String ipAddr ;
    private String type;
    @Version
    private Integer version ;
    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;

    @TableField(value = "create_user")
    private String createUser ;
    @TableField(value = "last_modifyer")
    private String lastModifyer;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}
