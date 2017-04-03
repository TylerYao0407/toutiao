package com.tyler.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by tyler on 2017/4/2.
 */
@Controller
public class IndexController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping("/sss")
    @ResponseBody
    public String index(@RequestParam("username")String username){
        logger.info("Visit Index");
        return "Hello World"+"<br>"+username;
    }
    @RequestMapping("/news")
    public String news(ModelMap modelMap){
        List<String> list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("name","tyler");
        map.put("sex","男");
        list.add("name");
        list.add("password");
        list.add("sex");
        list.add("ok");
        modelMap.put("list",list);
        modelMap.put("name","asdasdasd");
        modelMap.put("map",map);
        return "news";
    }
    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        StringBuilder s = new StringBuilder("");
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            s.append(name).append(" ").append(request.getHeader(name)).append("<br>");
        }
        for(Cookie cookie:request.getCookies()){
            s.append("Cookie");
            s.append(cookie.getName());
            s.append("=");
            s.append(cookie.getValue());
        }
        return s.toString();
    }

    /**
     * 处理异常
     */
    @RequestMapping("admin")
    @ResponseBody
    public String admin(@RequestParam(value="admin", required = false) String admin){
        if("admin".equals(admin)){
            return "hello Admin";
        }
        throw new IllegalArgumentException("Password is error");
    }

    /**
     * 处理重定向
     */
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error"+e.getMessage();
    }
    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code){
        RedirectView red = new RedirectView("/",true);
        if(301 == code){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    /**
     *  只支持302临时重定向
     */
    @RequestMapping("/redirect")
    public String redirect1(){
        return "redirect:/";
    }

}
