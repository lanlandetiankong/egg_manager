package com.egg.manager.web.controller.excel.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.persistence.excel.user.UserAccountXlsModel;
import com.egg.manager.persistence.vo.user.UserJobVo;
import com.egg.manager.service.excel.service.user.UserAccountXlsService;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.helper.excel.MyEasyExcelUtils;
import com.egg.manager.service.service.module.DefineMenuService;
import com.egg.manager.service.service.user.UserAccountService;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private DefineMenuService defineMenuService ;
    @Autowired
    private UserAccountXlsService userAccountXlsService ;

    @PostMapping(value = "/exportCheckList")
    public void dealExportCheckLists(HttpServletRequest request,HttpServletResponse response,
                                                          @NotBlank(message = "未知菜单id") String menuId,
                                                        String[] checkIds) {
        MyCommonResult result = new MyCommonResult();
        try {
            DefineMenu defineMenu = defineMenuService.selectById(menuId);
            if(defineMenu == null){
                throw new BusinessException("指定的无效的菜单！");
            }
            AntdFileUploadBean fileUploadBean = userAccountXlsService.dealVerifyMenuExportAble(defineMenu);
            userAccountXlsService.dealCheckExportSingleWithTemplate2Web(response,defineMenu,fileUploadBean,checkIds);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log,result,e);
            this.respResultJsonToFront(log, response,result);
        }
    }

    @PostMapping(value = "/exportAllList")
    public void dealGetAllUserAccountList(HttpServletRequest request, HttpServletResponse response, String menuId) {
        MyCommonResult result = new MyCommonResult();
        try {
            DefineMenu defineMenu = defineMenuService.selectById(menuId);
            if(defineMenu == null){
                throw new BusinessException("指定的无效的菜单！");
            }
            //菜单模板配置
            AntdFileUploadBean fileUploadBean = userAccountXlsService.dealVerifyMenuExportAble(defineMenu);
            userAccountXlsService.dealAllExportSingleWithTemplate2Web(response,defineMenu,fileUploadBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
            this.respResultJsonToFront(log, response,result);
        }
    }


}
