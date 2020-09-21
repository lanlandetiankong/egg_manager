package com.egg.manager.web.controller.common.file;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.enums.file.AntdFileUploadStatusEnum;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/2
 * \* Time: 23:00
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API -  ExcelUploadController ", description = "Excel上传接口")
@RestController
@RequestMapping(value = "/commom_api/file/excelUpload")
public class ExcelUploadController extends BaseController {
    @Autowired
    private UploadProps uploadProps;

    @ApiOperation(value = "上传Excel模板", notes = "上传Excel模板", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/excelModelUpload")
    public MyCommonResult doAddUserAccount(HttpServletRequest request, @RequestParam(value = "files") MultipartFile[] fileArr, @RequestParam(value = "prefixFolder", defaultValue = "") String prefixFolder) {
        MyCommonResult result = new MyCommonResult();
        try {
            if (fileArr == null || fileArr.length == 0) {
                throw new BusinessException("上传的文件为空！");
            } else {
                String baseDir = uploadProps.getLocationPrefix() + uploadProps.getProjectName() + uploadProps.getLocationOfExcel() + prefixFolder;
                List<AntdFileUploadBean> uploadBeanList = new ArrayList<>();
                for (MultipartFile file : fileArr) {
                    byte[] fileBytes = file.getBytes();
                    String oldFileName = file.getOriginalFilename();
                    int lastDotIndex = oldFileName.lastIndexOf(".");
                    String fileType = "";
                    if (lastDotIndex > -1) {
                        fileType = oldFileName.substring(lastDotIndex);
                    }
                    String uuid = MyUUIDUtil.renderSimpleUUID();
                    String uuidName = uuid + fileType;
                    String fileUri = File.separator + uploadProps.getProjectName() + uploadProps.getLocationOfExcel() + prefixFolder + File.separator + uuidName;
                    File folder = new File(baseDir);
                    if (folder.exists() == false) {
                        folder.mkdirs();
                    }
                    //最终的存储路径
                    String fileLocation = baseDir + File.separator + uuidName;
                    Path path = Paths.get(fileLocation);
                    Files.write(path, fileBytes);

                    AntdFileUploadBean uploadBean = AntdFileUploadBean.builder()
                            .name(oldFileName)
                            .uid(uuid)
                            .url(uploadProps.getUrlPrefix() + fileUri)
                            .uri(fileUri)
                            .urlLocation(fileUri)
                            .status(AntdFileUploadStatusEnum.Done.getValue())
                            .response("文件上传成功！")
                            .build();
                    uploadBeanList.add(uploadBean);
                }
                result.setFileUploaderBeanList(uploadBeanList);
            }
            this.dealCommonSuccessCatch(result, "已成功上传Excel文件！");
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
