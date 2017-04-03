package com.tyler.service;

import com.tyler.dao.UserDAO;
import com.tyler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tyler on 2017/4/3.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    public User getUserById(int id){
        return userDAO.selectById(id);
    }
}
