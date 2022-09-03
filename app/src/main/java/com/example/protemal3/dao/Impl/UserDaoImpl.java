package com.example.protemal3.dao.Impl;

import com.alibaba.fastjson2.JSON;
import com.example.protemal3.bean.UserBean;
import com.example.protemal3.dao.UserDao;
import okhttp3.*;

import javax.xml.transform.Source;
import java.io.IOException;

public class UserDaoImpl implements UserDao {
    @Override
    public int add(UserBean userBean) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(userBean));
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://43.142.105.173:8080/FirstWar/usermanager/add")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        System.out.println("adddao is running");
        return 0;
    }

    @Override
    public boolean checkById(String username, String password) {
        return false;
    }
}
