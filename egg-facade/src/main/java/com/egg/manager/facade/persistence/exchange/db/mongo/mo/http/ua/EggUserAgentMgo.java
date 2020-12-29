package com.egg.manager.facade.persistence.exchange.db.mongo.mo.http.ua;

import cn.hutool.http.useragent.*;
import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @Description: 请求用户的UserAgent
 * @ClassName: EggUserAgentMgo
 * @Author: zhoucj
 * @Date: 2020/11/6 11:25
 */
@Data
public class EggUserAgentMgo implements Serializable {
    /**
     *
     */
    @Field(value = "osName")
    private String osName;
    /**
     *
     */
    @Field(value = "engineName")
    private String engineName;
    /**
     *
     */
    @Field(value = "engineVersion")
    private String engineVersion;
    /**
     *
     */
    @Field(value = "platformName")
    private String platformName;

    /**
     *
     */
    @Field(value = "browserVersion")
    private String browserVersion;
    /**
     *
     */
    @Field(value = "browserName")
    private String browserName;
    /**
     *
     */
    @Field(value = "browserIsMobile")
    private Boolean browserIsMobile;
    /**
     *
     */
    @Field(value = FieldConst.COL_VERSION)
    private String version;

    public EggUserAgentMgo() {
    }

    public EggUserAgentMgo(UserAgent userAgent, String reqUserAgent) {
        if (userAgent == null) {
            return;
        }
        OS os = userAgent.getOs();
        if (os != null) {
            this.osName = os.getName();
        }
        Engine engine = userAgent.getEngine();
        if (engine != null) {
            this.engineName = engine.getName();
        }
        this.engineVersion = userAgent.getEngineVersion();
        Platform platform = userAgent.getPlatform();
        if (platform != null) {
            this.platformName = platform.getName();
        }
        Browser browser = userAgent.getBrowser();
        if (browser != null) {
            this.browserVersion = browser.getVersion(reqUserAgent);
            this.browserName = browser.getName();
            this.browserIsMobile = browser.isMobile();
        }
        this.version = userAgent.getVersion();
    }
}