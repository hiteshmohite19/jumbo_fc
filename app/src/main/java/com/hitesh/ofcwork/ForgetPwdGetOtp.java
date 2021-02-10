package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPwdGetOtp extends AppCompatActivity {
    private TextInputLayout emailLayout;
    private TextInputEditText email;
    private Button submit;
    String otpKey;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+",emailVal,TAG="project";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_get_otp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        email=findViewById(R.id.email);
        emailLayout=findViewById(R.id.emailLayout);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailVal=email.getText().toString().trim();
                if(emailVal.isEmpty()){
                    emailLayout.setError("Email required");
                }
                else if(!emailVal.matches(emailPattern)){
                    emailLayout.setError("Enter valid email");
                }
                else{
                    validateEmail(emailVal);
                }
            }
        });

    }

    public void validateEmail(String email){
        Log.i(TAG, "validateEmail: "+email);
        Call<ResponseBody> call=ApiClient.getApiClient().create(Api.class).sendFPOtp(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    String otp=jsonObject.getString("Otp").trim();
                    otpKey=otp;
                    Log.d(TAG, " "+otp);
                    if(otp=="null"){
                        emailLayout.setError("Email doesn't exists");
                    }else{
                        Log.d(TAG, "onResponse: "+otp);
                        startActivity(new Intent(ForgetPwdGetOtp.this,NewPassword.class).putExtra("Otp",otp).putExtra("Email",emailVal));
                    }
                }catch(Exception e){ e.getMessage(); }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public String otpKey(){
        return otpKey;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ForgetPwdGetOtp.this, LoginSignUp.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
