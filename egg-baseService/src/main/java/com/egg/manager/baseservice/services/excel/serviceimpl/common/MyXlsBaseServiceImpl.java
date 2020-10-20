package com.egg.manager.baseservice.services.excel.serviceimpl.common;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.egg.manager.api.services.excel.service.common.MyXlsBaseService;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2020/4/15
 * \* Time: 21:00
 * \* Description:
 * \
 */
@Slf4j
@Service(interfaceClass = MyXlsBaseService.class)
public class MyXlsBaseServiceImpl implements MyXlsBaseService {


    @Override
    public AntdFileUploadBean dealVerifyMenuExportAble(DefineMenu defineMenu) throws Exception {
        AntdFileUploadBean fileUploadBean = AntdFileUploadBean.dealJsonStrToBean(defineMenu.getExcelModelConf());
        if (fileUploadBean == null) {
            throw new BusinessException("当前菜单定义未上传Excel模板，请先上传后再使用导出Excel功能！");
        }
        String relaPath = fileUploadBean.getUrlLocation();
        if (StringUtils.isBlank(relaPath)) {
            throw new BusinessException("当前菜单定义上传Excel模板信息有误！");
        }
        return fileUploadBean;
    }

    @Override
    public <T> void dealSingleFillToExport2Web(HttpServletResponse response, String templatePath, String outFileName, Class<T> clazz, List<T> data) throws Exception {
        //导出的完整 文件名
        String outFileFullName = URLEncoder.encode(outFileName + ExcelTypeEnum.XLSX.getValue(), "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        response.setHeader("Content-disposition", "attachment;filename=" + outFileFullName);
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        EasyExcel.write(response.getOutputStream(), clazz).withTemplate(templatePath).sheet().doFill(data);
    }

}
