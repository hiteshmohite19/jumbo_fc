package com.hitesh.ofcwork;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<DateList> dateList;
    String TAG="project";
    ClickListener clickListener;
    GetPositions getPositions;
    String month,year,price,day,date,todayDate;
    String selectedDate,finalDate;
    LinearLayoutManager linearLayoutManager;
    Context context;
    String[] mMonthList={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    int row=-1,count=0;
    int n;

    public RecyclerViewAdapter(ArrayList<DateList> dateList, String selectedDate, GetPositions getPositions, Context context, LinearLayoutManager linearLayoutManager) {
        this.dateList = dateList;
        this.getPositions=getPositions;
        this.context=context;
        this.selectedDate=selectedDate;
        this.linearLayoutManager=linearLayoutManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler_view_adapter,parent,false);
        return new ViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(linearLayoutManager.findFirstCompletelyVisibleItemPosition()!=-1){
            getPositions.monthPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
        }
        todayDate=dateList.get(0).getYear()+"-"+dateList.get(0).getMonth()+"-"+dateList.get(0).getDate();
//        Log.d(TAG, "onBindViewHolder: "+selectedDate);
        Log.d(TAG, "position : "+linearLayoutManager.findFirstCompletelyVisibleItemPosition());
        day=String.valueOf(dateList.get(position).getDay());
        date=String.valueOf(dateList.get(position).getDate());
        holder.day.setText(day);
        holder.date.setText(date);
        month=String.valueOf(dateList.get(position).getMonth());
        year=String.valueOf(dateList.get(position).getYear());

        String updatingDate=year+"-"+month+"-"+date;

        Log.d(TAG, "get values : "+selectedDate+" : "+updatingDate+" "+todayDate);



        holder.price.setText(String.valueOf(dateList.get(position).getPrice()));

        getPositions.getYear(year);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row=position;
                count++;
                notifyDataSetChanged();
            }
        });

        markingSelectedDate(count,holder,updatingDate,position);
        Log.d(TAG, "markingSelectedDate: "+position);

        if(count!=0){
            dateSelectedOnClick(holder,position);
        }

        todayDate(holder,updatingDate);
    }

    public void markingSelectedDate(int count, ViewHolder holder,String updatingDate,int position){
        if(count==0){

            String dMonth,dDate,yYear;
            dDate=selectedDate.substring(selectedDate.length()-2);
            dMonth=selectedDate.substring(5,7);
            yYear=selectedDate.substring(0,4);
            int monthPos=Integer.parseInt(dMonth);

            if(finalDate==null)
                finalDate= dDate+" "+mMonthList[monthPos-1]+" "+yYear;
//            Log.d(TAG, "final Date: "+finalDate);

            if(updatingDate.equals(selectedDate)){
                Log.d(TAG, "selectedDate: "+position+" "+updatingDate +" "+selectedDate);
                holder.date.setTextColor(Color.parseColor("#FFFFFF"));
                holder.date.setBackground(context.getResources().getDrawable(R.drawable.date_background));
            } else{
                holder.date.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.date.setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    void dateSelectedOnClick(ViewHolder holder,int position){
        Log.d(TAG, "dateSelectedOnClick: "+position);
        if(row==position){
            holder.date.setTextColor(Color.parseColor("#FFFFFF"));
            holder.date.setBackground(context.getResources().getDrawable(R.drawable.date_background));
            finalDate=dateList.get(position)+" "+dateList.get(position)+" "+dateList.get(position);
            Log.d(TAG, "final date: "+finalDate);
        } else{
            holder.date.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.date.setTextColor(Color.parseColor("#000000"));
        }
    }


    void todayDate(ViewHolder holder,String updatingDate){
        Log.d(TAG, "todayDate: "+todayDate);

        if(updatingDate.equals(todayDate)){
            Log.d(TAG, "todayDate: true");
            holder.date.setTextColor(Color.parseColor("#E52235"));
        }
    }



    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+dateList.size());
        return dateList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView day,date,price;
        CardView cardview;
        ClickListener clickListener;
        GetPositions getPositions;
        Date todayDate=new Date();
        String tDate=todayDate.toString();
        private static final String TAG = "project";

        public ViewHolder(@NonNull View itemView,ClickListener clickListener) {
            super(itemView);
            day=itemView.findViewById(R.id.day);
            date=itemView.findViewById(R.id.date);
            price=itemView.findViewById(R.id.price);
            cardview=itemView.findViewById(R.id.cardview);

            this.clickListener=clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getAdapterPosition());
        }

    }

    public interface ClickListener{
        void onClick(int position);
    }

    public interface GetPositions{
        void monthPosition(int month);
        void getYear(String year);
    }

}