package com.example.test_udemy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.datatype.Duration;


public class MainActivity extends AppCompatActivity {

    private Button gotosingnup=null;
    private Button login_button=null;
    private EditText user_name=null;
    private EditText user_password=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotosingnup=(Button) this.<View>findViewById(R.id.show_signup);


        gotosingnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
         }


        public void login(View v ) {

                user_name = findViewById(R.id.user_name);
                user_password = findViewById(R.id.user_password);
                String user_namev = user_name.getText().toString();
                String user_passwordv = user_password.getText().toString();
                boolean flag=false;


                    try {
                    String dbuser_name = "";
                    String dbuser_password = "";
                    Integer dbuser_id;
                        DbHelper database = new DbHelper(getApplicationContext());
                        SQLiteDatabase db = database.getReadableDatabase();
                        Cursor c = db.query("users", null, null, null, null, null, null);

                    while (c.moveToNext()) {

                        dbuser_id = c.getInt(c.getColumnIndex("user_id"));
                        dbuser_name = c.getString(c.getColumnIndex("user_name"));
                        dbuser_password = c.getString(c.getColumnIndex("user_password"));

                        if (dbuser_name.equals(user_namev) && dbuser_password.equals(user_passwordv)) {
                            Intent i = new Intent(v.getContext(), ProfileActivity.class);
                            i.putExtra("theuser_name", dbuser_name);
                            startActivity(i);
                            Intent senduser_idintent = new Intent(v.getContext(), ReserveActivity.class);
                            senduser_idintent.putExtra("the user id",dbuser_id);
                            db.close();
                            c.close();
                            flag=true;
                            break; }
                        }if(!flag){
                            Toast.makeText(this, "Invalid Name Or Password", Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    Toast.makeText(this, " " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


        }

}