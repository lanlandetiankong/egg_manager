package com.egg.manager.api.services.em.user.xls;

import com.egg.manager.api.exchange.services.xls.MyXlsBaseService;
import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserAccountXlsService extends MyXlsBaseService {

    /**
     * 根据模板导出已选数据文件
     * @param loginUserInfo    当前登录用户
     * @param response
     * @param emDefineMenuEntity
     * @param fileUploadBean
     * @param checkIds
     * @throws Exception
     */
    void dealCheckExportSingleWithTemplate2Web(CurrentLoginEmUserInfo loginUserInfo, HttpServletResponse response, EmDefineMenuEntity emDefineMenuEntity, AntdFileUploadBean fileUploadBean, String[] checkIds) throws Exception;

    /**
     * 导出所有 用户账号 到excel
     * @param loginUserInfo    当前登录用户
     * @param response
     * @param emDefineMenuEntity
     * @param fileUploadBean
     * @throws Exception
     */
    void dealAllExportSingleWithTemplate2Web(CurrentLoginEmUserInfo loginUserInfo, HttpServletResponse response, EmDefineMenuEntity emDefineMenuEntity, AntdFileUploadBean fileUploadBean) throws Exception;
}
