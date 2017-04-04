package com.tyler.controller;

import com.tyler.model.*;
import com.tyler.service.CommentService;
import com.tyler.service.NewsService;
import com.tyler.service.QiniuService;
import com.tyler.service.UserService;
import com.tyler.util.ToutiaoUtil;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tyler on 2017/4/4.
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private CommentService commentService;
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
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public String detail(@PathVariable("newsId") int newsId, Model model){
        try {
            News news = newsService.getById(newsId);
            if(news != null){
                List<Comment> comments = commentService.selectByEntity(newsId, EntityType.ENTITY_NEWS);
                List<ViewObject> commentVOs = new ArrayList<>();
                for(Comment comment : comments){
                    ViewObject vo = new ViewObject();
                    vo.set("comment",comment);
                    vo.set("user",userService.getUserById(comment.getUserId()));
                    commentVOs.add(vo);
                }
                model.addAttribute("comments",commentVOs);
            }
            model.addAttribute("news",news);
            model.addAttribute("owner",userService.getUserById(news.getUserId()));
            model.addAttribute("localUserId",hostHolder.getUser().getId());
        } catch (Exception e) {
            logger.error("获取资讯明细错误" + e.getMessage());
        }
        return "detail";
    }
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content){

        try {
            Comment comment = new Comment();
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setContent(content);
            comment.setUserId(hostHolder.getUser().getId());
            comment.setCreatedDate(new Date());
            comment.setStatus(0);

            commentService.addComment(comment);

            //更新评论数量
            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            newsService.updateCommentCount(comment.getEntityId(),count);
        } catch (Exception e) {
            logger.error("提交评论错误" + e.getMessage());
        }
        return "redirect:/news/"+String.valueOf(newsId);
    }
    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    @ResponseBody
    public String deleteComment(@RequestParam("commentId") int commentId,@RequestParam("newsId") int newsId){
       if(commentId>0){
           commentService.deleteComment(commentId);
       }
        int count = commentService.getCommentCount(newsId,EntityType.ENTITY_NEWS);
        newsService.updateCommentCount(newsId,count);
       return ToutiaoUtil.getJSONString(0);
    }
}
