package com.egg.manager.persistence.enhance.pojo.mysql.transfer;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.enhance.pojo.mysql.dto.MyBaseMysqlDto;
import com.egg.manager.persistence.enhance.pojo.mysql.vo.MyBaseMysqlVo;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public abstract class BaseMysqlTransfer<T extends Model<T>, V extends MyBaseMysqlVo, D extends MyBaseMysqlDto> implements Serializable {
}
