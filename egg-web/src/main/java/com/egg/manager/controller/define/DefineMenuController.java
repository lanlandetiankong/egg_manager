package com.egg.manager.controller.define;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.entity.define.DefineMenu;
import com.egg.manager.mapper.define.DefineMenuMapper;
import com.egg.manager.service.define.DefineMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/define/define_menu")
public class DefineMenuController {
    @Autowired
    private DefineMenuService defineMenuService ;




    @PostMapping("/get/all_menu")
    public MyCommonResult<DefineMenu> doGetAllMenu() {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<DefineMenu> allMenus  = defineMenuService.selectList(null);
        result.setResultList(allMenus);
        return result ;
    }
}
