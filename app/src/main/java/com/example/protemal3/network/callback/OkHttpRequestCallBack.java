package com.example.protemal3.network.callback;

import java.io.IOException;
import okhttp3.Response;

public interface OkHttpRequestCallBack {
    /**
     * 响应成功
     */
    void onReqSuccess(Response response) throws IOException;

    /**
     * 响应失败
     */
    void onReqFailed(IOException e);
}
