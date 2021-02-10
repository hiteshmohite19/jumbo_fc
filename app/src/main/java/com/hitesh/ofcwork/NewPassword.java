package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPassword extends AppCompatActivity {
    private TextInputEditText otpVal,pwd1,pwd2;
    private TextInputLayout otpLayout,pwd1Layout,pwd2Layout;
    Button changeEmail,resetPwd;
    Context context=NewPassword.this;
    TextView resend;
    String otp,OtpVal,Password1,Password2,TAG="project",email;
    int countStart=10000,interval=1000;
    CountDownTimer countDownTimer;
    ForgetPwdGetOtp forgetPwdGetOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();

        email=bundle.getString("Email");
        otp=bundle.getString("Otp");

        otpVal=findViewById(R.id.otp);
        pwd1=findViewById(R.id.pwd1);
        pwd2=findViewById(R.id.pwd2);
        otpLayout=findViewById(R.id.otpLayout);
        pwd1Layout=findViewById(R.id.pwd1Layout);
        pwd2Layout=findViewById(R.id.pwd2Layout);
        changeEmail=findViewById(R.id.changeEmail);
        resetPwd=findViewById(R.id.resetPwd);
        resend=findViewById(R.id.resend);

        forgetPwdGetOtp=new ForgetPwdGetOtp();

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewPassword.this,ForgetPwdGetOtp.class));
            }
        });

        resetPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtpVal=otpVal.getText().toString().trim();
                    Password1=pwd1.getText().toString().trim();
                    Password2=pwd2.getText().toString().trim();
                    Bundle bundle1=getIntent().getExtras();
                    Log.i(TAG, "onClick: "+otp);
                    Log.i(TAG, ""+otp+" "+OtpVal+" "+Password1+" "+Password2);

                    if(OtpVal.isEmpty()){
                        otpLayout.setError("OTP required");
                    }
                    else if(!otp.equals(OtpVal)){
                        otpLayout.setError("OTP incorrect");
                    }
                    else if(Password1.isEmpty()){
                        pwd1Layout.setError("Required");
                        otpLayout.setError(null);
                    }
                    else if(Password2.isEmpty()){
                        pwd2Layout.setError("Required");
                        otpLayout.setError(null);
                    }
                    else if(!Password1.equals(Password2)){
                        pwd2Layout.setError("Password doesn't match");
                    }else{

                        Log.d(TAG, "password matched");
                        updatePassword(email,Password2,context);
                        otpLayout.setError(null);
                        pwd1Layout.setError(null);
                        pwd2Layout.setError(null);
                    }
                }
        });

        resendOTP();

    }

    public void resendOTP(){
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
                    public void onClick(View v) { resend(email); }
                });
            }
        }.start();
    }

    public void resend(String email){
        Log.i(TAG, "validateEmail: "+email);
        Call<ResponseBody> call=ApiClient.getApiClient().create(Api.class).sendFPOtp(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    JSONObject jsonObject=new JSONObject(response.body().string());

                    if(otp=="null"){
                        Toast.makeText(NewPassword.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }else{
                        if(!otp.isEmpty()){
                            otp = jsonObject.getString("Otp").trim();
                        }
                        Log.d(TAG, "onResponse: "+otp);
                        resendOTP();
                    }
                }catch(Exception e){ e.getMessage(); }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void updatePassword(String email, String Password2, final Context context){
        Log.d(TAG, "updatePassword: "+email+" "+Password2);
        Call<ResponseBody> call=ApiClient.getApiClient().create(Api.class).updatePasword(email,Password2);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{

                    JSONObject jsonObject=new JSONObject(response.body().string());
                    Log.i(TAG, " "+jsonObject);
                    if(jsonObject.getString("Status")=="true"){
                        Toast.makeText(context,"Password changed successfully",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){ e.getMessage(); }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(NewPassword.this, LoginSignUp.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
