package com.gxy.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxy.server.config.security.component.JwtTokenUtil;
import com.gxy.server.mapper.AdminMapper;
import com.gxy.server.mapper.AdminRoleMapper;
import com.gxy.server.mapper.RoleMapper;
import com.gxy.server.pojo.Admin;
import com.gxy.server.pojo.AdminRole;
import com.gxy.server.pojo.RespBean;
import com.gxy.server.pojo.Role;
import com.gxy.server.service.IAdminService;
import com.gxy.server.utils.AdminUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  AdminService实现类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passowordEncoder;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    /*
    * 登录之后返回token
    * */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        // 之前生成验证码后，会将验证码内容String放入session,session的key为captcha
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("生成的验证码：captcha ====="+captcha);
        // 如果用户输入的验证码为空，或者其和系统生成的验证码不匹配，则 :
        if (StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误，请重新输入！");
        }
        // 登录(根据username从数据库中查找该对应的user)
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if (userDetails==null || !passowordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);

        return RespBean.success("登录成功",tokenMap);
    }

    /*
    * 根据用户名获取用户
    * */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }

    /*
    * 根据用户Id,查询其对应的角色
    * */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }


    /*
    * 获取所有操作员
    * */
    @Override
    public List<Admin> getAllAdmins(String keywords) {
        //获取当前登录用户的id
        Integer currentId = AdminUtils.getCurrentAdmin().getId();
        return adminMapper.getAllAdmins(currentId,keywords);
    }

    /*
    * 更新操作员的角色
    * */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        //先删除
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));

        Integer result = adminRoleMapper.addAdminRole(adminId,rids);

        if(rids.length == result){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败！");

    }

    /*
    * 更新用户密码
    * */
    @Override
    public RespBean updateAdminPassword(String oldPassword, String password, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 判断旧密码是否正确
        if (encoder.matches(oldPassword,admin.getPassword())){
            admin.setPassword(encoder.encode(password));
            int result = adminMapper.updateById(admin);
            if (result ==  1){
                return RespBean.success("更新成功!");
            }
        }
        return RespBean.error("更新失败！");
    }

    /*
    * 更新用户头像
    * */
    @Override
    public RespBean updateAdminUserFace(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        int result = adminMapper.updateById(admin);
        if (result == 1){
            Admin principal = (Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }


}
