package com.example.test_udemy;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.Barrier;

public class ProfileActivity extends Activity {
   private Button reserve=null;
   private  TextView wellcomemessage=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        wellcomemessage=(TextView)findViewById(R.id.wellcomemessage);

        Intent user_data_intent=this.getIntent();
       // Integer user_id=user_data_intent.getExtras().getInt("theuser_id");
        String user_name=user_data_intent.getExtras().getString("theuser_name");
        wellcomemessage.setText(" Wellcome "+user_name);

        reserve=(Button)findViewById(R.id.reserve);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),ReserveActivity.class);
                startActivity(i);
     }
        });
    }
    //It is not work
    public void showUserReservation(String theuser_id){
        String[] user_idf = {theuser_id};
        String coulum="user_id" + " = ? ";
        Integer dbdoctor_id;
        String dbdoctor_address="";
        String dbappointment_view="";

        DbHelper database = new DbHelper(getApplicationContext());
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor c = db.query("appointments", null,coulum, user_idf, null, null, null);

        while (c.moveToNext()) {
            dbdoctor_address = c.getString(c.getColumnIndex("doctor_address"));
            dbdoctor_id = c.getInt(c.getColumnIndex("doctor_id"));
            dbappointment_view = c.getString(c.getColumnIndex("appointment_view"));
            Toast.makeText(this, dbdoctor_address+" "+dbappointment_view, Toast.LENGTH_SHORT).show(); }
    }
}
