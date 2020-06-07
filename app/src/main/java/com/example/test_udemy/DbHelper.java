package com.example.test_udemy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class DbHelper extends SQLiteOpenHelper {
    Context c;
    public static final String Db_name= "Resrvation";
    public static final int Db_version= 1;
    View v;

    public DbHelper(Context c) {
       super(c,Db_name,null,Db_version);
        this.c=c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql0="create table users(" +
                "user_id integer primary key autoincrement ,"+
                "user_name varchar(35) ,"+
                "user_password varchar(30) ,"+
                "user_email varchar(30))";
        db.execSQL(sql0);

        String sql1="create table doctors(" +
                "doctor_id integer primary key autoincrement  ,"+
                "doctor_name varchar(30) ,"+
                "doctor_address varchar(30))";
        db.execSQL(sql1);

//        String sql2="create table appointments(" +
//                "appointment_id integer primary key autoincrement ,"+
//                "user_id integer ,"+
//                "doctor_id integer ,"+
//                "appointment_view text ,"+
//                " FOREIGN KEY(doctor_id) REFERENCES doctors(doctor_id),"+
//               " FOREIGN KEY(user_id) REFERENCES users(user_id))";
//             db.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newnersion) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists doctors");

//        db.execSQL("drop table if exists appointments");
         onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }
}
