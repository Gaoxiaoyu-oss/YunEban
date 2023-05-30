package com.gxy.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxy.server.pojo.RespBean;
import com.gxy.server.pojo.MenuRole;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 */
public interface IMenuRoleService extends IService<MenuRole> {

	/**
	 * 更新角色菜单
	 * @param rid
	 * @param mids
	 * @return
	 */
	RespBean updateMenuRole(Integer rid, Integer[] mids);
}
