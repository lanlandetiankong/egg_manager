package com.egg.manager.persistence.pojo.vo.mysql.define;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineMenuMysqlVo extends MyBaseMysqlVo {
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
     * 上级菜单-vo
     */
    private DefineMenuMysqlVo parentMenu;
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
