package com.hitesh.ofcwork;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class VerticalDatePicker extends AppCompatActivity {

    private static final String TAG = "project";
    private EditText departure,arrival;
    String departureVal,arrivalVal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vertical_date_picker);

        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);

        Button done=findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDone();
            }
        });

        calendarPickerView();
    }

    void calendarPickerView(){
        Date today=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);
        Log.d(TAG, "calendarPickerView: "+today);
        departure.setText(dateConverter(today.toString()));

        CalendarPickerView pickerView=findViewById(R.id.calendar_picker_view);

        pickerView.init(today,calendar.getTime()).withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        pickerView.setBackgroundColor(getResources().getColor(R.color.white));
        pickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String dDate=date.toString();
                dDate=dateConverter(dDate);
                setBoth(dDate);
                Toast.makeText(VerticalDatePicker.this, dDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }


    void setBoth(String date){
        departureVal=departure.getText().toString();
        arrivalVal=arrival.getText().toString();
        if(departureVal==null){
            departureVal=date;
            departure.setText(date);
        }
        else if(!departureVal.isEmpty() && arrivalVal.isEmpty()){
            arrivalVal=date;
            arrival.setText(date);
        }
        else if(departureVal!=null && arrivalVal!=null){
            departureVal=date;
            departure.setText(date);
            arrival.setText(null);
        }
    }

    String dateConverter(String date){
        String day=date.substring(0,3);
        String month=date.substring(4,7);
        String dDate=date.substring(8,10);
        String year=date.substring(date.length()-4);

        return day+", "+dDate+" "+month;
    }

    void onClickDone(){
        startActivity(new Intent(getApplicationContext(),DatePicker.class).putExtra("departure",departureVal).putExtra("arrival",arrivalVal));
    }
}