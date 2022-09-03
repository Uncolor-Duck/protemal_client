package com.example.protemal3;

import com.alibaba.fastjson2.JSON;
import com.example.protemal3.bean.UserBean;
import com.example.protemal3.service.Impl.UserServiceImpl;
import com.example.protemal3.service.UserService;
import org.junit.Test;

import javax.xml.transform.Source;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Test1() {
        UserBean userBean = new UserBean("duansy", "123123");
        String JsonString = JSON.toJSONString(userBean);
        System.out.println(JsonString);
    }
}