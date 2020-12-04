package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucj
 * @description 文章查看记录-Dto
 * @date 2020-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleViewRecordDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -81516071414687793L;

    private String fid;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 查看人id
     */
    private String viewUserId;
    /**
     * 状态值
     */
    private Short state;
    /**
     * 备注
     */
    private String remark;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改人id
     */
    private String lastModifyerId;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否已删除?0:否1:是
     */
    private Short isDeleted;
    /**
     * 数据删除时间
     */
    private Date deletedTime;


}