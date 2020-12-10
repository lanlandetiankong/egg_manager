package com.egg.manager.persistence.commons.util.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class I18nUtil {

    private static MessageSource messageSource;

    public I18nUtil(MessageSource messageSource) {
        I18nUtil.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }
    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey,Object... args) {
        try {
            return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }
}