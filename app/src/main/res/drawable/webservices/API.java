package com.novisync.otp.webservices;


import com.novisync.otp.otpRes;
import com.novisync.otp.otpValRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("otp.php")//supervisor login
    Call<otpRes> generateOTP(
            @Query("mobileNum") String Num
    );


    @GET("valotp.php")//supervisor login
    Call<otpValRes> validateOTP(
            @Query("mobileNum") String Num,
            @Query("otp") String otp
    );



}
