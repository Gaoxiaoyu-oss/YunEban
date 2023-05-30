package com.gxy.server.pojo;

/*
* 分页公共返回对象
* */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {

    private Long total;//总条数
    private List<?> data;//数据

}
