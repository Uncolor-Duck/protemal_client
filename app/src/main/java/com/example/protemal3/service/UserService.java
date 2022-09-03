package com.example.protemal3.service;

import com.example.protemal3.bean.UserBean;

public interface UserService {
    public int add(UserBean userBean);
    public boolean checkById(String username, String password);

}
