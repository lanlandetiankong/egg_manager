package com.egg.manager.web.runner;

import com.egg.manager.common.annotation.shiro.ShiroPass;
import com.egg.manager.common.base.constant.Constant;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.util.str.ComUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Value("${egg.conf.controller.scanPackage}")
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
        this.doPackageScanner(scanPackagePath);
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
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, "GET");
                    } else if (!ComUtil.isEmpty(method.getAnnotation(DeleteMapping.class))) {
                        methodUrl = method.getAnnotation(DeleteMapping.class).value();
                        if (ComUtil.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(DeleteMapping.class).path();
                        }
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, "DELETE");
                    } else if (!ComUtil.isEmpty(method.getAnnotation(PutMapping.class))) {
                        methodUrl = method.getAnnotation(PutMapping.class).value();
                        if (ComUtil.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(PutMapping.class).path();
                        }
                        baseUrl = this.dealGetRequestUrl(classUrl, methodUrl, sb, "PUT");
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
                sb.append(url + Constant.SYMBOL_SLASH);
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


    private void doPackageScanner(String packageName) {
        //把所有的.替换成/
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", Constant.SYMBOL_SLASH));
        String jarSuffix = ".jar";
        // 是否循环迭代
        if (StringUtils.countMatches(url.getFile(), jarSuffix) > 0) {
            boolean recursive = true;
            JarFile jar;
            // 获取jar
            try {
                jar = ((JarURLConnection) url.openConnection())
                        .getJarFile();
                // 从此jar包 得到一个枚举类
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    // 如果是以/开头的
                    if (name.charAt(0) == '/') {
                        // 获取后面的字符串
                        name = name.substring(1);
                    }
                    // 如果前半部分和定义的包名相同
                    if (name.startsWith(packageName.replaceAll("\\.", Constant.SYMBOL_SLASH))) {
                        int idx = name.lastIndexOf('/');
                        // 如果以Constant.SYMBOL_SLASH结尾 是一个包
                        if (idx != -1) {
                            // 获取包名 把Constant.SYMBOL_SLASH替换成"."
                            packageName = name.substring(0, idx)
                                    .replace('/', '.');
                        }
                        // 如果可以迭代下去 并且是一个包
                        if ((idx != -1) || recursive) {
                            // 如果是一个.class文件 而且不是目录
                            if (name.endsWith(".class")
                                    && !entry.isDirectory()) {
                                // 去掉后面的".class" 获取真正的类名
                                String className = name.substring(
                                        packageName.length() + 1, name
                                                .length() - 6);
                                try {
                                    // 添加到classes
                                    Constant.METHOD_URL_SET.add(Class
                                            .forName(packageName + '.'
                                                    + className).getName());
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                //递归读取包
                doPackageScanner(packageName + "." + file.getName());
            } else {
                String className = packageName + "." + file.getName().replace(".class", "");
                Constant.METHOD_URL_SET.add(className);
            }
        }
    }
}
