package com.tyler;

import com.tyler.dao.UserDAO;
import com.tyler.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")
public class UserDAOTests {
	@Autowired
	private UserDAO userDAO;
	@Test
	public void insertUserTest() {
		for(int i = 0;i<10;i++){
			User user = new User();
			user.setName(String.format("USER%d",i));
			user.setHeadUrl("http://lorempixel.com/200/200/");
			user.setSalt("");
			user.setPassword("");
			userDAO.addUser(user);
		}
	}
	@Test
	public void selectUserTest(){
		User user = userDAO.selectById(9);
		System.out.println(user.toString());
	}
	@Test
	public void updateUserTest(){
		User user = new User();
		user.setId(9);
		user.setPassword("987654321");
		userDAO.updatePassword(user);
	}
	@Test
	public void deleteByIdTest(){
		userDAO.deleteById(10);
	}

}
