package com.egg.manager.em.web.controller.emctl.user.xls;

import cn.hutool.core.lang.Assert;
import org.apache.dubbo.config.annotation.Reference;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.define.basic.EmDefineMenuService;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.api.services.em.user.xls.EmUserAccountXlsService;
import com.egg.manager.api.trait.excel.listeners.introduce.user.UserAccountXlsIntroduceListener;
import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.exception.MyRuntimeBusinessException;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.excel.introduce.user.EmUserAccountXlsInModel;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-用户Excel处理接口")
@Controller
@RequestMapping("/excel/userAccount")
public class EmUserExcelController extends BaseController {
    @Reference
    private EmDefineMenuService emDefineMenuService;
    @Reference
    private EmUserAccountXlsService emUserAccountXlsService;
    @Reference
    private EmUserAccountService emUserAccountService;

    @ApiOperation(value = "导出/所选->excel文件", response = File.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/exportCheckList")
    public void dealExportCheckLists(HttpServletRequest request, HttpServletResponse response,
                                     @NotBlank(message = "未知菜单id") String menuId, String[] checkIds
            , @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(menuId, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmDefineMenuEntity emDefineMenuEntity = emDefineMenuService.getById(menuId);
        if (emDefineMenuEntity == null) {
            throw new MyRuntimeBusinessException(BaseRstMsgConstant.ErrorMsg.invalidObject());
        }
        AntdFileUploadBean fileUploadBean = emUserAccountXlsService.dealVerifyMenuExportAble(emDefineMenuEntity);
        emUserAccountXlsService.dealCheckExportSingleWithTemplate2Web(loginUserInfo, response, emDefineMenuEntity, fileUploadBean, checkIds);
        super.handleRespJsonToFront(response, result);
    }

    @ApiOperation(value = "导出/全部->excel文件", response = File.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/exportAllList")
    public void dealGetAllUserAccountList(HttpServletRequest request, HttpServletResponse response, String menuId
            , @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(menuId, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmDefineMenuEntity emDefineMenuEntity = emDefineMenuService.getById(menuId);
        Assert.notNull(emDefineMenuEntity, BaseRstMsgConstant.ErrorMsg.invalidObject());
        //菜单模板配置
        AntdFileUploadBean fileUploadBean = emUserAccountXlsService.dealVerifyMenuExportAble(emDefineMenuEntity);
        emUserAccountXlsService.dealAllExportSingleWithTemplate2Web(loginUserInfo, response, emDefineMenuEntity, fileUploadBean);
    }

    @ApiOperation(value = "导入->excel文件", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/importData")
    @ResponseBody
    public WebResult importData(HttpServletRequest request, @RequestParam(value = "files") MultipartFile[] fileArr,
                                @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notEmpty(fileArr, BaseRstMsgConstant.ErrorMsg.emptyUploadFile());
        Set<String> accountExistSet = emUserAccountService.dealGetExistAccountSet(loginUserInfo, BaseStateEnum.ENABLED.getValue(), new QueryWrapper<EmUserAccountEntity>());
        for (MultipartFile file : fileArr) {
            //前1行是头部，将不读取
            EasyExcel.read(file.getInputStream(), EmUserAccountXlsInModel.class, new UserAccountXlsIntroduceListener(emUserAccountService, loginUserInfo, accountExistSet))
                    .sheet()
                    .headRowNumber(1)
                    .doRead();
        }
        return result;
    }
}
