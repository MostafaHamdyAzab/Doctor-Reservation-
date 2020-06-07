package com.example.test_udemy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.channels.Channel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;

public class ReserveActivity extends Activity {
    private Button showdoctor_id = null;
    private Button doreserve = null;
    private EditText getdoctor_id = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);


        showdoctor_id = (Button) findViewById(R.id.showdoctor_id);

        showdoctor_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), ShowDoctor_idActivity.class);
                startActivity(i);

            }
        });
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "reservation confirmation";
            String description = "Your Reservation is Done";
            String CHANNEL_ID = "channel_1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            @SuppressLint("ServiceCast") NotificationManager nm = (NotificationManager) getSystemService(SEARCH_SERVICE);
            nm.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String doctor_id) {
       createNotificationChannel();
        try {
            DbHelper database = new DbHelper(getApplicationContext());
            SQLiteDatabase db = database.getWritableDatabase();
            String [] doctor_idlist={doctor_id};
            String [] selected_columlist={"doctor_name"};
            String colum="doctor_id"+ " = ? ";
            String dbdoctor_name="";
            Cursor c = db.query("doctors", selected_columlist,colum, doctor_idlist, null, null, null);
            while (c.moveToNext()) {
                  dbdoctor_name = c.getString(c.getColumnIndex("doctor_name"));

            }
          String CHANNEL_ID = "channel_1";
            NotificationCompat.Builder bulider = new NotificationCompat.Builder(this, CHANNEL_ID);
            bulider.setSmallIcon(R.drawable.app);
            bulider.setContentTitle("Done");
            bulider.setContentText("Reservation  Done With Doctor "+dbdoctor_name);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(1, bulider.build());

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void performReservation(View view) {
        getdoctor_id = findViewById(R.id.getdoctor_id);
        String doctor_idvalue = getdoctor_id.getText().toString();
        String i="";
        String doctor_idcount= getDoctor_IdCount().toString();
        boolean check = TextUtils.isDigitsOnly(doctor_idvalue);  //For Ensuring That doctor Id if Already An Integer Number
        if (check) {
            if (!doctor_idvalue.matches("")&& doctor_idvalue.equals(doctor_idcount)) { //doctor_idvalue is not null
                insertNew_Appointment(doctor_idvalue);
                sendNotification(doctor_idvalue);
            } else{ Toast.makeText(this, "Invalid Doctor Id", Toast.LENGTH_SHORT).show(); }

        } else { //doctor_idvalue is not String
            Toast.makeText(this, "Invalid Doctor Id", Toast.LENGTH_SHORT).show();
        }
        }

        public void insertNew_Appointment( String doctor_id ) {
        String user_id="";
            DbHelper database = new DbHelper(getApplicationContext());
            SQLiteDatabase db = database.getWritableDatabase();

            Bundle extras=getIntent().getExtras();
            if(extras!=null){
                user_id=extras.getString("the user id");
            }

            ContentValues values = new ContentValues();
            values.put("user_id", user_id);
            values.put("doctor_id", doctor_id);
            values.put("appointment_view", getDateTime());
            long row_num = db.insert("appointments", null, values);
            Toast.makeText(this, row_num + " ", Toast.LENGTH_LONG).show();
            if(row_num==-1){
                Toast.makeText(this,  "Invalid Id ", Toast.LENGTH_LONG).show();
            }

        }
        private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public Integer getDoctor_IdCount(){
        int count = 0;
        DbHelper database = new DbHelper(getApplicationContext());
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor c = db.query("doctors", null,null, null, null, null, null);


        while (c.moveToNext()) {
            count = c.getCount();
        }
     return count;
    }
    }





