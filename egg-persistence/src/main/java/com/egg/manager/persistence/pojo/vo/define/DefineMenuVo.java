package com.egg.manager.persistence.pojo.vo.define;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.pojo.vo.MyBaseVo;
import com.egg.manager.persistence.pojo.vo.user.UserAccountVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineMenuVo extends MyBaseVo {
    private String fid;
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
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建用户id
     */
    private String createUserId;
    /**
     * 最后修改用户id
     */
    private String lastModifyerId;

    /**
     * 上级菜单-vo
     */
    private DefineMenuVo parentMenu;
    /**
     * url跳转类型_名称
     */
    private String urlJumpTypeStr;



    /**
     * 创建人-vo
     */
    private UserAccountVo createUser;
    /**
     * 最后更新人-vo
     */
    private UserAccountVo lastModifyer;
    /**
     * 上传的excel模板
     */
    private List<AntdFileUploadBean> uploadExcelBeanList = new ArrayList<>();  //当前菜单已上传的Excel模板文件bean


    public void dealAddAntdFileUploadBean(AntdFileUploadBean bean) {
        this.uploadExcelBeanList = (this.uploadExcelBeanList != null) ? this.uploadExcelBeanList : new ArrayList<>();
        this.uploadExcelBeanList.add(bean);
    }


}
