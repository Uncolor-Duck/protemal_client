package com.example.protemal3.dao;

import com.example.protemal3.bean.UserBean;

public interface UserDao {
    public int add(UserBean userBean);

    public boolean checkById(String username, String password);

}
