package com.hitesh.ofcwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private String TAG = "project";
    int y, m, d,data;
    Dialog dialog;
    String sessName, sessPwd;
    Date date1;
    DateFormat dateFormat;
    SessionManagement sessionManagement;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", cpwd, npwd, repwd;
    private TextInputEditText firstName, middleName, lastName, nickName, email, password, alternateEmail, mobileNo, address1, address2, city, state, pincode, dob, passport, passportExpDate;
    private TextInputLayout fnameLayout, lnameLayout, nnameLayout, emailLayout, pwdLayout, altemailLayout, mnoLayout, add1Layout, add2Layout, cityLayout, stateLayout, pincodeLayout, dobLayout, passportLayout, passportExpDateLayout;
    String fnameVal, lnameVal, mnoVal, nnameVal, emailVal, passwordVal, altemailVal, add1Val, add2Val, cityVal, stateVal, pincodeVal, dobVal, passportVal, passportexpVal, mnameVal, Title, Box1, Box2, pped, dob1, userId;
    ImageView imageView;
    NewPassword newPassword;
    CheckBox box1, box2;
    Context context=SignUp.this;
    private Button save, btn, cancel;
    TextView close, mr, ms, mrs;
    TextInputEditText c_pwd, n_pwd, re_pwd;   // wait coading check kartoy mazyakade issue yetoy coade merge karayla
    TextInputLayout c_pwdLay, n_pwwdLay, re_pwdLay;
    //int y,m,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSignUp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();
        sessName = bundle.getString("User");
        sessPwd = bundle.getString("Pwd");
        sessionManagement = new SessionManagement(SignUp.this);
        newPassword=new NewPassword();

        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        dialog = new Dialog(SignUp.this);
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);

        firstName = findViewById(R.id.firstName);
        firstName.setText(bundle.getString("Fname"));
        middleName = findViewById(R.id.middleName);
        middleName.setText(bundle.getString("Mname"));
        lastName = findViewById(R.id.lastName);
        lastName.setText(bundle.getString("Lname"));
        nickName = findViewById(R.id.nickName);
        nickName.setText(bundle.getString("Nname"));
        email = findViewById(R.id.email);
        email.setText(bundle.getString("User"));
        password = findViewById(R.id.password);
        password.setText(bundle.getString("Pwd"));
        alternateEmail = findViewById(R.id.alternateEmail);
        alternateEmail.setText(bundle.getString("AltEmail"));
        mobileNo = findViewById(R.id.mobileNo);
        mobileNo.setText(bundle.getString("Mno"));
        address1 = findViewById(R.id.address1);
        address1.setText(bundle.getString("Address"));
        address2 = findViewById(R.id.address2);
        address2.setText(bundle.getString("Address1"));
        city = findViewById(R.id.city);
        city.setText(bundle.getString("City"));
        state = findViewById(R.id.state);
        state.setText(bundle.getString("State"));
        pincode = findViewById(R.id.pincode);
        pincode.setText(bundle.getString("Pincode"));
        dob = findViewById(R.id.dob);
        Title = bundle.getString("Title");
        //Button=bundle.getString("Title");
        passport = findViewById(R.id.passport);
        passport.setText(bundle.getString("PPnumber"));
        dob1 = bundle.getString("DOB");
        pped = bundle.getString("PPexpdate");
        passportExpDate = findViewById(R.id.passportExpDate);
        imageView = findViewById(R.id.edit_pwd);
        userId = bundle.getString("UserId");

        if (bundle.getString("Newsletters1").equals("1")) {
            box1.setChecked(true);
        }
        if (bundle.getString("Newsletters2").equals("1")) {
            box2.setChecked(true);
        }


        close = (TextView) findViewById(R.id.close);
        mr = findViewById(R.id.mr);
        ms = findViewById(R.id.ms);
        mrs = findViewById(R.id.mrs);
        fnameLayout = findViewById(R.id.firstnameLayout);
        lnameLayout = findViewById(R.id.lastnameLayout);
        nnameLayout = findViewById(R.id.nicknameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        pwdLayout = findViewById(R.id.passwordLayout);
        altemailLayout = findViewById(R.id.altemailLayout);
        mnoLayout = findViewById(R.id.mobilenoLayout);
        add1Layout = findViewById(R.id.address1Layout);
        add2Layout = findViewById(R.id.address2Layout);
        pincodeLayout = findViewById(R.id.pincodeLayout);
        cityLayout = findViewById(R.id.cityLayout);
        stateLayout = findViewById(R.id.stateLayout);
        dobLayout = findViewById(R.id.dobLayout);
        passportLayout = findViewById(R.id.passportLayout);
        passportExpDateLayout = findViewById(R.id.passportExpDateLayout);


        Log.d(TAG, "title " + Title);
        if (Title.equals("Mr")) {
            mr.setBackground(getResources().getDrawable(R.drawable.button));
            mr.setTextColor(getResources().getColor(R.color.white));
        } else if (Title.equals("Ms")) {
            ms.setBackground(getResources().getDrawable(R.drawable.button));
            ms.setTextColor(getResources().getColor(R.color.white));
        } else if (Title.equals("Mrs")) {
            mrs.setBackground(getResources().getDrawable(R.drawable.button));
            mrs.setTextColor(getResources().getColor(R.color.white));
        } else {
        }

        mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = "Mr";
                mr.setBackground(getResources().getDrawable(R.drawable.button));
                ms.setBackgroundColor(getResources().getColor(R.color.grey));
                mrs.setBackgroundColor(getResources().getColor(R.color.grey));
                mr.setTextColor(getResources().getColor(R.color.white));
                ms.setTextColor(getResources().getColor(R.color.black));
                mrs.setTextColor(getResources().getColor(R.color.black));
            }
        });
        ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = "Ms";
                ms.setBackground(getResources().getDrawable(R.drawable.button));
                mr.setBackgroundColor(getResources().getColor(R.color.grey));
                mrs.setBackgroundColor(getResources().getColor(R.color.grey));
                ms.setTextColor(getResources().getColor(R.color.white));
                mr.setTextColor(getResources().getColor(R.color.black));
                mrs.setTextColor(getResources().getColor(R.color.black));
            }
        });
        mrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = "Mrs";
                mrs.setBackground(getResources().getDrawable(R.drawable.button));
                mr.setBackgroundColor(getResources().getColor(R.color.grey));
                ms.setBackgroundColor(getResources().getColor(R.color.grey));
                mrs.setTextColor(getResources().getColor(R.color.white));
                mr.setTextColor(getResources().getColor(R.color.black));
                ms.setTextColor(getResources().getColor(R.color.black));
            }
        });


        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-12);
        //0001-01-01  dob1.substring(0,4),dob1.substring(5,7),dob1.substring(8,10)
        final Date date=calendar.getTime();
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y = Integer.valueOf(dob1.substring(0,4));
                m = Integer.valueOf(dob1.substring(5,7));
                d = Integer.valueOf(dob1.substring(8,10));

                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y=year;m=month;d=dayOfMonth;
                        dob.setText(y + "-" + (m+1) + "-" + d);
                        //y=year;d=dayOfMonth;m=month+1;
                    }
                }, y, m-1, d);
                // datePickerDialog.updateDate(y,m,d);
                datePickerDialog.getDatePicker().setMaxDate(date.getTime());
                datePickerDialog.show();
            }
        });



        passportExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year =Integer.valueOf(pped.substring(0,4));
                int month = Integer.valueOf(pped.substring(5,7));
                int day = Integer.valueOf(pped.substring(8,10));
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        passportExpDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        y=year;m=month;d=dayOfMonth;
                    }
                }, year, month, day);
                datePickerDialog.updateDate(y,m,d);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });
        Log.d(TAG, "DOB " + dob1);

        Log.d(TAG, "asdasdsad  " + dob1.substring(0, 10));
        if (!dob1.equals("0001-01-01T00:00:00")) {
            dob.setText(dob1.substring(0, 10));
        }
        if (!pped.equals("0001-01-01T00:00:00")) {
            passportExpDate.setText(pped.substring(0, 10));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });

        validate();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LoginSignUp.class));
                finish();
            }
        });
    }

    public void popup() {
        dialog.setContentView(R.layout.edit_password);
        close = (TextView) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn = dialog.findViewById(R.id.changePwd);
        c_pwdLay = dialog.findViewById(R.id.currentPasswordLayout);
        n_pwwdLay = dialog.findViewById(R.id.newPasswordLayout);
        re_pwdLay = dialog.findViewById(R.id.reEnterPasswordLayout);

        c_pwd = dialog.findViewById(R.id.currentPassword);
        n_pwd = dialog.findViewById(R.id.newPassword);
        re_pwd = dialog.findViewById(R.id.reEnterPassword);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicked btn");
                cpwd = c_pwd.getText().toString().trim();
                npwd = n_pwd.getText().toString().trim();
                repwd = re_pwd.getText().toString().trim();

                String realPwd = sessionManagement.getPassword();
                Log.i(TAG, realPwd + " " + cpwd + " " + npwd + " " + repwd);
                Log.i(TAG, "readPwd " + realPwd);

                if (!realPwd.equals(cpwd)) {
                    c_pwdLay.setError("Enter correct password");
                } else if (npwd.isEmpty()) {
                    Log.d(TAG, "true1");
                    n_pwwdLay.setError("Required");
                } else if (cpwd.isEmpty()) {
                    Log.d(TAG, "true2");
                    c_pwdLay.setError("Required");
                } else if (repwd.isEmpty()) {
                    Log.d(TAG, "true3");
                    c_pwdLay.setErrorEnabled(false);  // kar run
                    re_pwdLay.setError("Required");
                } else if (!npwd.equals(repwd)) {
                    Log.i(TAG, cpwd + " " + npwd + " " + repwd);
                    Log.d(TAG, "true4");
                    c_pwdLay.setErrorEnabled(false);
                    re_pwdLay.setError("Password doesn't match");
                } else {
                    Log.i(TAG, cpwd + " " + npwd + " " + repwd);
                    password.setText(repwd);
                    Log.d(TAG, "onClick: "+repwd);
                    newPassword.updatePassword(email.getText().toString().trim(),repwd,context);
                    //Toast.makeText(SignUp.this, "Password updated", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    c_pwdLay.setErrorEnabled(false);
                    n_pwwdLay.setErrorEnabled(false);
                    re_pwdLay.setErrorEnabled(false);
                }
            }
        });
        dialog.create();
        dialog.show();

    }

    public void validate() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked save button");
                fnameVal = firstName.getText().toString().trim();
                lnameVal = lastName.getText().toString().trim();
                mnameVal = middleName.getText().toString().trim();
                nnameVal = nickName.getText().toString().trim();
                emailVal = email.getText().toString().trim();
                passwordVal = password.getText().toString().trim();
                Log.d(TAG, "onClick: "+passwordVal);
                altemailVal = alternateEmail.getText().toString().trim();
                mnoVal = mobileNo.getText().toString().trim();
                add1Val = address1.getText().toString().trim();
                add2Val = address2.getText().toString().trim();
                cityVal = city.getText().toString().trim();
                stateVal = state.getText().toString().trim();
                pincodeVal = pincode.getText().toString().trim();
                dobVal = dob.getText().toString().trim();
                passportVal = passport.getText().toString().trim();
                passportexpVal = passportExpDate.getText().toString().trim();
                if (box1.isChecked()) {
                    Box1 = "true";
                }
                if (box2.isChecked()) {
                    Box2 = "true";
                }
//
                //Important code DND
//               if(fnameVal.isEmpty()|lnameVal.isEmpty()|nnameVal.isEmpty()|emailVal.isEmpty()|passwordVal.isEmpty()|altemailVal.isEmpty()|mnoVal.isEmpty()|add1Val.isEmpty()|add2Val.isEmpty()|cityVal.isEmpty()|stateVal.isEmpty()|pincodeVal.isEmpty()|dobVal.isEmpty()|passportVal.isEmpty()|passportexpVal.isEmpty() | !box1.isChecked()|!box2.isChecked()){
//                   fnameLayout.setError("Required");
//                   lnameLayout.setError("Required");
//                   nnameLayout.setError("Required");
//                   emailLayout.setError("Required");
//                   pwdLayout.setError("Required");
//                   altemailLayout.setError("Required");
//                   mnoLayout.setError("Required");
//                   add1Layout.setError("Required");
//                   add2Layout.setError("Required");
//                   cityLayout.setError("Required");
//                   stateLayout.setError("Required");
//                   pincodeLayout.setError("Required");
//                   dobLayout.setError("Required");
//                   passportLayout.setError("Required");
//                   passportExpDateLayout.setError("Required");
//               }
//               else
               if(fnameVal.isEmpty()){
                   fnameLayout.setError("Required");
               }
               else if(lnameVal.isEmpty()){
                   lnameLayout.setError("Required");
               }
               else if(nnameVal==""){
                   nnameLayout.setError("Required");
               }
               else if(emailVal.isEmpty()){
                   emailLayout.setError("Required");
               }
               else if(!emailVal.matches(emailPattern)){
                   emailLayout.setError("Invalid email address");
               }
               else if(passwordVal.isEmpty()){
                   pwdLayout.setError("Required");
               }
               else if(altemailVal.isEmpty()){
                   altemailLayout.setError("Required");
               }
               else if(!altemailVal.matches(emailPattern)){
                   altemailLayout.setError("Invalid email address");
               }
               else if(mnoVal.isEmpty()){
                   mnoLayout.setError("Required");
               }else if (mnoVal.length()<10){
                   mnoLayout.setError("Enter valid mobile number");
               }
               else if(add1Val.isEmpty()){
                   add1Layout.setError("Required");
               }
               else if(add2Val.isEmpty()){
                   add2Layout.setError("Required");
               }
               else if(cityVal.isEmpty()){
                   cityLayout.setError("Required");
               }
               else if(stateVal.isEmpty()){
                   stateLayout.setError("Required");
               }
               else if(pincodeVal.isEmpty()){
                   pincodeLayout.setError("Required");
               }
               else if(pincodeVal.length()<6){
                   pincodeLayout.setError("Invalid pincode");
               }
               else if(dobVal.isEmpty()){
                   dobLayout.setError("Required");
               }
               else if(passportVal.isEmpty()){
                   passportLayout.setError("Required");
               }
               else if(passportVal.length()<9){
                   passportLayout.setError("Invalid passport number");
               }
               else if(passportexpVal.isEmpty()){
                   passportLayout.setError("Required");
               }
               else{
                 save.setEnabled(true);
                Log.d(TAG, "Ready to go");
                Log.i(TAG, fnameVal + " " + lnameVal + " " + nnameVal + " " + emailVal + " " + Title + " " + passwordVal + " " + mnoVal + " " + add1Val + " " + add2Val + " " + cityVal + " " + stateVal + " " + pincodeVal + " " + dobVal + " " + passportVal + " " + passportexpVal);
                fnameLayout.setError(null);
                lnameLayout.setError(null);
                nnameLayout.setError(null);
                emailLayout.setError(null);
                pwdLayout.setError(null);
                altemailLayout.setError(null);
                mnoLayout.setError(null);
                add1Layout.setError(null);
                add2Layout.setError(null);
                cityLayout.setError(null);
                stateLayout.setError(null);
                pincodeLayout.setError(null);
                passportLayout.setError(null);
                passportExpDateLayout.setError(null);
                Log.d(TAG, "onClick: clicked");
                updateProfile();
               }
            }
        });
    }

    public void updateProfile() {

        Call<ResponseBody> call = ApiClient.getApiClient().create(Api.class).updateProfile(userId, emailVal, fnameVal, lnameVal, mnoVal, add1Val, cityVal, "India", pincodeVal, Box1, stateVal, Title, altemailVal, add2Val, dobVal, passportVal, "IN", passportexpVal, Box2, mnameVal, "", repwd, emailVal, nnameVal);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: "+repwd);
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d(TAG, "response =" + jsonObject);
                    if (jsonObject.getString("Status") == "true") {
                        Toast.makeText(SignUp.this, "Updated successfully", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: "+jsonObject.getString("Status"));
                    }
                } catch (Exception e) {
                    e.getMessage();
                    data=0;
                }
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
                Intent intent = new Intent(SignUp.this, LoginSignUp.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
