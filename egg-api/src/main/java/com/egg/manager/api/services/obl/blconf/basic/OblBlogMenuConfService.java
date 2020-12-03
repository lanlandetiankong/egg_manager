package com.egg.manager.api.services.obl.blconf.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogMenuConfEntity;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogMenuConfMapper;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogMenuConfDto;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogMenuConfVo;

/**
 * @author zhoucj
 * @description 博客菜单定义表-Service
 * @date 2020-11-30
 */
public interface OblBlogMenuConfService extends MyBaseMysqlService<OblBlogMenuConfEntity, OblBlogMenuConfMapper, OblBlogMenuConfVo> {

    /**
     * 分页查询-博客菜单定义表 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblBlogMenuConfDto> queryPageBean);


    /**
     * 新增-博客菜单定义表
     * @param loginUserInfo     当前登录用户
     * @param oblBlogMenuConfVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblBlogMenuConfVo oblBlogMenuConfVo) throws Exception;

    /**
     * 更新博客菜单定义表
     * @param loginUserInfo     当前登录用户
     * @param oblBlogMenuConfVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblBlogMenuConfVo oblBlogMenuConfVo) throws Exception;


}