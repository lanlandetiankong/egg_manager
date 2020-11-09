package com.egg.manager.web.controller.user.xls;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.api.services.em.define.basic.DefineMenuService;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.api.services.em.user.xls.UserAccountXlsService;
import com.egg.manager.api.trait.excel.listeners.introduce.user.UserAccountXlsIntroduceListener;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.web.controller.BaseController;
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
public class UserExcelController extends BaseController {


    @Reference
    private DefineMenuService defineMenuService;
    @Reference
    private UserAccountXlsService userAccountXlsService;
    @Reference
    private UserAccountService userAccountService;

    @ApiOperation(value = "导出/所选->excel文件", response = File.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/exportCheckList")
    public void dealExportCheckLists(HttpServletRequest request, HttpServletResponse response,
                                     @NotBlank(message = "未知菜单id") String menuId, Long[] checkIds
            , @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(menuId, BaseRstMsgConstant.ErrorMsg.unknowId());
            DefineMenuEntity defineMenuEntity = defineMenuService.getById(menuId);
            if (defineMenuEntity == null) {
                throw new BusinessException("指定的无效的菜单！");
            }
            AntdFileUploadBean fileUploadBean = userAccountXlsService.dealVerifyMenuExportAble(defineMenuEntity);
            userAccountXlsService.dealCheckExportSingleWithTemplate2Web(loginUser, response, defineMenuEntity, fileUploadBean, checkIds);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
            this.respResultJsonToFront(log, response, result);
        }
    }

    @ApiOperation(value = "导出/全部->excel文件", response = File.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/exportAllList")
    public void dealGetAllUserAccountList(HttpServletRequest request, HttpServletResponse response, String menuId
            , @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(menuId, BaseRstMsgConstant.ErrorMsg.unknowId());
            DefineMenuEntity defineMenuEntity = defineMenuService.getById(menuId);
            Assert.notNull(defineMenuEntity, "无效菜单:" + actionFailMsg);
            //菜单模板配置
            AntdFileUploadBean fileUploadBean = userAccountXlsService.dealVerifyMenuExportAble(defineMenuEntity);
            userAccountXlsService.dealAllExportSingleWithTemplate2Web(loginUser, response, defineMenuEntity, fileUploadBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
            this.respResultJsonToFront(log, response, result);
        }
    }


    @ApiOperation(value = "导入->excel文件", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/importData")
    @ResponseBody
    public MyCommonResult importData(HttpServletRequest request, @RequestParam(value = "files") MultipartFile[] fileArr,
                                     @CurrentLoginUser UserAccountEntity loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notEmpty(fileArr, BaseRstMsgConstant.ErrorMsg.emptyUploadFile());
            Set<String> accountExistSet = userAccountService.dealGetExistAccountSet(loginUser, BaseStateEnum.ENABLED.getValue(), new QueryWrapper<UserAccountEntity>());
            for (MultipartFile file : fileArr) {
                //前1行是头部，将不读取
                EasyExcel.read(file.getInputStream(), UserAccountXlsInModel.class, new UserAccountXlsIntroduceListener(userAccountService, loginUser, accountExistSet))
                        .sheet()
                        .headRowNumber(1)
                        .doRead();
            }
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
