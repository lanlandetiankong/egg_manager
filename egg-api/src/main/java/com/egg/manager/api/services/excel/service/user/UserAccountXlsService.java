package com.egg.manager.api.services.excel.service.user;

import com.egg.manager.api.services.excel.service.common.MyXlsBaseService;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

import javax.servlet.http.HttpServletResponse;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2020/4/15
 * \* Time: 20:58
 * \* Description:
 * \
 */

public interface UserAccountXlsService extends MyXlsBaseService {

    /**
     * 根据模板导出已选数据文件
     * @param loginUser 当前登录用户
     * @param response
     * @param defineMenu
     * @param fileUploadBean
     * @param checkIds
     * @throws Exception
     */
    void dealCheckExportSingleWithTemplate2Web(UserAccount loginUser, HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean, String[] checkIds) throws Exception;

    /**
     * 导出所有 用户账号 到excel
     * @param loginUser 当前登录用户
     * @param response
     * @param defineMenu
     * @param fileUploadBean
     * @throws Exception
     */
    void dealAllExportSingleWithTemplate2Web(UserAccount loginUser,HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean) throws Exception;
}
