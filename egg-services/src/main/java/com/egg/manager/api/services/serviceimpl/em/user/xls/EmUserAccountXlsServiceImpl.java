package com.egg.manager.api.services.serviceimpl.em.user.xls;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.facade.api.exchange.servicesimpl.xls.MyXlsBaseServiceImpl;
import com.egg.manager.facade.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.facade.api.services.em.user.xls.EmUserAccountXlsService;
import com.egg.manager.facade.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.commons.base.props.upload.UploadProps;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.facade.persistence.em.user.pojo.excel.export.user.EmUserAccountXlsOutModel;
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
@Service(interfaceClass = EmUserAccountXlsService.class)
public class EmUserAccountXlsServiceImpl extends MyXlsBaseServiceImpl implements EmUserAccountXlsService {
    @Autowired
    private UploadProps uploadProps;

    @Autowired
    private EmUserAccountService emUserAccountService;

    @Override
    public void dealCheckExportSingleWithTemplate2Web(CurrentLoginEmUserInfo loginUserInfo, HttpServletResponse response, EmDefineMenuEntity emDefineMenuEntity, AntdFileUploadBean fileUploadBean, String[] checkIds) throws Exception {
        Assert.notEmpty(checkIds, BaseRstMsgConstant.WarningMsg.selectAtLeastOneExportData());
        //导出文件名
        String excelFileName = BaseRstMsgConstant.InfoMsg.excelExportData(emDefineMenuEntity.getMenuName());
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation();
        List<EmUserAccountXlsOutModel> userAccountList = emUserAccountService.dealGetExportXlsModelList(loginUserInfo, checkIds, new QueryWrapper<>());
        this.dealSingleFillToExport2Web(response, excelPath, excelFileName, EmUserAccountXlsOutModel.class, userAccountList);
    }


    @Override
    public void dealAllExportSingleWithTemplate2Web(CurrentLoginEmUserInfo loginUserInfo, HttpServletResponse response, EmDefineMenuEntity emDefineMenuEntity, AntdFileUploadBean fileUploadBean) throws Exception {
        //导出文件名
        String excelFileName = BaseRstMsgConstant.InfoMsg.excelExportData(emDefineMenuEntity.getMenuName());
        String excelPath = uploadProps.getLocationPrefix() + fileUploadBean.getUrlLocation();
        List<EmUserAccountXlsOutModel> userAccountList = emUserAccountService.dealGetExportXlsModelList(loginUserInfo, null, new QueryWrapper<>());
        this.dealSingleFillToExport2Web(response, excelPath, excelFileName, EmUserAccountXlsOutModel.class, userAccountList);
    }
}
