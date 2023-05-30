package com.gxy.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxy.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface IMenuService extends IService<Menu> {
    /*
    * 根据用户id查询菜单列表
    * */
    List<Menu> getMenusByAdminId();

    /*
    * 根据角色获取菜单列表
    * */
    List<Menu> getMenusWithRole();

    /*
    * 查询所有菜单
    * */
    List<Menu> getAllMenus();
}
