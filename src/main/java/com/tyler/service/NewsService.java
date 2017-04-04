package com.tyler.service;

import com.tyler.dao.NewsDAO;
import com.tyler.model.News;
import com.tyler.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

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
    public News getById(int newsId){
        return newsDAO.getById(newsId);
    }
    public int addNews(News news) {
        return newsDAO.insertNews(news);
    }
    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }
    public String saveImage(MultipartFile file) throws IOException{
        int lastPoint = file.getOriginalFilename().lastIndexOf(".");
        if(lastPoint<0){
            return null;
        }
        String imageExt = file.getOriginalFilename().substring(lastPoint+1).toLowerCase();
        if (!ToutiaoUtil.isImage(imageExt)) {
            return null;
        }
        //获取文件名
        String fileName = UUID.randomUUID().toString().replace("-","")+"."+imageExt;
        //上传
        Files.copy(file.getInputStream(),new File(ToutiaoUtil.UPLOAD_DIR+fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOUTIAO_DOMAIN+"image?name="+fileName;
    }
}
