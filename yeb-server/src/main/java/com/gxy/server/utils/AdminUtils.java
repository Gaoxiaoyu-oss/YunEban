package com.gxy.server.utils;

import com.gxy.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/*
* 操作员工具类
* */
public class AdminUtils {

    //获取当前登录的操作员
    public static Admin getCurrentAdmin(){
        Admin currentAdmin = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return currentAdmin;
    }

}
