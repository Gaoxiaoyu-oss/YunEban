package com.gxy.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxy.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface RoleMapper extends BaseMapper<Role> {
    /*
     * 根据用户Id,查询其对应的角色
     * */
    List<Role> getRoles(Integer adminId);
}
