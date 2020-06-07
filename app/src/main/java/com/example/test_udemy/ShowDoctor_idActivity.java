package com.example.test_udemy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDoctor_idActivity  extends Activity {
    private TextView dbdoctor_id=null;
    private TextView dbdoctor_name=null;
    private TextView dbdoctor_address=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdoctor_id);

        dbdoctor_id=findViewById(R.id.dbDoctor_id);
        DbHelper database=new DbHelper(getApplicationContext());
//
//            SQLiteDatabase db = database.getWritableDatabase();
//            ContentValues values=new ContentValues();
//            values.put("doctor_name","Amr");
//            values.put("doctor_address","13-st15-Nasr_city");
//            long row_num=  db.insert("doctors",null,values);
//            Toast.makeText(this,row_num+" ",Toast.LENGTH_LONG).show();
       getDoctorData();
    }
    public void getDoctorData() {
        dbdoctor_id=findViewById(R.id.dbDoctor_id);
        dbdoctor_name=findViewById(R.id.dbDoctor_name);
       dbdoctor_address=findViewById(R.id.dbDoctor_address);


            DbHelper database = new DbHelper(getApplicationContext());
            String dbdoctor_id_value="";
            String dbdoctor_name_value = "";
            String dbdoctor_address_value="";
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor c = db.query("doctors", null, null, null, null, null, null);

            while (c.moveToNext()) {

                dbdoctor_id_value = c.getString(c.getColumnIndex("doctor_id"));
                dbdoctor_name_value = c.getString(c.getColumnIndex("doctor_name"));
                dbdoctor_address_value= c.getString(c.getColumnIndex("doctor_address"));

                dbdoctor_id.setText(dbdoctor_id_value);
                dbdoctor_name.setText(dbdoctor_name_value);
                dbdoctor_address.setText(dbdoctor_address_value);

            }


    }
}
