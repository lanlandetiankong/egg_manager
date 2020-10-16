package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineGroupMapper;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineGroupVo;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineGroupService extends IService<DefineGroup>,MyBaseMysqlService<DefineGroup,DefineGroupMapper,DefineGroupVo> {


}
