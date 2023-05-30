package com.gxy.server.pojo;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
* 通用返回结果，包含执行情况，错误编号，错误信息等.
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    //成功返回
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    //成功返回结果
    public static RespBean success(String message,Object obj){
        return new RespBean(200,message,obj);
    }

    //失败返回结果
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }

    public static RespBean error(String message,Object obj){
        return new RespBean(500,message,obj);
    }
}
