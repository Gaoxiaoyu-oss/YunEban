package com.gxy.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxy.server.pojo.AdminRole;
import com.gxy.server.pojo.RespBean;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /*
     * 更新操作员的角色
     * */
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
