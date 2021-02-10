package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateOtp extends AppCompatActivity {
    String otp,status,otpVal,UserName,Password, TAG="project";
    private TextInputEditText inputOtp;
    private TextInputLayout otpLayout;
    Button validate;int countStart=10000,interval=1000;
    CountDownTimer countDownTimer;
    LoginSignUp loginSignUp;
    TextView resend;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_otp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        resend=findViewById(R.id.resend);
        loginSignUp=new LoginSignUp();
        sessionManagement=new SessionManagement(ValidateOtp.this);

        Bundle bd=getIntent().getExtras();

        UserName=bd.getString("UserName").toString().trim();
        Password=bd.getString("Password").toString().trim();
        otp=bd.getString("Otp").toString().trim();
        inputOtp=findViewById(R.id.otp);
        otpLayout=findViewById(R.id.otpLayout);
        validate=findViewById(R.id.validate);
//        do{
//
//            otp=loginSignUp.otpKey();
//            Log.d(TAG, "onCreate: "+otp);
//        }while(otp==null);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //otp=loginSignUp.otpKey();
                Log.d(TAG, "onClick: "+UserName);
                Log.d(TAG, "onClick: "+Password);
                otpVal=inputOtp.getText().toString().trim();
                Log.d(TAG, "OTP onClick: "+otpVal);
                Log.d(TAG, "OTP onClick: "+otp);

                if(otpVal.isEmpty()){
                    otpLayout.setError("Reqiured");
                }else if(!otpVal.equals(otp)){
                    otpLayout.setError("Incorrect OTP");
                }else if(otpVal.equals(otp)){
                    validate();
                }
            }
        });
        count();
    }

    public void count(){
        //loginSignUp.progressDialog.dismiss();
        countDownTimer=new CountDownTimer(countStart,interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, "onTick: "+millisUntilFinished/1000);
                int sec=(int) (millisUntilFinished/1000);
                if(sec==10){ sec-=1; }
                resend.setText(sec+" sec left");
            }

            @Override
            public void onFinish() {
                resend.setText("Resend OTP");
                resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginSignUp.spinner(ValidateOtp.this);
                        getOTP(UserName);
                        Log.d(TAG, "onClick: "+otp);
                    }
                });
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(ValidateOtp.this, LoginSignUp.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getOTP(String email){
        Call<ResponseBody> call = ApiClient.getApiClient().create(Api.class).signUp(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    if(!otp.isEmpty()){
                        otp = json.getString("Otp").trim();
                    }
                    status = json.getString("Status").trim();
                    if (status.equals(false)) {
                        Toast.makeText(ValidateOtp.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    else if(status=="true"){
                        Log.d(TAG, "onResponse:otp "+otp);
                        count();
                        loginSignUp.progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void validate(){
        Log.d(TAG, "validate: "+UserName);
        Log.d(TAG, "validate: "+Password);

            Call<ResponseBody> call=ApiClient.getApiClient().create(Api.class).saveSignUp(UserName,Password,"IN","IN");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        String status=jsonObject.getString("Status").trim();
                        Log.d(TAG, ""+status);
                        Toast.makeText(ValidateOtp.this,"Registered successfully",Toast.LENGTH_LONG).show();
                        if(status=="true"){
                            startActivity(new Intent(ValidateOtp.this, SignUp.class).putExtra("User", UserName).putExtra("Pwd", Password).putExtra("Fname","").putExtra("Lname","").putExtra("Mno", "").putExtra("Address", "").putExtra("City", "").putExtra("Country", "").putExtra("Pincode", "").putExtra("Newsletters1", "").putExtra("AltEmail","").putExtra("Address1", "").putExtra("DOB", "0001-01-01").putExtra("PPnumber", "").putExtra("PPexpdate", "0001-01-01").putExtra("Newsletters2", "").putExtra("Mname", "").putExtra("Nname", "").putExtra("Title", "").putExtra("UserId", "").putExtra("State", ""));
                            Log.i(TAG, UserName+" "+Password);
                            sessionManagement.loginSession(UserName,Password);
                        }else{
                            Toast.makeText(ValidateOtp.this,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                        Log.d(TAG, "Exception: "+e.getMessage()); }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
    }
}


