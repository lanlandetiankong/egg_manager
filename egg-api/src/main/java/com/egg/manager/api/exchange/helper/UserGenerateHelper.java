package com.egg.manager.api.exchange.helper;

import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.util.data.random.RandomValueUtil;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;

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
    public static UserAccountEntity generateUser() {
        UserAccountEntity userAccountEntity = UserAccountEntity.builder()
                .userName(RandomValueUtil.getChineseName())
                .email(RandomValueUtil.getEmail(1, 9))
                .phone(RandomValueUtil.getTel())
                .address(RandomValueUtil.getRoad())
                .sex(RandomValueUtil.getShortNum(0, 1))
                .locked((short) 0)
                .userType(UserAccountBaseTypeEnum.GeneratedUser.getValue()).build();
        //密码及盐
        passwordHelper.encryptPassword(userAccountEntity);
        return userAccountEntity;
    }

    /**
     * 批量生成 用户
     * @param size 数量
     * @return
     */
    public static ArrayList<UserAccountEntity> batchGenerateUser(int size) {
        ArrayList<UserAccountEntity> list = new ArrayList<>();
        if (size <= 0) {
            return list;
        }
        for (int i = 0; i < size; i++) {
            list.add(generateUser());
        }
        return list;
    }
}