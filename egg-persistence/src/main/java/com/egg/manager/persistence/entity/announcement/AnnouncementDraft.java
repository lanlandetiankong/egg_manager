package com.egg.manager.persistence.entity.announcement;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_announcement_draft")
public class AnnouncementDraft extends Model<AnnouncementDraft> {

    @TableId
    private String fid ;

    private String title ;
    @TableField("key_word")
    private String keyWord ;    //关键字
    @TableField("publish_department")
    private String publishDepartment ;  //发布部门
    private String content ;
    @TableField("tag_ids")
    private String tagIds ; //公告标签 集合
    private String accessory ;      //附件
    @TableField("is_published")
    private short isPublished ;      //是否已提交

    private String remark;
    private Short state ;
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

}
