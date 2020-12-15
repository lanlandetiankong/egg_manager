package com.egg.manager.obl.web.controller.article;


import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.article.basic.OblArticleTagRelatedService;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleTagRelatedMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description 文章与标签关联表-Api
 * @date 2020-11-30
 */
@Slf4j
@Api(value = "API-文章与标签关联表")
@RestController
@RequestMapping("/oblArticleTagRelated")
public class OblArticleTagRelatedController extends BaseController {

    @Autowired
    private OblArticleTagRelatedMapper oblArticleTagRelatedMapper;
    @Reference
    private OblArticleTagRelatedService oblArticleTagRelatedService;

}