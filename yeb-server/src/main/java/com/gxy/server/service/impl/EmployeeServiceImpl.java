package com.gxy.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxy.server.mapper.EmployeeMapper;
import com.gxy.server.mapper.MailLogMapper;
import com.gxy.server.pojo.*;
import com.gxy.server.service.IEmployeeService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gxy
 * @since 2023-04-08
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    //RabitMQ
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;

    /*
     * 获取所有员工(分页)
     * */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启MyBatisPlus的分页
        Page<Employee> page = new Page<>(currentPage,size);
        //查询数据
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page,employee,beginDateScope);
        //创建返回数据
        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal(),employeeByPage.getRecords());

        return respPageBean;
    }


    /*
     * 获取工号
     * */
    @Override
    public RespBean maxWorkId() {
        List<Map<String,Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));


        return RespBean.success(null,String.format("%08d",Integer.parseInt(maps.get(0).get("max(workID)").toString())+1));
    }

    /*
    * 添加员工
    * */
    @Override
    public RespBean addEmp(Employee employee) {
        //处理员工的合同期限，保留2位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        Long days = beginContract.until(endContract, ChronoUnit.DAYS);//相差天数
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if (employeeMapper.insert(employee) ==1){
            //消息id,数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            // 获取完整用户信息
            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);
            //RabbitMQ发送邮件
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));

            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败！");
    }

    /*
    * 查询员工
    * */
    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    /*
     * 获取所有工资账套
     * */
    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        //查询数据
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeWithSalary(page);
        // 封装并返回数据
        RespPageBean respPageBean = new RespPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());

        return respPageBean;
    }
}
