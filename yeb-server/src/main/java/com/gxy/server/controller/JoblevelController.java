package com.gxy.server.controller;


import com.gxy.server.pojo.Joblevel;
import com.gxy.server.pojo.RespBean;
import com.gxy.server.service.IJoblevelService;
import com.gxy.server.service.impl.JoblevelServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJobLevels(){
        return joblevelService.list();
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)){
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "更新职位")
    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody Joblevel joblevel){
        if (joblevelService.updateById(joblevel)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除职位")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable Integer id){
        if (joblevelService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation(value = "批量删除职位")
    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids){
        if (joblevelService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("批量删除成功!");
        }
        return RespBean.error("批量删除失败！");
    }

}
