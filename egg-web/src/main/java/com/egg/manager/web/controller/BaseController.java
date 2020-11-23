package com.egg.manager.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
public class BaseController {
    /**
     * 对于void的接口，将Result用流方式返回到前端
     * @param response
     * @param result
     * @param <T>
     */
    public <T> void handleRespJsonToFront(HttpServletResponse response, WebResult result) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            log.error("", e);
        }
    }


}
