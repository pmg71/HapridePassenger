package com.cadrac.hap_passenger.webservices;

import retrofit2.Retrofit;

public class RestClient {

   // public static final String baseUrl = "http://183.82.120.3:90/hapride/";

   public static final String baseUrl = "http://toobworks.com/hap/";
    public static Retrofit client = new Retrofit.Builder().baseUrl(baseUrl).build();

}
