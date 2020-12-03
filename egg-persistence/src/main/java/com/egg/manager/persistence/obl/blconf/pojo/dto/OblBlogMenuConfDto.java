package com.egg.manager.persistence.obl.blconf.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 博客菜单定义表-Dto
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblBlogMenuConfDto extends MyBaseMysqlDto {
    /**
     * 菜单名
     */
    private String name;
    /**
     * 级别
     */
    private Short level;
    /**
     * 路径跳转方式
     */
    private Short urlJumpType;
    /**
     * 图标名称
     */
    private String iconName;
    /**
     * 路由跳转
     */
    private String routerUrl;
    /**
     * 外部跳转路径
     */
    private String hrefUrl;
    /**
     * 标签名
     */
    private String label;

}