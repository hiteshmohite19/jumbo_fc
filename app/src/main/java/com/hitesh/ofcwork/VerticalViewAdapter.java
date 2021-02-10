package com.hitesh.ofcwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VerticalViewAdapter extends RecyclerView.Adapter<VerticalViewAdapter.ViewHolder>  {

    String mon="";

    Calendar calendar;
    Context context;
    String[] days={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
//    String[] mMonthList={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    int row=-1,count;
    ArrayList dateList,mMonthList;
    String TAG="project";
    String month,year,price,day,date,todayDate;
    VerticalViewAdapter(ArrayList newList,ArrayList dateList,Context context){
        this.context=context;
        this.mMonthList=newList;
        this.dateList=dateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vertical_view_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

//        ArrayList dayList,dateList,monthList,yearList,priceList;
//        dayList=new ArrayList<>();
//        dateList=new ArrayList<>();

//        monthList=new ArrayList<>();
//        yearList=new ArrayList<>();
//        int max_days=0;
//
//        Calendar calendar= Calendar.getInstance();
//        Date d=calendar.getTime();
//        String dt=d.toString();
//        Log.d(TAG, "recyclerData: 3 "+calendar.getTime());
//        day=dt.substring(0,3);
//        date=dt.substring(8,10);
////        month=dt.substring(4,7);
//        int count=0;
//        for(int i=0;i<mMonthList.size();i++){
//            int c=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//            if(count==0){
//                Log.d(TAG, "onBindViewHolder: "+(c-Integer.parseInt(date)));
//                max_days=c-Integer.parseInt(date);
//                count++;
//            }
//            else {
//                max_days=c;
//            }
//
//            for(int j=0;j<max_days;j++){
//                Log.d(TAG, "macdays : "+max_days);
//                Log.d(TAG, "onBindViewHolder: "+calendar.getTime());
//
//                holder.date.setText(calendar.getTime().toString().substring(8,10));
//                month=calendar.getTime().toString().substring(4,7);
//
//                if(!mon.equals(month)){
//                    Log.d(TAG, "onBindViewHolder: on month change "+date+" "+day+" "+month+" "+year);
//                    mon=month;
//                }
//                calendar.add(Calendar.DATE,1);
//            }
//        }
//
////        day=String.valueOf(dayList.get(position));
        Log.d(TAG, "onBindViewHolder: "+dateList.size());
        Log.d(TAG, "onBindViewHolder: "+dateList.get(position));
        date=String.valueOf(dateList.get(position));
//        month=String.valueOf(monthList.get(position));
////        year=String.valueOf(yearList.get(position));
//        Log.d(TAG, "mon: "+mon+" "+month+" "+date);
//
//
//        holder.date.setText(date+" "+day);
//        if(!mon.equals(month)){
//
//            Log.d(TAG, "onBindViewHolder: on month change "+date+" "+day+" "+month+" "+year);
//            mon=month;
//        }

        holder.date.setText(date);
    }


    void setData(String date,String day,ViewHolder holder){
        Log.d(TAG, "setData: "+day+" "+date);

    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mon,tue,wed,thu,fri,sat,sun;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
//            mon=itemView.findViewById(R.id.mon);
//            tue=itemView.findViewById(R.id.tue);
//            wed=itemView.findViewById(R.id.wed);
//            thu=itemView.findViewById(R.id.thu);
//            fri=itemView.findViewById(R.id.fri);
//            sat=itemView.findViewById(R.id.sat);
//            sun=itemView.findViewById(R.id.sun);
        }
    }
}
