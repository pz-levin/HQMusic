package com.example.hqproj.Activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hqproj.DB.MySQLiteOpenHelper;
import com.example.hqproj.R;

public class RegActivity extends AppCompatActivity {

    private EditText et_res_name;
    private EditText et_res_pwd;
    private EditText et_confirm_pwd;
    private String userName;
    private String userPwd;
    private String confirmPwd;
    private MySQLiteOpenHelper helper;
    private Button btn_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        helper = new MySQLiteOpenHelper(this,"HQdb",null,1);
        btn_reg = findViewById(R.id.user_register);
        et_res_name = findViewById(R.id.et_res_name);
        et_res_pwd = findViewById(R.id.et_res_pwd);
        et_confirm_pwd = findViewById(R.id.et_confirm_pwd);

        btn_reg.setOnClickListener(v -> {
            SQLiteDatabase db =helper.getWritableDatabase();
            userName = et_res_name.getText().toString();
            userPwd = et_res_pwd.getText().toString();
            confirmPwd = et_confirm_pwd.getText().toString();
            try {
                if(!userName.equals("") && !userPwd.equals("")){
                    Intent intent = new Intent();
                    if (!userPwd.equals(confirmPwd)){
                        Toast.makeText(this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        intent.putExtra("reg_userName",userName);
                        intent.putExtra("reg_userPwd",userPwd);
                        setResult(RESULT_OK,intent);
                    }
                    finish();
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "用户名/密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }catch (SQLException e){
                setTitle("注册失败！");
            }
        });
    }
}