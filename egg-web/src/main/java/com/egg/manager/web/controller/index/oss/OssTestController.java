package com.egg.manager.web.controller.index.oss;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import com.egg.manager.api.trait.utils.oss.AliyunOSSUtil;
import com.egg.manager.persistence.commons.base.beans.oss.AliyunOssResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 阿里云OSS测试接口
 * @author jason
 */
@Slf4j
@RestController
@RequestMapping("/oss")
public class OssTestController {
    /**
     * 测试-上传
     * -自定义文件
     * -自定义OSS存储路径
     * http://localhost:10100/egg_manager/oss/uploadFile
     * @return {"success":true,"fileName":"png/banner.txt","url":"http://egg-manager-bucket.oss-cn-shanghai.aliyuncs.com/png/banner.txt?Expires=1604130884&OSSAccessKeyId=LTAI4G3VqT7puMGdVjAdKm8m&Signature=GM1CbT9gpKWJRv4UcH1lJYINKY0%3D","msg":"上传成功"}
     */
    @GetMapping("/uploadFile")
    public AliyunOssResult uploadFile() {
        return AliyunOSSUtil.uploadFile(new File(this.getClass().getResource("/").getPath() + "/config/others/banner.txt"), "png/banner.txt");
    }

    /**
     * 上传文件-指定目录
     */
    @PostMapping("/uploadTest")
    public AliyunOssResult uploadTest(MultipartFile file) {
        return AliyunOSSUtil.uploadFile(file, "自定义目录/" + file.getOriginalFilename());
    }

    /**
     * 删除文件
     * http://localhost:10100/egg_manager/oss/deleteFile?fileName=TEST/png/重命名.png
     */
    @GetMapping("/deleteFile")
    public boolean deleteFile(@RequestParam String fileName) {
        return AliyunOSSUtil.deleteFile(fileName);
    }

    /**
     * oss路径下获取文件带有效期的url，获取的url可下载
     * http://localhost:10100/egg_manager/oss/getOssUrl?fileName=TEST/png/重命名.png
     */
    @GetMapping("/getOssUrl")
    public String getOssUrl(@RequestParam String fileName) {
        return AliyunOSSUtil.getOssUrl(fileName);
    }

    /**
     * 预览PDF或图片
     * http://localhost:10100/egg_manager/oss/preview?fileName=TEST/png/重命名.png
     */
    @GetMapping("/preview")
    public ResponseEntity<InputStreamResource> previewFile(@RequestParam String fileName) {
        // 获取文件类型
        String contentType = FileUtil.extName(fileName);
        // 获取文件流
        InputStream inputStream = AliyunOSSUtil.getInputStream(fileName);
        // 预览
        HttpHeaders httpHeaders = new HttpHeaders();
        if ("pdf".equalsIgnoreCase(contentType)) {
            httpHeaders.add("Content-Type", MediaType.APPLICATION_PDF_VALUE);
        } else if ("jpg".equalsIgnoreCase(contentType) || "png".equalsIgnoreCase(contentType) || "jpeg".equalsIgnoreCase(contentType)) {
            httpHeaders.add("Content-Type", MediaType.IMAGE_JPEG_VALUE);
        }
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    /**
     * 列举所有的文件url
     * http://localhost:10100/egg_manager/oss/urlList
     */
    @GetMapping("/urlList")
    public List<String> urlList() {
        return AliyunOSSUtil.urlList();
    }

    /**
     * 图片转base64
     * http://localhost:10100/egg_manager/oss/base64
     */
    @GetMapping("/base64")
    public String base64() {
        return Base64.encode(AliyunOSSUtil.getInputStream("png/重命名.png"));
    }

    /**
     * 文件转byte[]
     * http://localhost:10100/egg_manager/oss/bytes
     */
    @GetMapping("/bytes")
    public byte[] bytes() {
        return AliyunOSSUtil.getBytes("png/重命名.png");
    }
}