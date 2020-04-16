package com.egg.manager.service.excel.serviceimpl.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.persistence.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.service.excel.service.user.UserAccountXlsService;
import com.egg.manager.service.excel.serviceimpl.common.MyXlsBaseServiceImpl;
import com.egg.manager.service.service.user.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/15
 * \* Time: 20:58
 * \* Description:
 * \
 */
@Service
public class UserAccountXlsServiceImpl extends MyXlsBaseServiceImpl implements UserAccountXlsService {
    @Autowired
    private UploadProps uploadProps ;

    @Autowired
    private UserAccountService userAccountService ;

    @Override
    public void dealCheckExportSingleWithTemplate2Web(HttpServletResponse response,DefineMenu defineMenu, AntdFileUploadBean fileUploadBean,String[] checkIds) throws Exception{
        if(checkIds == null || checkIds.length == 0){
            throw new BusinessException("请选择至少一条要导出的数据！");
        }
        String excelFileName = defineMenu.getMenuName() + "选择导出表";  //导出文件名
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation() ;
        List<UserAccountXlsOutModel> userAccountList =  userAccountService.dealGetExportXlsModelList(checkIds,new EntityWrapper<>());
        this.dealSingleFillToExport2Web(response,excelPath, excelFileName,UserAccountXlsOutModel.class, userAccountList);
    }


    @Override
    public void dealAllExportSingleWithTemplate2Web(HttpServletResponse response,DefineMenu defineMenu, AntdFileUploadBean fileUploadBean) throws Exception{
        String excelFileName = defineMenu.getMenuName() + "选择导出表";  //导出文件名
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation() ;
        List<UserAccountXlsOutModel> userAccountList =  userAccountService.dealGetExportXlsModelList(null,new EntityWrapper<>());
        this.dealSingleFillToExport2Web(response,excelPath, excelFileName,UserAccountXlsOutModel.class, userAccountList);
    }
}
