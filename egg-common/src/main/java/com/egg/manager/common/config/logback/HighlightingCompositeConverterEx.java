package com.egg.manager.common.config.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * @author zhoucj
 * @description: log4j 输出样式配置
 * @date 2020/10/20
 */
public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                // same as default color scheme
                return ANSIConstants.BOLD + ANSIConstants.RED_FG;
            case Level.WARN_INT:
                // same as default color scheme
                return ANSIConstants.RED_FG;
            case Level.INFO_INT:
                // use CYAN instead of BLUE
                return ANSIConstants.CYAN_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }

}
