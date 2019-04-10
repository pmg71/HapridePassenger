package com.novisync.otp.webservices;

import retrofit2.Retrofit;

public class RestClient {

    public static final String baseUrl = "http://183.82.120.3:90/hapride/";

    public static Retrofit client = new Retrofit.Builder().baseUrl(baseUrl).build();

}
