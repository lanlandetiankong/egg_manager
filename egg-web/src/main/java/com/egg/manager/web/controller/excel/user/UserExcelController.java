package com.egg.manager.web.controller.excel.user;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.vo.user.UserJobVo;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.helper.excel.MyEasyExcelUtils;
import com.egg.manager.service.service.module.DefineMenuService;
import com.egg.manager.service.service.user.UserAccountService;
import com.egg.manager.web.controller.BaseController;
import com.github.crab2died.ExcelUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  UserExcelController ", description = "用户Excel处理接口")
@Controller
@RequestMapping("/excel/user_account")
public class UserExcelController extends BaseController {

    @Autowired
    private UploadProps uploadProps ;

    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private DefineMenuService defineMenuService ;

    @PostMapping(value = "/exportCheckList")
    public void dealExportCheckLists(HttpServletRequest request,HttpServletResponse response,
                                                          @NotBlank(message = "未知菜单id") String menuId,
                                                          @NotEmpty(message = "请至少勾选一条要导出的数据！") String[] checkIds) {
        MyCommonResult<UserJobVo> result = new MyCommonResult<UserJobVo>();
        try {
            DefineMenu defineMenu = defineMenuService.selectById(menuId);
            if(defineMenu == null){
                throw new BusinessException("指定的无效的菜单！");
            }
            AntdFileUploadBean fileUploadBean = AntdFileUploadBean.dealJsonStrToBean(defineMenu.getExcelModelConf()) ;
            if(fileUploadBean == null){
                throw new BusinessException("当前菜单定义未上传Excel模板，请先上传后再使用导出Excel功能！");
            }
            String relaPath = fileUploadBean.getUrlLocation() ;
            if(StringUtils.isBlank(relaPath)){
                throw new BusinessException("当前菜单定义上传Excel模板信息有误！");
            }
            String excelPath = uploadProps.getLocationPrefix() + relaPath ;
            List<UserAccount> userAccountList =  userAccountService.selectList(null);

            String excelFileName = defineMenu.getMenuName() + "选择导出表";

            MyEasyExcelUtils.export2Web(response, excelFileName, defineMenu.getMenuName(), UserAccount.class, userAccountList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        //return result;
    }

    @PostMapping(value = "/exportAllList")
    public MyCommonResult<UserJobVo> dealGetAllUserAccountList(HttpServletRequest request, String menuId) {
        MyCommonResult<UserJobVo> result = new MyCommonResult<UserJobVo>();
        try {
            String path = "/excel/Test1.xlsx";
            dealCommonSuccessCatch(result, "上传excel:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
