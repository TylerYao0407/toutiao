package com.tyler.controller;

import com.tyler.model.News;
import com.tyler.model.User;
import com.tyler.model.ViewObject;
import com.tyler.service.NewsService;
import com.tyler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyler on 2017/4/3.
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList =  newsService.getLatestNews(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(News news : newsList){
            ViewObject vo = new ViewObject();
            vo.set("news",news);
            vo.set("user",userService.getUserById(news.getId()));
            vos.add(vo);
        }
        return vos;
    }
    @RequestMapping(value = "/", method ={RequestMethod.GET,RequestMethod.POST} )
    public String index(Model model){
        model.addAttribute("vos",getNews(0,0,10));
        return "home";
    }
    @RequestMapping(value = "/user/{userId}", method ={RequestMethod.GET,RequestMethod.POST} )
    public String index(Model model, @PathVariable("userId")int userId){
        model.addAttribute("vos",getNews(userId,0,10));
        return "home";
    }
}
