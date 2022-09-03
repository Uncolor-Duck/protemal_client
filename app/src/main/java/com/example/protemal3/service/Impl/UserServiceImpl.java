package com.example.protemal3.service.Impl;

import com.example.protemal3.bean.UserBean;
import com.example.protemal3.dao.Impl.UserDaoImpl;
import com.example.protemal3.dao.UserDao;
import com.example.protemal3.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public int add(UserBean userBean) {
        System.out.println("addservice is running");
        return userDao.add(userBean);
    }

    @Override
    public boolean checkById(String username, String password) {
        return userDao.checkById(username, password);
    }
}
