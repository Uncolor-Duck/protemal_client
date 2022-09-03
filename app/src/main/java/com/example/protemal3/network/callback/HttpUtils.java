package com.example.protemal3.network.callback;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    private static final String nextLine = "\r\n";
    private static final String twoHyphens = "--";
    private static final String boundary = "android";

    public static String uploadFile(String uploadUrl, String path, String key) {
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        FileInputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            URL url = new URL(uploadUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            //设置请求参数格式以及boundary分割线
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            //传递头参
            /*httpURLConnection.setRequestProperty("userId", "1892");
            httpURLConnection.setRequestProperty("sessionId","15856368900771892");*/
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            File file = new File(path);
            dos.writeBytes(twoHyphens + boundary + nextLine);  //分隔符头部
            dos.writeBytes("Content-Disposition: form-data;name=" + key + ";" + "filename=\"" + file.getName() + "\"" + nextLine + nextLine);

            //读取文件并写入
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                dos.write(bytes, 0, length);
            }
            //文件写入完成后加回车
            dos.write(nextLine.getBytes());
            //写入结束分隔符
            String footer = nextLine + twoHyphens + boundary + twoHyphens + nextLine;
            dos.write(footer.getBytes());
            dos.flush();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), "utf-8");
                BufferedReader br = new BufferedReader(inputStreamReader);
                String result = br.readLine();
                Log.i("xiaosi", "uploaded image uuid : " + result);
                return result;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + uploadUrl);
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return "";
    }

}
