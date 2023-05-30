package com.gxy.service.impl;

import com.gxy.pojo.Admin;
import com.gxy.mapper.AdminMapper;
import com.gxy.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
