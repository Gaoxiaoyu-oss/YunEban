package com.gxy.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxy.server.mapper.DepartmentMapper;
import com.gxy.server.pojo.Department;
import com.gxy.server.pojo.RespBean;
import com.gxy.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /*
    * 获取所有部门
    * */
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }


    /*
     * 添加部门
     * */
    @Override
    public RespBean addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
        if(dep.getResult() == 1){
            return RespBean.success("添加成功",dep);
        }
        return RespBean.error("添加失败！");
    }


    /*
    * 删除部门
    * */
    @Override
    public RespBean deleteDep(Integer id) {
        Department dep =new Department();
        dep.setId(id);
        departmentMapper.deleteDep(dep);
        if(dep.getResult()==-2){
            return RespBean.error("该部门下还有子部门，删除失败！");
        } else if (dep.getResult()==-1) {
            return RespBean.error("该部门下还有员工，删除失败！");
        } else if (dep.getResult()==1) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败！");
    }
}
