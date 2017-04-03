package com.tyler.service;

import com.tyler.dao.LoginTicketDAO;
import com.tyler.dao.UserDAO;
import com.tyler.model.LoginTicket;
import com.tyler.model.User;
import com.tyler.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by tyler on 2017/4/3.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;
    public User getUserById(int id){
        return userDAO.selectById(id);
    }
    public Map<String, Object> register(String username,String password){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user != null){
            map.put("msgname","用户名已经存在了");
            return map;
        }
        //密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }
    public Map<String, Object> login(String username,String password){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user == null){
            map.put("msgname","用户名不存在了");
            return map;
        }
        if(!user.getPassword().equals(ToutiaoUtil.MD5(password+user.getSalt()))){
            map.put("msgpwd","密码不正确");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setStatus(0);
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime()+ 1000*3600*24);
        ticket.setExpired(date);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }
}
