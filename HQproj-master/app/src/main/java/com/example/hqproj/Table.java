package com.example.hqproj;

public class Table {
    public static final String CREATE_USER = "create table User_info(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_name VARCHAR(255) NOT NULL," +
            "user_password VARCHAR(255) NOT NULL" +
            ")";
}
