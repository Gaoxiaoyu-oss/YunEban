package com.gxy.server.config.security.component;

/*
* 权限控制，根据请求Url分析请求所需的角色
* */

import com.gxy.server.pojo.Menu;
import com.gxy.server.pojo.Role;
import com.gxy.server.service.IMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

import java.util.Collection;
/*
* 系统权限控制:
*   根据请求url，分析请求所需的角色
* */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    // 根据此次用户请求的URL查询访问该url需要哪些角色,后面还需要根据此时的用户id获取角色列表，两者进行对比
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //通过用户id获取菜单列表
        List<Menu> menus = menuService.getMenusWithRole();
        for (Menu menu : menus) {
            //判断请求url与菜单角色是否匹配
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                // 将匹配上的角色放入list中
                return SecurityConfig.createList(str);
            }
        }
        //没匹配的url默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
