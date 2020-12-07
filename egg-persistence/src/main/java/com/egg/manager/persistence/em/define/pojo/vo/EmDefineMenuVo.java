package com.egg.manager.persistence.em.define.pojo.vo;

import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.em.define.domain.enums.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmDefineMenuVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 2615364203328397224L;
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
     * 上级菜单-com.egg.manager.persistence.obl.article.pojo.vo
     */
    private EmDefineMenuVo parentMenu;
    /**
     * url跳转类型_名称
     */
    private String urlJumpTypeStr;

    /**
     * 当前菜单已上传的Excel模板文件bean
     */
    private List<AntdFileUploadBean> uploadExcelBeanList = new ArrayList<>();


    public void dealAddAntdFileUploadBean(AntdFileUploadBean bean) {
        this.uploadExcelBeanList = (this.uploadExcelBeanList != null) ? this.uploadExcelBeanList : new ArrayList<>();
        this.uploadExcelBeanList.add(bean);
    }


}
