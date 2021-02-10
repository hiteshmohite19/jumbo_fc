package com.hitesh.ofcwork;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("MobileLoginDetails/SignUp")
    Call<ResponseBody> signUp(
            @Field("EmailID") String EmailID
    );

    @FormUrlEncoded
    @POST("MobileLoginDetails/Save")
    Call<ResponseBody> saveSignUp(
            @Field("UserName") String UserName,
            @Field("Password") String Password,
            @Field("Country") String Country,
            @Field("BookingCountry") String BookingCountry
    );

    @FormUrlEncoded
    @POST("MobileLoginDetails/ForgotPassword")
    Call<ResponseBody> sendFPOtp(@Field("UserName") String UserName);

    @FormUrlEncoded
    @POST("MobileLoginDetails/Login")
    Call<ResponseBody> login(@Field("UserName") String UserName,@Field("Password") String Password,@Field("Option") String Option,@Field("Device") String Device,@Field("IPAddress") String IPAddress,@Field("Country") String Country);

    @FormUrlEncoded
    @POST("MobileLoginDetails/Update")
    Call<ResponseBody> updateProfile(@Field("UserID") String UserID,@Field("UserCode") String UserCode,@Field("FirstName") String FirstName, @Field("LastName") String LastName,@Field("MobileNo") String MobileNo,@Field("Address1") String Address1,@Field("City") String City,@Field("Country") String Country,@Field("Pincode") String Pincode,@Field("Newsletters1") String Newsletters1,@Field("State") String State,@Field("Titles") String Titles,@Field("AlterNateEmailId") String AlterNateEmailId,@Field("Address2") String Address2,@Field("DateOfBirth") String DateOfBirth, @Field("PassportNumber") String Passport,@Field("IssuingCountry") String IssuingCountry,@Field("PassportExpiryDate") String PassportExpiryDate,@Field("NewsLetters2") String NewsLetters2,@Field("MiddleName") String MiddleName,@Field("Option") String Option,@Field("CurrentPassword") String CurrentPassword,@Field("EmailID") String EmailID,@Field("NickName") String NickName);

    @FormUrlEncoded
    @POST("MobileLoginDetails/ResetPassword")
    Call<ResponseBody> updatePasword(@Field("EmailID") String Email,@Field("Password") String Password);
}
