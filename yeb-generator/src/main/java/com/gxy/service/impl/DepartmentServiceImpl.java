package com.gxy.service.impl;

import com.gxy.pojo.Department;
import com.gxy.mapper.DepartmentMapper;
import com.gxy.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
