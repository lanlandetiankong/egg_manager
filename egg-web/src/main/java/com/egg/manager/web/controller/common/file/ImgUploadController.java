package com.egg.manager.web.controller.common.file;

import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.web.controller.BaseController;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/12
 * \* Time: 18:49
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API -  ImgUploadController ",description = "图片上传接口")
@RestController
@RequestMapping(value = "/commom_api/file/imgUpload")
public class ImgUploadController extends BaseController{

    @Autowired
    private UploadProps uploadProps ;

    @ApiOperation(value = "上传头像", notes = "上传头像", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/headImgUpload")
    public MyCommonResult doAddUserAccount(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(file == null || file.isEmpty()){
                throw new BusinessException("上传的文件为空！");
            }   else {
                try{
                    byte[] fileBytes = file.getBytes() ;
                    String oldFileName = file.getOriginalFilename() ;
                    int lastDotIndex = oldFileName.lastIndexOf(".");
                    String fileType = "" ;
                    if(lastDotIndex > -1){
                        fileType = oldFileName.substring(lastDotIndex);
                    }
                    String uuidName = MyUUIDUtil.renderSimpleUUID() + fileType;
                    String baseDir = uploadProps.getLocationPrefix()+uploadProps.getProjectName()+uploadProps.getLocationOfImg() ;
                    String fileUri = File.separator+uploadProps.getProjectName()+uploadProps.getLocationOfImg()+File.separator+uuidName;
                    File folder = new File(baseDir);
                    if(folder.exists() == false){
                        folder.mkdirs();
                    }
                    //最终的存储路径
                    String fileLocation = baseDir +File.separator+uuidName ;
                    Path path = Paths.get(fileLocation) ;
                    Files.write(path,fileBytes);

                    FileResBean fileResBean = FileResBean.builder()
                            .fileName(uuidName)
                            .fileLocation(fileLocation)
                            .fileOldName(oldFileName)
                            .filePrefix(uploadProps.getUrlPrefix()+File.separator)
                            .fileUri(fileUri)
                            .build();
                    result.setFileResBean(fileResBean);
                }   catch (IOException e){
                    throw e ;
                }
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


}
