package com.gxy.server.controller;


import com.gxy.server.pojo.Menu;
import com.gxy.server.service.IAdminService;
import com.gxy.server.service.IMenuService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "通过用户Id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId(){
        return menuService.getMenusByAdminId();
    }

}
