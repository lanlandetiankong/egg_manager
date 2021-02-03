package com.egg.manager.persistence.commons.util.basic.http;

import javax.servlet.http.HttpServletRequest;

/**
 * ip相关的工具
 * @author konghang
 */
public class IpUtils {

    /**
     * 获取真实IP
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        if (request == null) {
            return "0.0.0.0";
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && realIp.length() > 0) {
            return realIp;
        }
        realIp = request.getHeader("X-Forwarded-For");
        if (realIp != null && realIp.length() > 0) {
            return realIp;
        }
        return request.getRemoteAddr();
    }


    public static String getRealHost(HttpServletRequest request) {
        if (request == null) {
            return "localhost";
        }
        String realHost = request.getHeader("X-Forwarded-Host");
        if (realHost != null && realHost.length() > 0) {
            return realHost;
        }

        realHost = request.getHeader("Host");
        if (realHost != null && realHost.length() > 0) {
            Integer port = request.getServerPort();
            return realHost.replace(":" + port.toString(), "");
        }
        return request.getServerName();
    }

    /**
     * ip地址转成long型数字
     * 将IP地址转化成整数的方法如下：
     * 1、通过String的split方法按.分隔得到4个长度的数组
     * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
     * @param strIp
     * @return
     */
    public static long ipToLong(String strIp) {
        String[]ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 将十进制整数形式转换成127.0.0.1形式的ip地址
     * 将整数形式的IP地址转化成字符串的方法如下：
     * 1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。
     * 2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。
     * 3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。
     * 4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
     * @param longIp
     * @return
     */
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

    public static boolean isIP(String addr) {
        if (addr != null && !addr.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            if (addr.matches(regex)) {
                return true;
            }
        }
        return false;
    }

}
