package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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

public class LoginSignUp extends AppCompatActivity {
    Context context = LoginSignUp.this;
    private TextInputEditText email, password;
    private Button login, signup;
    TextView forgetPwd, emailerror;
    private TextInputLayout emailLayout, pwdLayout;
    String TAG = "project", emailVal, pwdVal, otpKey,status,otpVal;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    ValidateOtp validateOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        emailLayout = findViewById(R.id.emailLayout);
        pwdLayout = findViewById(R.id.passwordLayout);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        forgetPwd = findViewById(R.id.forgetPassword);
        emailerror = findViewById(R.id.alreadyexists);
        forgetPwd.setText(Html.fromHtml("<u>Forget Password</u>"));


        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailVal = email.getText().toString().trim();
                pwdVal = password.getText().toString().trim();
                if (emailVal.isEmpty()) {
                    emailLayout.setError("Email required");
                } else if (pwdVal.isEmpty()) {
                    pwdLayout.setError("Password required");
                } else if (!emailVal.matches(emailPattern)) {
                    emailLayout.setError("Enter valid email");
                } else {
                    spinner(context);
                    signup(emailVal);
                    emailLayout.setError(null);
                    pwdLayout.setError(null);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailVal = email.getText().toString().trim();
                pwdVal = password.getText().toString().trim();
                if (emailVal.isEmpty()) {
                    emailLayout.setError("Email required");
                } else if (pwdVal.isEmpty()) {
                    pwdLayout.setError("Password required");
                } else if (!emailVal.matches(emailPattern)) {
                    emailLayout.setError("Enter valid email");
                } else {
                    login();
                    emailLayout.setError(null);
                    pwdLayout.setError(null);
                }
            }
        });


    }

    public void signup(String email) {

        Call<ResponseBody> call = ApiClient.getApiClient().create(Api.class).signUp(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    otpVal = json.getString("Otp").trim();
                    status = json.getString("Status").trim();
                    Log.i(TAG, json.toString());
                    Log.i(TAG, otpVal);
                    System.out.println(otpVal);
                    if (otpVal == "null") {
                        emailerror.setText("Email already exists");
                        progressDialog.dismiss();
                        otpKey="false1";
                        Log.d(TAG, "onResponse: "+otpKey);
                    } else if (status.equals(false)) {
                        Toast.makeText(LoginSignUp.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        otpKey="false2";
                        Log.d(TAG, "onResponse: "+otpKey);
                    }
                     else if(status=="true"){
                        otpKey="true";
                        //validateOtp.count();
                        Log.d(TAG, "onResponse:otp "+otpKey);
                        Log.d(TAG, "onResponse: "+otpVal);
                        otpVal=json.getString("Otp");
                        Toast.makeText(LoginSignUp.this, "OTP send successfully", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginSignUp.this, ValidateOtp.class).putExtra("UserName", emailVal).putExtra("Password", pwdVal).putExtra("Otp",otpVal));
                        validateOtp.count();
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

    public String otpKey(){
        return otpKey;
    }

    public void login() {
        Call<ResponseBody> call = ApiClient.getApiClient().create(Api.class).login(emailVal, pwdVal, "1", "Android", "1.1.1.1", "IN");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("Status");
                    if (status == "true") {
                        System.out.println(jsonObject);
                        SessionManagement sessionManagement = new SessionManagement(LoginSignUp.this);
                        Log.i(TAG, emailVal + " " + pwdVal);
                        sessionManagement.loginSession(emailVal, pwdVal);
                        JSONObject obj = jsonObject.getJSONObject("userProfileData");
                        Log.i(TAG, obj.getString("UserID"));

                        startActivity(new Intent(LoginSignUp.this, SignUp.class).putExtra("User", emailVal).putExtra("Pwd", pwdVal).putExtra("Fname", obj.getString("FirstName")).putExtra("Lname", obj.getString("LastName")).putExtra("Mno", obj.getString("MobileNo")).putExtra("Address", obj.getString("Address")).putExtra("City", obj.getString("City")).putExtra("Country", obj.getString("Country")).putExtra("Pincode", obj.getString("Pincode")).putExtra("Newsletters1", obj.getString("NewsLetters1")).putExtra("AltEmail", obj.getString("AlternateEmailID")).putExtra("Address1", obj.getString("Address1")).putExtra("DOB", obj.getString("DateOfBirth")).putExtra("PPnumber", obj.getString("PassportNumber")).putExtra("PPexpdate", obj.getString("PassportExpiryDate")).putExtra("Newsletters2", obj.getString("NewsLetters2")).putExtra("Mname", obj.getString("MiddleName")).putExtra("Nname", obj.getString("NickName")).putExtra("Title", obj.getString("Title")).putExtra("UserId", obj.getString("UserID")).putExtra("State", obj.getString("Province")));

                    } else {
                        Log.d(TAG, status);
                        //progressDialog.dismiss();
                        pwdLayout.setError("Either Email or Password is incorrect");
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        //progressDialog.dismiss();
    }

    public void spinner(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setContentView(R.layout.spinner);
        progressDialog.show();
    }


    public void forgetPassword() {
        startActivity(new Intent(LoginSignUp.this, ForgetPwdGetOtp.class).putExtra("UserName", emailVal));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(LoginSignUp.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
