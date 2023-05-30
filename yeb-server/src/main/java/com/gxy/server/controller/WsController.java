package com.gxy.server.controller;


import com.gxy.server.pojo.Admin;
import com.gxy.server.pojo.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg){
        //获取用户信息
        Admin admin = (Admin) authentication.getPrincipal();
        //设置消息属性
        chatMsg.setFrom(admin.getUsername());
        chatMsg.setFormNickName(admin.getName());
        chatMsg.setDate(LocalDateTime.now());

        //发送
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"queue/chat",chatMsg);
    }

}
