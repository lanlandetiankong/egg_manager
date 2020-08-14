package com.egg.manager.persistence.vo.define;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineMenuVo {
    private String fid ;
    private String parentId ;
    private DefineMenuVo parentMenu ;
    private String menuName ;
    private String iconName ;
    private String routerUrl ;
    private String hrefUrl ;
    private Integer urlJumpType;
    private String urlJumpTypeStr;
    private String label ;
    private Integer level ;
    private Integer orderNum ;
    private String excelModelConf ;


    private String remark ;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;

    private List<AntdFileUploadBean> uploadExcelBeanList = new ArrayList<>();  //当前菜单已上传的Excel模板文件bean



    public void dealAddAntdFileUploadBean(AntdFileUploadBean bean){
        this.uploadExcelBeanList = (this.uploadExcelBeanList != null) ? this.uploadExcelBeanList : new ArrayList<>() ;
        this.uploadExcelBeanList.add(bean);
    }



}
