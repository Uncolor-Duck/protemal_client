package com.example.protemal3.network;

import android.os.Build;

import androidx.annotation.RequiresApi;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.example.protemal3.network.callback.OkHttpRequestCallBack;
import okhttp3.*;

/**
 * Author:一条屈依
 * Date:2021/5/31
 * Blog:https://blog.csdn.net/weixin_44758662
 */
public class OkHttpUtil {
    private OkHttpClient client;
    private static final String BASE_URL = "http://43.142.105.173:8080/FirstWar/"; //请求接口根地址
    private static Logger logger = Logger.getLogger(String.valueOf(OkHttpUtil.class));
    private static volatile OkHttpUtil mInstance;//单利引用

    public OkHttpUtil() {
        client = new OkHttpClient();
    }

    public static OkHttpUtil getInstance() {
        OkHttpUtil util = mInstance;
        if (util == null) {
            synchronized (OkHttpUtil.class) {
                util = mInstance;
                if (util == null) {
                    util = new OkHttpUtil();
                    mInstance = util;
                }
            }
        }
        return util;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public <T> void demo(String actionUrl, String filePath, final OkHttpRequestCallBack callBack) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .build();
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json, "json字符串");
        Request request = new Request.Builder().post(body).url("http://43.142.105.173:8080/FirstWar/"+actionUrl).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                System.out.println("成功");
            }
        });

    }

    /**
     * 上传文件
     *
     * @param actionUrl 接口地址
     * @param filePath  本地文件地址
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public <T> void upLoadFile(String actionUrl, String filePath, String UserName, final OkHttpRequestCallBack callBack) {
        File file = new File(filePath);
        String postUrl = "http://43.142.105.173:8080/FirstWar";
        postUrl += "/" + actionUrl;
        // 定义图片文件解析，下面的 * 代表的是要上传的图片的格式，比如：png、jpg、JPEG等等
        MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);
        MultipartBody.Builder multiBuilder=new MultipartBody.Builder();
        HashMap<String, String> params = new HashMap<>();
        params.put("client","Android");
        params.put("uid","1061");
        params.put("token","1911173227afe098143caf4d315a436d");
        params.put("uuid","A000005566DA77");
        //参数以添加header方式将参数封装，否则上传参数为空
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                multiBuilder.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
        RequestBody multipartBody = multiBuilder
                .setType(MultipartBody.FORM)
                // 这里设置要传给后台的参数；如果要添加多个参数，可以先获取到MultipartBody.Builder对象，然后再for循环添加
                .addFormDataPart("orderId", UserName)
                // 约定key 如 "certificate" 作为后台接受图片的key；这里约定的key是：certificate
                .addFormDataPart("image", file.getName(), requestBody)
                .build();
        //构建Request请求体
        Request.Builder RequestBuilder = new Request.Builder();
        Request request = RequestBuilder
                // 添加URL地址
                .url(postUrl)
                .post(multipartBody)
                .build();
        // 构建 OkHttpClient 请求对象，后面大家都很熟悉了，不多说
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                System.out.println("成功");
            }
        });

    }
}