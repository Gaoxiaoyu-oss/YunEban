package com.gxy.server.pojo;

/*
* 用户登录实体类，
*   保存用户登录的信息参数
* */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data //lombok的一个注解，在类上引用了该注解后，就能使得该类的每个属性具有get和set的方法
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) //该注解主要作用是：当属性字段在生成 getter 和 setter 方法时，做一些相关的设置,不写默认为false，当该值为 true 时，对应字段的 setter 方法调用后，会返回当前对象,这样可以进行链式调用
@ApiModel(value = "AdminLogin对象",description = "")
public class AdminLoginParam {

    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
