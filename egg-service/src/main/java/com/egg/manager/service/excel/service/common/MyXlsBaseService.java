package com.egg.manager.service.excel.service.common;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.entity.define.DefineMenu;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/15
 * \* Time: 21:00
 * \* Description:
 * \
 */
public interface MyXlsBaseService {

    /**
     * 判断 该菜单 是否可以导出excel
     * @param defineMenu
     * @return
     * @throws Exception
     */
    AntdFileUploadBean dealVerifyMenuExportAble(DefineMenu defineMenu) throws Exception;

    /**
     * web导出 单个sheet,返回blob到前端
     * @param response
     * @param templatePath 模板位置
     * @param outFileName
     * @param clazz
     * @param data
     * @param <T>
     * @throws Exception
     */
    <T> void dealSingleFillToExport2Web(HttpServletResponse response, String templatePath, String outFileName, Class<T> clazz, List<T> data) throws Exception;
}
