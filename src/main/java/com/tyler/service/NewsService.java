package com.tyler.service;

import com.tyler.dao.NewsDAO;
import com.tyler.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tyler on 2017/4/3.
 */
@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;
    public List<News> getLatestNews(int userId,int offset,int limit){
        return newsDAO.selectByUserIdAndOffset(userId,offset,limit);
    }
}
