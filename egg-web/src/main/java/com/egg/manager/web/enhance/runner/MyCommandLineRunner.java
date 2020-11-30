package com.egg.manager.web.enhance.runner;

import com.egg.manager.persistence.commons.base.constant.Constant;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.util.file.PackageScanUtil;
import com.egg.manager.persistence.commons.util.str.ComUtil;
import com.egg.manager.persistence.enhance.annotation.shiro.ShiroPass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhoucj
 * @description 扫描包取得需要进行@ShiroPass的接口列表
 * @date 2020/10/21
 */
@Slf4j
//@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Value("${egg.conf.com.oolong.blog.web.controller.scanPackage}")
    private String scanPackagePath;
    @Value("${egg.conf.project.name}")
    private String projectName;

    /**
     * @ShiroPass url 与 method  之间的分隔符
     */
    private String urlDelimiter = ":--:";


    @Override
    public void run(String... args) throws Exception {
        //初始化 @ShiroPass 要过滤掉的 路径 Set集合
        this.handleInitMethodUrlSet(args);
    }

    public void handleInitMethodUrlSet(String... args) throws Exception {
        //先扫描Controller包并添加到 Constant.METHOD_URL_SET 中
        PackageScanUtil.doPackageScanner(scanPackagePath, this.getClass());
        Set<String> urlAndMethodSet = new HashSet<>();

        for (String aClassName : Constant.METHOD_URL_SET) {
            Class<?> clazz = Class.forName(aClassName);
            String baseUrl = "";
            String[] classUrl = {};
            if (!ComUtil.isEmpty(clazz.getAnnotation(RequestMapping.class))) {
                classUrl = clazz.getAnnotation(RequestMapping.class).value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(ShiroPass.class)) {
                    String[] methodUrl = null;
                    StringBuilder sb = new StringBuilder();
                    if (!ComUtil.isEmpty(method.getAnnotation(PostMapping.class))) {
                        methodUrl = method.getAnnotation(PostMapping.class).value();
                        if (ComUtil.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(PostMapping.class).path();
                        }
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, HttpMethodConstant.POST);
                    } else if (!ComUtil.isEmpty(method.getAnnotation(GetMapping.class))) {
                        methodUrl = method.getAnnotation(GetMapping.class).value();
                        if (ComUtil.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(GetMapping.class).path();
                        }
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, HttpMethodConstant.GET);
                    } else if (!ComUtil.isEmpty(method.getAnnotation(DeleteMapping.class))) {
                        methodUrl = method.getAnnotation(DeleteMapping.class).value();
                        if (ComUtil.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(DeleteMapping.class).path();
                        }
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, HttpMethodConstant.DELETE);
                    } else if (!ComUtil.isEmpty(method.getAnnotation(PutMapping.class))) {
                        methodUrl = method.getAnnotation(PutMapping.class).value();
                        if (ComUtil.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(PutMapping.class).path();
                        }
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, HttpMethodConstant.PUT);
                    } else {
                        methodUrl = method.getAnnotation(RequestMapping.class).value();
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, RequestMapping.class.getSimpleName());
                    }
                    if (!ComUtil.isEmpty(baseUrl)) {
                        urlAndMethodSet.add(baseUrl);
                    }
                }
            }
        }
        Constant.METHOD_URL_SET = urlAndMethodSet;
        log.info("@Pass ==> " + urlAndMethodSet);
    }


    private String dealGetRequestUrl(String[] classUrl, String[] methodUrl, StringBuilder sb, String requestName) {
        sb.append(this.projectName);
        if (!ComUtil.isEmpty(classUrl)) {
            for (String url : classUrl) {
                if (StringUtils.isBlank(url)) {
                    continue;
                }
                if (url.trim().indexOf("/") != -1) {
                    sb.append("/" + url + Constant.SYMBOL_SLASH);
                } else {
                    sb.append(url + Constant.SYMBOL_SLASH);
                }
            }
        }
        for (String url : methodUrl) {
            sb.append(url);
        }
        if (sb.toString().endsWith(Constant.SYMBOL_SLASH)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString().replaceAll("/+", Constant.SYMBOL_SLASH) + this.urlDelimiter + requestName;
    }
}
