package com.egg.manager.api.trait.excel.listeners.introduce.user;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.em.user.pojo.transfer.UserAccountTransfer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
public class UserAccountXlsIntroduceListener extends AnalysisEventListener<UserAccountXlsInModel> {

    private UserAccountService userAccountService;
    /**
     * 每隔100条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    List<UserAccountXlsInModel> list = new ArrayList<UserAccountXlsInModel>();

    /**
     * 已存在的 account
     */
    Set<String> accountExistSet = new HashSet<>();
    /**
     * 当前登录用户
     */
    UserAccount loginUser = null;


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     * @param userAccountService
     */
    public UserAccountXlsIntroduceListener(UserAccountService userAccountService, UserAccount loginUser, Set<String> accountExistSet) {
        this.userAccountService = userAccountService;
        this.loginUser = loginUser;
        if (accountExistSet != null && accountExistSet.isEmpty() == false) {
            this.accountExistSet.addAll(accountExistSet);
        }
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(UserAccountXlsInModel data, AnalysisContext context) {
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        if (list != null && list.isEmpty() == false) {
            userAccountService.saveBatch(UserAccountTransfer.xlsModelListToEntitys(list, loginUser, accountExistSet));
            log.info("存储数据库成功！");
        }
    }
}
