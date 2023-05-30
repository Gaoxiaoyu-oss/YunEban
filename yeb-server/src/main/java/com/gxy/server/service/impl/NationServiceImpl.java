package com.gxy.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxy.server.mapper.NationMapper;
import com.gxy.server.pojo.Nation;
import com.gxy.server.service.INationService;
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
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
