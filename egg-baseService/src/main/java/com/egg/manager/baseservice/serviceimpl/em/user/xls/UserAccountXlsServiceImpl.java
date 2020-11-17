package com.egg.manager.baseservice.serviceimpl.em.user.xls;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.exchange.servicesimpl.xls.MyXlsBaseServiceImpl;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.api.services.em.user.xls.UserAccountXlsService;
import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.props.upload.UploadProps;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.excel.export.user.UserAccountXlsOutModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Service(interfaceClass = UserAccountXlsService.class)
public class UserAccountXlsServiceImpl extends MyXlsBaseServiceImpl implements UserAccountXlsService {
    @Autowired
    private UploadProps uploadProps;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public void dealCheckExportSingleWithTemplate2Web(CurrentLoginUserInfo loginUserInfo, HttpServletResponse response, DefineMenuEntity defineMenuEntity, AntdFileUploadBean fileUploadBean, String[] checkIds) throws Exception {
        if (checkIds == null || checkIds.length == 0) {
            throw new BusinessException("请选择至少一条要导出的数据！");
        }
        //导出文件名
        String excelFileName = defineMenuEntity.getMenuName() + "选择导出表";
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation();
        List<UserAccountXlsOutModel> userAccountList = userAccountService.dealGetExportXlsModelList(loginUserInfo, checkIds, new QueryWrapper<>());
        this.dealSingleFillToExport2Web(response, excelPath, excelFileName, UserAccountXlsOutModel.class, userAccountList);
    }


    @Override
    public void dealAllExportSingleWithTemplate2Web(CurrentLoginUserInfo loginUserInfo, HttpServletResponse response, DefineMenuEntity defineMenuEntity, AntdFileUploadBean fileUploadBean) throws Exception {
        //导出文件名
        String excelFileName = defineMenuEntity.getMenuName() + "选择导出表";
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation();
        List<UserAccountXlsOutModel> userAccountList = userAccountService.dealGetExportXlsModelList(loginUserInfo, null, new QueryWrapper<>());
        this.dealSingleFillToExport2Web(response, excelPath, excelFileName, UserAccountXlsOutModel.class, userAccountList);
    }
}
