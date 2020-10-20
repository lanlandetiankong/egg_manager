package com.egg.manager.baseservice.services.excel.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.api.services.excel.service.user.UserAccountXlsService;
import com.egg.manager.baseservice.services.excel.serviceimpl.common.MyXlsBaseServiceImpl;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.excel.export.user.UserAccountXlsOutModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2020/4/15
 * \* Time: 20:58
 * \* Description:
 * \
 */
@Slf4j
@Service(interfaceClass = UserAccountXlsService.class)
public class UserAccountXlsServiceImpl extends MyXlsBaseServiceImpl implements UserAccountXlsService {
    @Autowired
    private UploadProps uploadProps;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public void dealCheckExportSingleWithTemplate2Web(UserAccount loginUser, HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean, String[] checkIds) throws Exception {
        if (checkIds == null || checkIds.length == 0) {
            throw new BusinessException("请选择至少一条要导出的数据！");
        }
        //导出文件名
        String excelFileName = defineMenu.getMenuName() + "选择导出表";
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation();
        List<UserAccountXlsOutModel> userAccountList = userAccountService.dealGetExportXlsModelList(loginUser, checkIds, new QueryWrapper<>());
        this.dealSingleFillToExport2Web(response, excelPath, excelFileName, UserAccountXlsOutModel.class, userAccountList);
    }


    @Override
    public void dealAllExportSingleWithTemplate2Web(UserAccount loginUser, HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean) throws Exception {
        //导出文件名
        String excelFileName = defineMenu.getMenuName() + "选择导出表";
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation();
        List<UserAccountXlsOutModel> userAccountList = userAccountService.dealGetExportXlsModelList(loginUser, null, new QueryWrapper<>());
        this.dealSingleFillToExport2Web(response, excelPath, excelFileName, UserAccountXlsOutModel.class, userAccountList);
    }
}
