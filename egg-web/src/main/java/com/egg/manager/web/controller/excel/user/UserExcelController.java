package com.egg.manager.web.controller.excel.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.annotation.log.CurrentLoginUser;
import com.egg.manager.api.services.excel.service.user.UserAccountXlsService;
import com.egg.manager.api.services.basic.module.DefineMenuService;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.api.trait.excel.listeners.introduce.user.UserAccountXlsIntroduceListener;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Set;


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




    @Reference
    private DefineMenuService defineMenuService ;
    @Reference
    private UserAccountXlsService userAccountXlsService ;
    @Reference
    private UserAccountService userAccountService ;

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


    @ApiOperation(value = "导入数据", notes = "导入数据", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/dealImportData")
    @ResponseBody
    public MyCommonResult dealImportData(HttpServletRequest request, @RequestParam(value = "files") MultipartFile[] fileArr,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(fileArr == null || fileArr.length == 0){
                throw new BusinessException("上传的文件为空！");
            }   else {
                Set<String> accountExistSet = userAccountService.dealGetExistAccountSet(BaseStateEnum.ENABLED.getValue(),new EntityWrapper<UserAccount>());
                for(MultipartFile file : fileArr){
                    EasyExcel.read(file.getInputStream(), UserAccountXlsInModel.class, new UserAccountXlsIntroduceListener(userAccountService,loginUser,accountExistSet))
                            .sheet()
                            .headRowNumber(1)   //前几行是头部，将不读取
                            .doRead();
                }
                this.dealCommonSuccessCatch(result, "成功导入数据！");
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


}
