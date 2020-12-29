package com.egg.manager.facade.persistence.commons.util.basic.file;

import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.commons.base.constant.basic.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Description:
 * @ClassName: PackageScanUtil
 * @Author: zhoucj
 * @Date: 2020/11/12 15:09
 */
@Slf4j
public class PackageScanUtil {

    public static void doPackageScanner(String packageName, Class clazz) {
        //把所有的.替换成/
        URL url = clazz.getClassLoader().getResource(packageName.replaceAll("\\.", Constant.SYMBOL_SLASH));
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
                                    log.error(BaseRstMsgConstant.ErrorMsg.executionException("--->"),e);
                                }
                            }
                        }
                    }
                }
                return;
            } catch (IOException e) {
                log.error(BaseRstMsgConstant.ErrorMsg.executionException("--->"),e);
            }
        }
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                //递归读取包
                doPackageScanner(packageName + "." + file.getName(), clazz);
            } else {
                String className = packageName + "." + file.getName().replace(".class", "");
                Constant.METHOD_URL_SET.add(className);
            }
        }
    }
}