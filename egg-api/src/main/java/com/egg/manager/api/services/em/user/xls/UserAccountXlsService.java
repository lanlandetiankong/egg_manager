package com.egg.manager.api.services.em.user.xls;

import com.egg.manager.api.exchange.services.xls.MyXlsBaseService;
import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserAccountXlsService extends MyXlsBaseService {

    /**
     * 根据模板导出已选数据文件
     * @param loginUser      当前登录用户
     * @param response
     * @param defineMenu
     * @param fileUploadBean
     * @param checkIds
     * @throws Exception
     */
    void dealCheckExportSingleWithTemplate2Web(UserAccount loginUser, HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean, Long[] checkIds) throws Exception;

    /**
     * 导出所有 用户账号 到excel
     * @param loginUser      当前登录用户
     * @param response
     * @param defineMenu
     * @param fileUploadBean
     * @throws Exception
     */
    void dealAllExportSingleWithTemplate2Web(UserAccount loginUser, HttpServletResponse response, DefineMenu defineMenu, AntdFileUploadBean fileUploadBean) throws Exception;
}
