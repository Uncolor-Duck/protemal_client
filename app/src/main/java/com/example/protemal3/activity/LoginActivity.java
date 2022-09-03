package com.example.protemal3.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.app.ActivityCompat;
import com.example.protemal3.bean.UserBean;
import com.example.protemal3.service.Impl.UserServiceImpl;
import com.example.protemal3.service.UserService;

public class LoginActivity extends AppCompatActivity {
    EditText lg_name,lg_psw;
    Button btn_login;
    Button btn_register;
    SQLiteDatabase db;

    Button btn_test;
    UserService userService = new UserServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readRequest();
        setContentView(R.layout.activity_login);
        lg_name=findViewById(R.id.lg_name);
        lg_psw=findViewById(R.id.lg_psw);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        btn_test=findViewById(R.id.btn_test);


        btn_test.setOnClickListener(view ->{
            new Thread(() -> {
                userService.add(new UserBean("duansy", "hahaha"));
                System.out.println("新进程使用成功！");
            }).start();
        });


        //登录注册部分数据库
        db=SQLiteDatabase.openOrCreateDatabase(getCacheDir()+"/note",null);
        try {
            db.execSQL("create table user(username varchar(100),password varchar(100))");
        } catch (Exception e){
            e.printStackTrace();
        }
        //登录注册的储存数据库初始化
        try{
            db.execSQL("create table login(username varchar(100),password varchar(100))");
        } catch (Exception e){
            e.printStackTrace();
        }

        //调用login sqlite获取之前登录的数据
        reLogin();

        SharedPreferences sharedPreferences=getSharedPreferences("user",0);
        lg_name.setText(sharedPreferences.getString("lg_name",""));
        lg_psw.setText(sharedPreferences.getString("lg_psw",""));
        btn_login.setOnClickListener(view -> {
            if (lg_name.getText().toString().equals("") || lg_psw.getText().toString().equals("")){
                Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from user where username='"+lg_name.getText().toString()+"'",null);
            if (cursor.moveToNext()){
                if (cursor.getString(1).equals(lg_psw.getText().toString())){
                    SharedPreferences.Editor editor=getSharedPreferences("lg_name",0).edit();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    editor.apply();
                }else {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
            }
        });
        btn_register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));
    }

    public void reLogin(){
        Cursor reUser = db.rawQuery("select * from login", null);
        if(reUser.moveToNext()){
            System.out.println("Hello");

            //System.out.println(reUser.getString(1));rePwd
            //System.out.println(reUser.getString(0));reID
            @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from user where username='"+reUser.getString(0)+"'",null);
            if (cursor.moveToNext()){
                if (cursor.getString(1).equals(reUser.getString(1))){
                    SharedPreferences.Editor editor=getSharedPreferences("lg_name",0).edit();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    editor.apply();
                }else {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
            }
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    void readRequest() {             //获取相机拍摄读写权限
        System.out.println("我正在获取权限");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            }
        }
    }

}