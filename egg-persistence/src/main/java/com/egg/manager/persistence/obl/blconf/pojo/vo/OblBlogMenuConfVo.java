package com.egg.manager.persistence.obl.blconf.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 博客菜单定义表-Vo
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblBlogMenuConfVo extends MyBaseMysqlVo {
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