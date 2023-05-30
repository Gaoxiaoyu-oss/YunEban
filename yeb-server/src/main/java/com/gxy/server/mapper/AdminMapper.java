package com.gxy.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxy.server.pojo.Admin;
import com.gxy.server.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
public interface AdminMapper extends BaseMapper<Admin> {


    /*
    * 获取所有操作员
    * */
    List<Admin> getAllAdmins(@Param("id") Integer currentId, @Param("keywords") String keywords);
}
