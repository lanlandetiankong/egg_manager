package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineGroupMapper;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineGroupVo;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineGroupService extends IService<DefineGroup>, MyBaseMysqlService<DefineGroup, DefineGroupMapper, DefineGroupVo> {


}
