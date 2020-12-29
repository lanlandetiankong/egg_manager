package com.egg.manager.em.web.controller.emctl.index.olddata;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.facade.api.exchange.BaseController;
import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserAccountMapper;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-测试接口")
@RestController
@RequestMapping("/emCtl/index/olddata")
@Deprecated
public class OldDataController extends BaseController {
    @Autowired
    private EmUserAccountMapper emUserAccountMapper;

    @EmPcWebQueryLog(fullPath = "/emCtl/index/olddata/restore", flag = false)
    @GetMapping(value = "/testEnv")
    public void restore() {
        String json = "{\"RECORDS\":[{\"fid\":\"25d369c102964af487265f84d1564b86\",\"user_name\":\"\",\"account\":\"testAccount6\",\"nick_name\":\"测试人员5\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"\",\"user_type\":\"\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 22:48:43\",\"update_time\":\"16/4/2020 22:48:43\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"357eac4bf8ea429cb68b547fb4a4914c\",\"user_name\":\"\",\"account\":\"jianke\",\"nick_name\":\"呆着的剑客\",\"avatar_url\":\"\\\\egg_manager\\\\dev\\\\img\\\\06e76356aa724960807c861b3dd6f086.jpg\",\"password\":\"123456\",\"phone\":\"12345678901\",\"email\":\"\",\"sex\":\"1\",\"user_type_num\":\"\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"5/4/2020 18:26:18\",\"update_time\":\"5/4/2020 18:26:18\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"5a376758f968448f9cad65ec09c627af\",\"user_name\":\"\",\"account\":\"testAccount51\",\"nick_name\":\"测试人员5\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"0\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 23:19:09\",\"update_time\":\"16/4/2020 23:19:09\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"667c9ccc530d49bb878b35353fb716e9\",\"user_name\":\"\",\"account\":\"testAccount2\",\"nick_name\":\"测试人员2\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"\",\"user_type\":\"\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 22:48:43\",\"update_time\":\"16/4/2020 22:48:43\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"73c3d2e2b8414e2d9c03678a9d6c0bc1\",\"user_name\":\"\",\"account\":\"testAccount5\",\"nick_name\":\"测试人员5\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"\",\"user_type\":\"\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 22:48:43\",\"update_time\":\"16/4/2020 22:48:43\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"816eb1784a524811bc5322c924edfcd2\",\"user_name\":\"\",\"account\":\"2020090702\",\"nick_name\":\"2020090702\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"\",\"email\":\"\",\"sex\":\"\",\"user_type_num\":\"\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"7/9/2020 22:09:31\",\"update_time\":\"14/9/2020 21:35:11\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"8aa6cb27bab7448a8cf0edb76e66f7ce\",\"user_name\":\"\",\"account\":\"testAccount11\",\"nick_name\":\"测试人员1\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"0\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 23:19:09\",\"update_time\":\"16/4/2020 23:19:09\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"8c6398db0ba1457693695179883d34a2\",\"user_name\":\"\",\"account\":\"testAccount6_31afd81844f5428d92a8ec3dada1022b\",\"nick_name\":\"测试人员5\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"0\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 23:19:09\",\"update_time\":\"16/4/2020 23:19:09\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"a66207fe2f444dde84b02c5af221bc69\",\"user_name\":\"\",\"account\":\"testAccount4_620d096dbb6f44d9a6fef3438e3d0096\",\"nick_name\":\"测试人员4\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"0\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 23:19:09\",\"update_time\":\"16/4/2020 23:19:09\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"aa12e98712754f73b31b97a9a4e7a86a\",\"user_name\":\"\",\"account\":\"testAccount2_88754545687f4c6e9e4845331ede1098\",\"nick_name\":\"测试人员2\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"0\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 23:19:09\",\"update_time\":\"16/4/2020 23:19:09\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"b118ccaf4a4b4cb791b8c713d206a565\",\"user_name\":\"\",\"account\":\"2020090701\",\"nick_name\":\"2020090701\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"\",\"email\":\"\",\"sex\":\"\",\"user_type_num\":\"\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"7/9/2020 21:56:44\",\"update_time\":\"7/9/2020 21:56:44\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"b183d4c4cc754b4b8dad1d8698327bad\",\"user_name\":\"\",\"account\":\"testAccount3\",\"nick_name\":\"测试人员3\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"\",\"user_type\":\"\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 22:48:43\",\"update_time\":\"16/4/2020 22:48:43\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"b47b4c59de2e496d8637d767d0275c39\",\"user_name\":\"\",\"account\":\"testAccount31\",\"nick_name\":\"测试人员3\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@qq.com\",\"sex\":\"1\",\"user_type_num\":\"0\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 23:19:09\",\"update_time\":\"10/8/2020 23:08:44\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"bc986ed965a64dd481f9aa4c137d0f39\",\"user_name\":\"\",\"account\":\"测试邮箱\",\"nick_name\":\"TestEmail\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"12222222222\",\"email\":\"sd12\",\"sex\":\"1\",\"user_type_num\":\"\",\"user_type\":\"0\",\"state\":\"1\",\"remark\":\"\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"12/4/2020 14:38:55\",\"update_time\":\"12/4/2020 14:49:01\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"fd2ea6d83af94a61a8ea1085b58670a3\",\"user_name\":\"\",\"account\":\"testAccount4\",\"nick_name\":\"测试人员4\",\"avatar_url\":\"\",\"password\":\"123456\",\"phone\":\"1234455555\",\"email\":\"232399239@\",\"sex\":\"1\",\"user_type_num\":\"\",\"user_type\":\"\",\"state\":\"1\",\"remark\":\"sdsdllds\",\"create_user_id\":\"super10002323232\",\"last_modifyer_id\":\"super10002323232\",\"create_time\":\"16/4/2020 22:48:43\",\"update_time\":\"16/4/2020 22:48:43\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"},{\"fid\":\"super10002323232\",\"user_name\":\"SuperRoot\",\"account\":\"SuperRoot\",\"nick_name\":\"超级管理员\",\"avatar_url\":\"\\\\egg_manager\\\\img\\\\16ed3afff2024d09a877ea62adf11971.jpg\",\"password\":\"123456\",\"phone\":\"\",\"email\":\"695605813@qq.com\",\"sex\":\"1\",\"user_type_num\":\"100\",\"user_type\":\"100\",\"state\":\"1\",\"remark\":\"超级管理员\",\"create_user_id\":\"\",\"last_modifyer_id\":\"4217023a73264edba88b2e6609ebee56\",\"create_time\":\"16/9/2019 23:04:55\",\"update_time\":\"16/9/2019 23:04:58\",\"locked\":\"0\",\"version\":\"\",\"is_deleted\":\"\",\"deleted_time\":\"\"}]}";
        JSONObject jsonObj = JSONObject.parseObject(json);
        JSONArray jsonArray = jsonObj.getJSONArray("RECORDS");
        runRestore(jsonArray);
    }

    public void runRestore(JSONArray jsonArray) {
        List<EmUserAccountEntity> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObject.put(FieldConst.COL_CREATE_USER_ID, null);
            jsonObject.put(FieldConst.COL_LAST_MODIFYER_ID, null);
            jsonObject.put(FieldConst.COL_CREATE_TIME, null);
            jsonObject.put(FieldConst.COL_UPDATE_TIME, null);
            jsonObject.put(FieldConst.COL_FID, null);
            EmUserAccountEntity obj = jsonArray.getObject(i, EmUserAccountEntity.class);
            list.add(obj);
            count += emUserAccountMapper.insert(obj);
        }
        System.out.println();
    }
}
