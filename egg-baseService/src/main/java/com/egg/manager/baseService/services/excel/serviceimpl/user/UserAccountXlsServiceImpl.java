package com.egg.manager.baseService.services.excel.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.api.services.excel.service.user.UserAccountXlsService;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.pojo.common.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.baseService.services.excel.serviceimpl.common.MyXlsBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

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
@Service(interfaceClass = UserAccountXlsService.class)
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
