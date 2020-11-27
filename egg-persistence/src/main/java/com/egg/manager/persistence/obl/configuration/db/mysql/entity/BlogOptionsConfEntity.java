package com.egg.manager.persistence.obl.configuration.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("obl_blog_options_conf")
@Deprecated
public class BlogOptionsConfEntity extends Model<BlogOptionsConfEntity> {
    private static final long serialVersionUID = -776792869602511933L;

    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     *
     */
    @TableField("site_title")
    private String  siteTitle;
    /**
     *
     */
    @TableField("site_descrption")
    private String  siteDescrption;
    /**
     *
     */
    @TableField("meta_descrption")
    private String  metaDescrption;
    /**
     *
     */
    @TableField("meta_keyword")
    private String  metaKeyword;
    /**
     *
     */
    @TableField("aboutsite_avatar")
    private String  aboutsiteAvatar;
    /**
     *
     */
    @TableField("aboutsite_title")
    private String  aboutsiteTitle;
    /**
     *
     */
    @TableField("aboutsite_content")
    private String  aboutsiteContent;
    /**
     * 微信联系方式
     */
    @TableField("aboutsite_wechat")
    private String  aboutsiteWechat;
    /**
     * qq联系方式
     */
    @TableField("aboutsite_qq")
    private String  aboutsiteQq;
    /**
     * github地址
     */
    @TableField("aboutsite_github")
    private String  aboutsiteGithub;
    /**
     * 微博地址
     */
    @TableField("aboutsite_weibo")
    private String  aboutsiteWeibo;

    /**
     * 备注
     */
    @TableField(FieldConst.COL_REMARK)
    private String remark;
    /**
     * 状态
     */
    @TableField(value = FieldConst.COL_STATE, fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = FieldConst.COL_CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = FieldConst.COL_UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = FieldConst.COL_CREATE_USER_ID)
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = FieldConst.COL_LAST_MODIFYER_ID)
    private String lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = FieldConst.COL_VERSION)
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = FieldConst.COL_IS_DELETED)
    private short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField(value = "deleted_time")
    private Date deletedTime;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

}