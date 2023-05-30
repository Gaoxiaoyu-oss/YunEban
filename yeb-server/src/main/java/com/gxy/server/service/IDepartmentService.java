package com.gxy.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxy.server.pojo.Department;
import com.gxy.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface IDepartmentService extends IService<Department> {

    /*
    * 获取所有部门
    * */
    List<Department> getAllDepartments();

    /*
    * 添加部门
    * */
    RespBean addDep(Department dep);


    /*
    * 删除部门
    * */
    RespBean deleteDep(Integer id);
}
