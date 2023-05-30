package com.gxy.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxy.server.pojo.Admin;
import com.gxy.server.pojo.Menu;
import com.gxy.server.pojo.RespBean;
import com.gxy.server.pojo.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface IAdminService extends IService<Admin> {

    //登录之后返回token
    RespBean login(String username, String password, String code, HttpServletRequest request);

    //根据用户名获取用户
    Admin getAdminByUserName(String username);

    //根据用户Id,查询其对应的角色
    List<Role> getRoles(Integer adminId);

    //获取所有操作员
    List<Admin> getAllAdmins(String keywords);


    //更新操作员角色
    RespBean updateAdminRole(Integer adminId, Integer[] rids);

    //更新用户密码
    RespBean updateAdminPassword(String oldPassword, String password, Integer adminId);

    //更新用户头像
    RespBean updateAdminUserFace(String url, Integer id, Authentication authentication);
}
