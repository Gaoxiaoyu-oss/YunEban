package com.gxy.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.gxy.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /*
    * 获取所有部门
    * */
    List<Department> getAllDepartments(Integer i);

    /*
     * 添加部门
     * */
    void addDep(Department dep);

    /*
    * 删除部门
    * */
    void deleteDep(Department dep);
}
