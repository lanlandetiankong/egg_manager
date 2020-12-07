package com.egg.manager.api.exchange.helper;

import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.util.data.random.RandomValueUtil;

import java.util.ArrayList;

/**
 * @Description:
 * @ClassName: UserGenerateHelper
 * @Author: zhoucj
 * @Date: 2020/12/1 14:55
 */
public class UserGenerateHelper {
    private static PasswordHelper passwordHelper = new PasswordHelper();

    /**
     * 生成 用户
     * @return
     */
    public static EmUserAccountEntity generateUser() {
        EmUserAccountEntity emUserAccountEntity = EmUserAccountEntity.builder()
                .userName(RandomValueUtil.getChineseName())
                .email(RandomValueUtil.getEmail(1, 9))
                .phone(RandomValueUtil.getTel())
                .address(RandomValueUtil.getRoad())
                .sex(RandomValueUtil.getShortNum(0, 1))
                .locked((short) 0)
                .userType(UserAccountBaseTypeEnum.GeneratedUser.getValue()).build();
        //密码及盐
        passwordHelper.encryptPassword(emUserAccountEntity);
        return emUserAccountEntity;
    }

    /**
     * 批量生成 用户
     * @param size 数量
     * @return
     */
    public static ArrayList<EmUserAccountEntity> batchGenerateUser(int size) {
        ArrayList<EmUserAccountEntity> list = new ArrayList<>();
        if (size <= 0) {
            return list;
        }
        for (int i = 0; i < size; i++) {
            list.add(generateUser());
        }
        return list;
    }
}