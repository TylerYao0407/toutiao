package com.tyler;

import com.tyler.dao.NewsDAO;
import com.tyler.model.News;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tyler on 2017/4/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsDAOTest {
    @Autowired
    private NewsDAO newsDAO;
    @Test
    public void insertNews(){
        for(int i = 0;i<10;i++){
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            News news = new News();
            news.setTitle(String.format("TITLE%d",i));
            news.setImage("http://lorempixel.com/300/300/");
            news.setCommentCount(i+1);
            news.setLikeCount(i+1);
            news.setCreatedDate(date);
            news.setLink("http://www.yaofuqiang.cn");
            news.setUserId(i+1);
            newsDAO.insertNews(news);
        }

    }
    @Test
    public void selectTest(){

        List<News> list = newsDAO.selectByUserIdAndOffset(0,0,10);
        for(News n : list){
            System.out.println(n.toString());
        }
    }
}
