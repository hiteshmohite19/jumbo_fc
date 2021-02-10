package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DatePicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_date_picker);

        EditText departure,arrival;
        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){
            setDeparture(departure,bundle);
            setArrival(arrival,bundle);
        }

        departure.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),VerticalDatePickerPage.class));
            }
        });
        arrival.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),VerticalDatePickerPage.class));
            }
        });
    }

    void setDeparture(EditText departure,Bundle bundle){

        if(!bundle.getString("departure").isEmpty()){
            departure.setText(bundle.getString("departure"));
        }
    }

    void setArrival(EditText arrival,Bundle bundle){
        if(!bundle.getString("arrival").isEmpty()){
            arrival.setText(bundle.getString("arrival"));
        }
    }

}
