package com.egg.manager.persistence.em.define.pojo.dto;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.expand.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class DefineMenuDto extends MyBaseMysqlDto {
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 路径跳转方式
     * @see DefineMenuUrlJumpTypeEnum
     */
    private Integer urlJumpType;
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
    /**
     * 层级
     */
    private Integer level;
    /**
     * 排序值
     */
    private Integer orderNum;
    /**
     * excel配置信息
     */
    private String excelModelConf;

    /**
     * url跳转类型_名称
     */
    private String urlJumpTypeStr;

    /**
     * 当前菜单已上传的Excel模板文件bean
     */
    @Builder.Default
    private List<AntdFileUploadBean> uploadExcelBeanList = new ArrayList<>();

    /**
     * 上级菜单-dto
     */
    private DefineMenuDto parentMenuDto;


}
