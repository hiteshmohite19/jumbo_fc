package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Flights extends AppCompatActivity implements  RecyclerViewAdapter.GetMonth {

    String TAG="project";
    String selectedDate;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    TextView tvMonth,tvYear,tvDate;
    ArrayList<DateList> dateList;
    int price=3000;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        dateList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        tvMonth=findViewById(R.id.month);
        tvYear=findViewById(R.id.yearVal);
        tvDate=findViewById(R.id.date);

        Bundle bundle=getIntent().getExtras();
        selectedDate=bundle.getString("date");
        Log.d(TAG, "asdasd "+selectedDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData(){

        Calendar calendar=Calendar.getInstance();

        for(int j=0;j<=365;j++){

            String date,day,month,year;
            Date d=calendar.getTime();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String date1=simpleDateFormat.format(d);
//            Log.d(TAG, "date : "+date+" "+selectedDate);

            String dt=d.toString();
            calendar.add(Calendar.DATE,1);
            day=dt.substring(0,3);
            date=dt.substring(8,10);
            date.replace("-","0");
            month=dt.substring(4,7);
            year=dt.substring(dt.length()-4);

            if(date1==selectedDate){
                pos=j;
            }

            DateList dateList1 = new DateList();
            dateList1.setDate(date);
            dateList1.setDay(day);
            dateList1.setMonth(month);
            dateList1.setYear(year);
            dateList1.setPrice(price);

            dateList.add(dateList1);
            price++;

        }


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerViewAdapter=new RecyclerViewAdapter(dateList,selectedDate,this,getApplicationContext(),linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.scrollToPosition(pos);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

    }


    @Override
    public void monthPosition(int month) {
        tvMonth.setText(dateList.get(month).getMonth().toString());
    }

    @Override
    public void getYear(String year) {
        tvYear.setText(year);
    }
}
