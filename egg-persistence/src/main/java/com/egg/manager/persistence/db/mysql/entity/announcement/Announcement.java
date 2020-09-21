package com.egg.manager.persistence.db.mysql.entity.announcement;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告-entity
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_announcement")
public class Announcement extends Model<Announcement> {

    @TableId
    private String fid;

    /**
     * 标题
     */
    private String title;
    /**
     * 关键字
     */
    @TableField("key_word")
    private String keyWord;
    /**
     * 发布部门
     */
    @TableField("publish_department")
    private String publishDepartment;
    /**
     * 内容
     */
    private String content;
    /**
     * 公告标签 集合
     */
    @TableField("tag_ids")
    private String tagIds;
    /**
     * 附件
     */
    private String accessory;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Short state;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = "create_user_id")
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

}
