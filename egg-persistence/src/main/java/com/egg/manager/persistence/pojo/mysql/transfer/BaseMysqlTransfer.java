package com.egg.manager.persistence.pojo.mysql.transfer;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;

import java.io.Serializable;


public abstract class BaseMysqlTransfer<T extends Model<T>, V extends MyBaseMysqlVo, D extends MyBaseMysqlDto> implements Serializable {
}