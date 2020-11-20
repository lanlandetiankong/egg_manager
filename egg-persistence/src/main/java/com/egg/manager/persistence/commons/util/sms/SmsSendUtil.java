package com.egg.manager.persistence.commons.util.sms;

import com.alibaba.fastjson.JSON;
import com.egg.manager.persistence.commons.base.constant.Constant;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.sms.send.SmsSendRequest;
import com.egg.manager.persistence.commons.base.sms.send.SmsSendResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
public class SmsSendUtil {

    private static final String CHARSET = "utf-8";

    /**
     * 短信平台请求地址
     */
    private static final String URL = "http://smssh1.253.com/msg/send/json";

    /**
     * 用户平台API账号(非登录账号,示例:N1234567)
     */
    private static final String ACCOUNT = "*****";

    /**
     * 用户平台API密码(非登录密码)
     */
    private static final String PASSWORD = "*****";


    private static final String REPORT = "true";

    private static final String SIGNATURE = "【*****】";

    /**
     * 调用第三方平台，下发短信
     * @param receiveUser
     * @param message
     * @return
     */
    public static SmsSendResponse sendMessage(String receiveUser, String message) throws BusinessException {
        String str = SIGNATURE + message;
        SmsSendRequest smsSingleRequest = new SmsSendRequest(ACCOUNT, PASSWORD, str, receiveUser, REPORT);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        log.info("before request string is: " + requestJson);
        String response = sendSmsByPost(URL, requestJson);
        log.info("response after request result is :" + response);
        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
        log.info(smsSingleResponse.toString());
        return smsSingleResponse;
    }

    /**
     * @param path
     * @param postContent
     * @return String
     * @throws
     */
    private static String sendSmsByPost(String path, String postContent) throws BusinessException {
        java.net.URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(HttpMethodConstant.POST);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            // 发送POST请求必须设置如下两行
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Charset", CHARSET);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            os.write(postContent.getBytes(CHARSET));
            os.flush();

            StringBuilder sb = new StringBuilder();
            int httpRspCode = urlConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                // 开始获取数据
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), CHARSET));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();

            } else {
                throw new BusinessException("短信下发失败");
            }
        } catch (Exception e) {
            throw new BusinessException("短信下发失败");
        }
    }

    public enum SMSType {
        AUTH,

        REG,

        FINDPASSWORD,


        MODIFYINFO;

        /**
         * toString
         * @return String
         */
        @Override
        public String toString() {
            String smsType = "";
            switch (this) {
                case AUTH:
                    smsType = "登陆验证";
                    break;
                case REG:
                    smsType = "注册账号";
                    break;
                case FINDPASSWORD:
                    smsType = "修改密码";
                    break;
                case MODIFYINFO:
                    smsType = "修改账号";
                    break;
                default:
                    smsType = "";
            }
            return smsType;
        }

        public static Integer getType(String str) {
            Integer smsType;
            switch (str) {
                case "AUTH":
                    smsType = 1;
                    break;
                case "REG":
                    smsType = 2;
                    break;
                case "FINDPASSWORD":
                    smsType = 3;
                    break;
                case "MODIFYINFO":
                    smsType = 4;
                    break;
                default:
                    smsType = 0;
            }
            return smsType;
        }

        public static SMSType getSmsType(String str) {
            SMSType smsType = null;
            for (SMSType ut : SMSType.values()) {
                if (ut.name().equals(str)) {
                    smsType = ut;
                    break;
                }
            }
            return smsType;
        }
    }

    public static boolean isCaptchaPassTime(Long sendTime) {
        long now = System.currentTimeMillis();
        return now - sendTime > Constant.PASS_TIME;
    }

    public static void main(String[] args) throws BusinessException {
        sendMessage("13888888888", "我试试请求，看一看结果");
    }
}
