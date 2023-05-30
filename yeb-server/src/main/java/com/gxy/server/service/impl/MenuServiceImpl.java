package com.gxy.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxy.server.mapper.MenuMapper;
import com.gxy.server.pojo.Admin;
import com.gxy.server.pojo.Menu;
import com.gxy.server.service.IMenuService;
import com.gxy.server.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /*
    * 根据用户id查询菜单列表
    * */
    @Override
    public List<Menu> getMenusByAdminId() {
        Integer adminId = AdminUtils.getCurrentAdmin().getId();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //先从redis缓存里面查询
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_"+adminId);
        //如果redis缓存中不存在,则去Mysql数据库中查询,并将这些数据再放入redis中
        if (CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(adminId);
            valueOperations.set("menu_"+adminId,menus);
        }
        return menus;
    }


    /*
     * 根据角色获取菜单列表
     * */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    /*
     * 查询所有菜单
     * */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
