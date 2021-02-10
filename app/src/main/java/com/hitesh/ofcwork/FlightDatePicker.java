package com.hitesh.ofcwork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FlightDatePicker extends AppCompatActivity {
    TextView date;
    Button btn;
    int y,m,d;
    String TAG="project";
    String dDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_date_picker);
        date =findViewById(R.id.date);

        btn=findViewById(R.id.btn);
        final Calendar calendar=Calendar.getInstance();


        //date picker
       date.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               d=calendar.get(Calendar.DAY_OF_MONTH);
               m=calendar.get(Calendar.MONTH);
               y=calendar.get(Calendar.YEAR);

               DatePickerDialog datePickerDialog=new DatePickerDialog(FlightDatePicker.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

                       // input date to calendar date
                       Calendar c=Calendar.getInstance();
                       c.set(Calendar.YEAR,year);
                       c.set(Calendar.MONTH,month);
                       c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                       Date d=c.getTime();
                        // date formating
                       SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");

                       dDate=format.format(d);
                       Log.d(TAG, "onDateSet: "+(year + "-" + (month + 1) + "-" + dayOfMonth));
                   }
               },y, m, d);
               datePickerDialog.show();
           }
       });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCreate: "+date.getText().toString());
                startActivity(new Intent(FlightDatePicker.this,Flights.class).putExtra("date",dDate));
            }
        });

    }

}
