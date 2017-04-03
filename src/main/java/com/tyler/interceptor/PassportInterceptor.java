package com.tyler.interceptor;

import com.tyler.dao.LoginTicketDAO;
import com.tyler.dao.UserDAO;
import com.tyler.model.HostHolder;
import com.tyler.model.LoginTicket;
import com.tyler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tyler on 2017/4/3.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        //先从Cookie中取ticket
        if(httpServletRequest.getCookies()!=null){
            for(Cookie cookie : httpServletRequest.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        //判断是否取到ticket
        if(ticket!=null){
            //通过这个ticket找数据库中的ticket
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            // 要是没有这个ticket(伪造的)、要是过期了、或者状态不是0（过期了），
            if(loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!= 0){
                //直接返回
                return true;
            }
            //要是有这个ticket、没过期、没失效，那通过这个ticket直接登录
            User user = userDAO.selectById(loginTicket.getUserId());
            //把这个通过ticket登录的用户装到本地线程中
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            //将用户传到前端
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
