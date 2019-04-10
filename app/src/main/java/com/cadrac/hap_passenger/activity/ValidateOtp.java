package com.cadrac.hap_passenger.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap_passenger.MainActivity;
import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.responses.OtpResponse;
import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValidateOtp extends AppCompatActivity {

    private static final String TAG ="243" ;
    EditText ed1,ed2,ed3,ed4, ed5;
    TextView resend;
    Button b1;
    OtpResponse valres;
    OtpResponse res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_otp);


        resend = (TextView) findViewById(R.id.resend);
        b1=(Button) findViewById(R.id.submit);


        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        ed4 = (EditText) findViewById(R.id.ed4);
        ed5 = (EditText) findViewById(R.id.ed5);
        ed5.setVisibility(View.GONE);

        ed1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed1.getText().toString().length()==1)     //size as per your requirement
                {
                    ed2.requestFocus();
                }else{

                    ed1.requestFocus();

                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed2.getText().toString().length()==1)     //size as per your requirement
                {
                    ed3.requestFocus();
                }else{

                    ed1.requestFocus();

                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed3.getText().toString().length()==1)     //size as per your requirement
                {
                    ed4.requestFocus();
                }
                else{

                    ed2.requestFocus();

                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed4.getText().toString().length()==1)     //size as per your requirement
                {
                    ed5.requestFocus();
                }
                else{

                    ed3.requestFocus();

                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                valOtp();

            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ValidateOtp.this, "OTP has been sent", Toast.LENGTH_SHORT).show();

                getOtp();

            }
        });

    }


    public void getOtp() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            // Log.d("TAG", "onClick: " + mobileNum.getText().toString());
            String num;
            num = Config.getNumber(getApplicationContext());

            Config.saveNumber(getApplication(),num);
            Log.d("TAG", "onClick: " + num);
            Call<OtpResponse> call = api.generateOTP(num);
            call.enqueue(new Callback<OtpResponse>() {
                @Override
                public void onResponse(Call<OtpResponse> call,
                                       Response<OtpResponse> response) {

                    res = new OtpResponse();
                    res = response.body();
                    if(res.getStatus().equalsIgnoreCase("true")){
                        Toast.makeText(ValidateOtp.this, "OTP has been sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ValidateOtp.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OtpResponse> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }


    }


    public void valOtp(){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            // Log.d(TAG, "onClick: "+mobileNum.getText().toString());
            String num = Config.getNumber(getApplication());
            String otp;
            otp = ed1.getText().toString()+ed2.getText().toString()+ed3.getText().toString()+ed4.getText().toString();
            Log.d(TAG, "onClick:1 "+otp);
            Log.d(TAG, "onClick:num "+num);
            Call<OtpResponse> call = api.validateOTP(num, otp);
            call.enqueue(new Callback<OtpResponse>() {
                @Override
                public void onResponse(Call<OtpResponse> call,
                                       Response<OtpResponse> response) {

                    valres = new OtpResponse();
                    valres = response.body();
                    if(valres.getStatus().equalsIgnoreCase("true")){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        Config.saveLoginStatus(getApplicationContext(),"1");
                        Toast.makeText(ValidateOtp.this, "success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ValidateOtp.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                    //  Log.d(TAG, "onResponse: "+valres);
                }

                @Override
                public void onFailure(Call<OtpResponse> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);


        }

    }





}
