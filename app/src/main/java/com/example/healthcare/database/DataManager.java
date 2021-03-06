package com.example.healthcare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 3;

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Thuoc ( id INTEGER, ten TEXT, lieuluong	INTEGER, donvi	TEXT, truocsau	TEXT,  PRIMARY KEY(id) )";
        db.execSQL(sql);
        sql = "CREATE TABLE UongThuoc (id INTEGER , idthuoc INTEGER, buoi TEXT, PRIMARY KEY(id))";
        db.execSQL(sql);
        sql = "CREATE TABLE TrieuChung (ngay int, mota TEXT, taikham INTEGER, PRIMARY KEY(ngay))";
        db.execSQL(sql);
        sql = "CREATE TABLE HuyetAp (ngay INTEGER, min INTEGER, max INTEGER, PRIMARY KEY(ngay))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS Thuoc");
        db.execSQL("DROP TABLE IF EXISTS UongThuoc");
        db.execSQL("DROP TABLE IF EXISTS TrieuChung");
        db.execSQL("DROP TABLE IF EXISTS HuyetAp");
        //Tiến hành tạo bảng mới
        onCreate(db);
    }


}