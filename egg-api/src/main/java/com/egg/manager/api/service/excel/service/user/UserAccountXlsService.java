package com.egg.manager.api.service.excel.service.user;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.api.service.excel.service.common.MyXlsBaseService;

import javax.servlet.http.HttpServletResponse;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/15
 * \* Time: 20:58
 * \* Description:
 * \
 */

public interface UserAccountXlsService extends MyXlsBaseService {

    void dealCheckExportSingleWithTemplate2Web( HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean,String[] checkIds) throws Exception;

    /**
     * 导出所有 用户账号 到excel
     * @param response
     * @param defineMenu
     * @param fileUploadBean
     * @throws Exception
     */
    void dealAllExportSingleWithTemplate2Web(HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean) throws Exception;
}
