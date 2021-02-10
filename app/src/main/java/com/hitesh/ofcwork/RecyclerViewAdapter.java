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
    GetMonth getMonth;
    String month,year,price,day,date,todayDate;
    String selectedDate,finalDate;
    LinearLayoutManager linearLayoutManager;
    Context context;
    String[] mMonthList={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    int row=-1,count=0;
    int n;

    public RecyclerViewAdapter(ArrayList<DateList> dateList, String selectedDate, GetMonth getMonth, Context context, LinearLayoutManager linearLayoutManager) {
        this.dateList = dateList;
        this.getMonth=getMonth;
        this.context=context;
        this.selectedDate=selectedDate;
        this.linearLayoutManager=linearLayoutManager;
        this.n=n;
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
            getMonth.monthPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
        }
//        Log.d(TAG, "onBindViewHolder: "+selectedDate);
        Log.d(TAG, "position : "+linearLayoutManager.findFirstCompletelyVisibleItemPosition());
        day=String.valueOf(dateList.get(position).getDay());
        date=String.valueOf(dateList.get(position).getDate());
        holder.day.setText(day);
        holder.date.setText(date);
        month=String.valueOf(dateList.get(position).getMonth());
        year=String.valueOf(dateList.get(position).getYear());

        Log.d(TAG, "onBindViewHolder: "+selectedDate+" : "+date+" "+month+" "+year);
        holder.price.setText(String.valueOf(dateList.get(position).getPrice()));

        getMonth.getYear(year);

        if(count==0){

            String dMonth,dDate,yYear;
            dDate=selectedDate.substring(selectedDate.length()-2);
            dMonth=selectedDate.substring(5,7);
            yYear=selectedDate.substring(0,4);
            int monthPos=Integer.parseInt(dMonth);

            if(finalDate==null)
                finalDate= dDate+" "+mMonthList[monthPos-1]+" "+yYear;
//            Log.d(TAG, "final Date: "+finalDate);

            if(date.equals(dDate)&&month.equals(mMonthList[monthPos-1])&&year.equals(yYear)){
//                Log.d(TAG, "selectedDate: "+position);
                holder.date.setTextColor(Color.parseColor("#FFFFFF"));
                holder.date.setBackground(context.getResources().getDrawable(R.drawable.date_background));
            } else{
                holder.date.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.date.setTextColor(Color.parseColor("#000000"));
            }
        }
        if(count!=0){
            dateSelectedOnClick(holder,position);
        }

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row=position;
                count++;
                notifyDataSetChanged();
            }
        });
        todayDate(holder);
    }


    void dateSelectedOnClick(ViewHolder holder,int position){
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


    void todayDate(ViewHolder holder){
        String dDay,dMonth,dDate,yYear;
        dDay=todayDate.substring(0,3);
        dDate=todayDate.substring(8,10);
        dMonth=todayDate.substring(4,7);
        yYear=todayDate.substring(todayDate.length()-4);

        if(date.equals(dDate)&&month.equals(dMonth)&&year.equals(yYear)){
            holder.date.setTextColor(Color.parseColor("#E52235"));
            //holder.date.setBackground(context.getResources().getDrawable(R.drawable.first_date));
        }
//        else{
//            holder.date.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            holder.date.setTextColor(Color.parseColor("#000000"));
//        }
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
        GetMonth getMonth;
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

    public interface GetMonth{
        void monthPosition(int month);
        void getYear(String year);
    }
}
