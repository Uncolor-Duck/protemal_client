package com.example.protemal3.bean;

import java.util.Date;

public class UserBean {
    private int Id;
    private String username;
    private String password;
    private Date createDate;

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
        this.createDate = new Date();
    }

    public int getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
