package com.tyler.controller;

import com.tyler.model.HostHolder;
import com.tyler.model.News;
import com.tyler.service.NewsService;
import com.tyler.service.QiniuService;
import com.tyler.util.ToutiaoUtil;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

/**
 * Created by tyler on 2017/4/4.
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private NewsService newsService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private HostHolder hostHolder;
    @RequestMapping(value = "/uploadImage/", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try{
            String fileUrl =  qiniuService.saveImage(file);
            if(fileUrl == null){
                return ToutiaoUtil.getJSONString(0,"图片上传失败");
            }
            return ToutiaoUtil.getJSONString(0,fileUrl);
        } catch(Exception e) {
            logger.error("图片上传失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"图片上传失败");
        }
    }
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @ResponseBody
    public void getImage(@RequestParam("name") String name, HttpServletResponse response) {
        try{
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.UPLOAD_DIR+name)),response.getOutputStream());

        } catch (Exception e){
            e.printStackTrace();
            logger.error("读取图片错误",e.getMessage());
        }
    }
    @RequestMapping(value = "/user/addNews/", method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link){

        try{
            News news = new News();
            if(hostHolder.getUser()!=null){
                news.setUserId(hostHolder.getUser().getId());
            }else {
                news.setUserId(3);
//                return ToutiaoUtil.getJSONString(1,"您尚未登录");
            }
            news.setLink(link);
            news.setCreatedDate(new Date());
            news.setImage(image);
            news.setTitle(title);
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0,"发布成功");

        }catch (Exception e){
            logger.error("添加资讯出错"+e.getMessage());
            return ToutiaoUtil.getJSONString(1, "发布失败");
        }

    }
}
