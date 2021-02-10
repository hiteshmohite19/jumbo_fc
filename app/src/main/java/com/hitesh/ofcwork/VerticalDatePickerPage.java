package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class VerticalDatePickerPage extends AppCompatActivity {

    String TAG="project";
    RecyclerView recyclerView;
    Calendar calendar;
    String[] days={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    VerticalViewAdapter verticalViewAdapter;
    String mMonth="";
    ArrayList<String> m=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_date_picker_page);

        recyclerView=findViewById(R.id.recycler);

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerData();
    }


    void recyclerData(){
        String date="",day,month,year;
        int price=3000;
        ArrayList dayList,dateList,monthList,yearList,priceList;
        ArrayList newList=new ArrayList();
        ArrayList max_day=new ArrayList();
        dayList=new ArrayList<>();
        dateList=new ArrayList<>();
        monthList=new ArrayList<>();
        yearList=new ArrayList<>();
        priceList=new ArrayList<>();

        calendar= Calendar.getInstance();
//        Log.d(TAG, "recyclerData: "+calendar.getTime()+" : "+getDate());
//        Log.d(TAG, "recyclerData: 2 "+ calendar.getTime());

        for(int j=0;j<=70;j++){
            Date d=calendar.getTime();
            String dt=d.toString();

//            Log.d(TAG, "recyclerData: 3 "+calendar.getTime());
            day=dt.substring(0,3);
            date=dt.substring(8,10);
            month=dt.substring(4,7);

            year=dt.substring(dt.length()-4);
//            Log.d(TAG, "recyclerData: 4 "+day+" "+date+" "+month+" "+year);
            dayList.add(day);
            dateList.add(date);
            monthList.add(month);
            yearList.add(year);
            priceList.add(price);
            price++;
//            Log.d(TAG, "recyclerData: "+date);
            Log.d(TAG, "recyclerData: "+date);

//            if(mMonth!=month){
//                mMonth=month;
////                Log.d(TAG, "month value : "+mMonth);
//                m.add(mMonth);
//            }
            calendar.add(Calendar.DATE,1);
        }
//        int count=0;
//        int c=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        for(int i=0;i<3;i++){
//            if(count==0){
//                max_day.add(c-Integer.parseInt(date));
//                count++;
//            }
//            else {
//                max_day.add(c);
//            }
//
//        }
//
//        for (String element : m) {
//            if (!newList.contains(element)) {
//                newList.add(element);
//            }
//        }

        Log.d(TAG, "array of month :=> "+dateList.size());
//        Log.d(TAG, "count "+dayList.size());
        verticalViewAdapter=new VerticalViewAdapter(newList,dateList,getApplicationContext());
        GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),7);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(verticalViewAdapter);
    }

    String getTodayDay(){
        String day;
        Date d=calendar.getTime();
        String dt=d.toString();
        day=dt.substring(0,3);

        Log.d(TAG, "getTodayDay: "+day);
        return day;
    }

    int getDate(){
        int pos=0;
        for(int i=0;i<days.length;i++){
            if(getTodayDay().equals(days[i])){
                pos=i;
                break;
            }
        }
        return pos;
    }
}
