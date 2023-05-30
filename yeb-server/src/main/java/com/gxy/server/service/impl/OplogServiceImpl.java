package com.gxy.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxy.server.mapper.OplogMapper;
import com.gxy.server.pojo.Oplog;
import com.gxy.server.service.IOplogService;
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
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
