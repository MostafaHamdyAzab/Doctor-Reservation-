package com.example.test_udemy;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity {
    private EditText user_name = null;
    private EditText user_password = null;
    private EditText user_email = null;
    private Button doregister = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_register);
    }


    public void insert(View v) {
        user_name = findViewById(R.id.user_name);
        user_password = (EditText) findViewById(R.id.password);
        user_email = findViewById(R.id.user_email);

        String user_name_value = user_name.getText().toString();
        String user_password_value = user_password.getText().toString();
        String user_email_value = user_email.getText().toString();


        if(checkUser_Data(user_name_value)) {
            DbHelper database = new DbHelper(getApplicationContext());
            SQLiteDatabase db = database.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("user_name", user_name_value);
            values.put("user_password", user_password_value);
            values.put("user_email", user_email_value);
            long row_num = db.insert("users", null, values);
            if(row_num != -1){
                Toast.makeText(this, " Now You Can Register"  , Toast.LENGTH_LONG).show();
            }
                db.close();
           }else{
            Toast.makeText(this, " " + "Invalid User Name", Toast.LENGTH_LONG).show();
        }
        }



        public boolean checkUser_Data(String username) {
            String[] user_namef = {username};
            String coulum="user_name" + " = ? ";

            int count = 0;
            DbHelper database = new DbHelper(getApplicationContext());
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor c = db.query("users", null,coulum, user_namef, null, null, null);

            while (c.moveToNext()) {
                count = c.getCount();
            }
            if (count == 0) {        // User data is unique
                return true;
            } else{                  //user data is not unique
               return false ;}
        }



    }



