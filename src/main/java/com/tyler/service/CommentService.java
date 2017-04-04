package com.tyler.service;

import com.tyler.dao.CommentDAO;
import com.tyler.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tyler on 2017/4/4.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;
    public List<Comment> selectByEntity(int entityId,int entityType){
        return commentDAO.selectByEntity(entityId,entityType);
    }
    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }
    public int getCommentCount(int entityId,int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }
    public void deleteComment(int commentId){
        commentDAO.updateStatus(commentId);
    }
}
