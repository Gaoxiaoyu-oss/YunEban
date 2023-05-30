package com.gxy.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxy.server.pojo.Employee;
import com.gxy.server.pojo.RespBean;
import com.gxy.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface IEmployeeService extends IService<Employee> {

    /*
    * 获取所有员工(分页)
    * */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /*
    * 获取工号
    * */
    RespBean maxWorkId();

    /*
    * 添加员工
    * */
    RespBean addEmp(Employee employee);

    /*
    * 查询员工
    * */
    List<Employee> getEmployee(Integer id);

    /*
    * 获取所有工资账套
    * */
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
