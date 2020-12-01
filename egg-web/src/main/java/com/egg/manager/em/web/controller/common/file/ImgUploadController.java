package com.egg.manager.em.web.controller.common.file;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.config.db.SnowflakeConfig;
import com.egg.manager.persistence.commons.base.beans.file.FileResBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.props.upload.UploadProps;
import com.egg.manager.api.exchange.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-图片上传接口")
@RestController
@RequestMapping(value = "/commomApi/file/imgUpload")
public class ImgUploadController extends BaseController {
    @Autowired
    private UploadProps uploadProps;
    @Autowired
    private SnowflakeConfig snowflakeConfig;

    @ApiOperation(value = "上传/图片->头像", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/headImgUpload")
    public WebResult doAddUserAccount(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file) throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(file, BaseRstMsgConstant.ErrorMsg.emptyUploadFile());
        Assert.isFalse(file.isEmpty(), BaseRstMsgConstant.ErrorMsg.emptyUploadFile());
        byte[] fileBytes = file.getBytes();
        String oldFileName = file.getOriginalFilename();
        int lastDotIndex = oldFileName.lastIndexOf(".");
        String fileType = "";
        if (lastDotIndex > -1) {
            fileType = oldFileName.substring(lastDotIndex);
        }
        String uuidName = snowflakeConfig.snowflakeId() + fileType;
        String baseDir = uploadProps.getLocationPrefix() + uploadProps.getProjectName() + uploadProps.getLocationOfImg();
        String fileUri = File.separator + uploadProps.getProjectName() + uploadProps.getLocationOfImg() + File.separator + uuidName;
        File folder = new File(baseDir);
        if (folder.exists() == false) {
            folder.mkdirs();
        }
        //最终的存储路径
        String fileLocation = baseDir + File.separator + uuidName;
        Path path = Paths.get(fileLocation);
        Files.write(path, fileBytes);
        FileResBean fileResBean = FileResBean.builder()
                .fileName(uuidName)
                .fileLocation(fileLocation)
                .fileOldName(oldFileName)
                .filePrefix(uploadProps.getUrlPrefix() + File.separator)
                .fileUri(fileUri)
                .build();
        result.putFileResBean(fileResBean);
        return result;
    }
}
