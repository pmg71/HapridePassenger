package com.cadrac.hap_passenger.activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cadrac.hap_passenger.MainActivity;
import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.responses.Profile_Response;
import com.cadrac.hap_passenger.supporter_classes.Connection_Detector;
import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    EditText name,number,email;
    Button submit;
    Profile_Response profile_response;
    Connection_Detector connection_detector;

    String name1,number1,email1,number2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        submit = (Button) findViewById(R.id.submit1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Transition slide= TransitionInflater.from(this).inflateTransition(R.transition.hello);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setEnterTransition(slide);
            }
        }

        connection_detector = new Connection_Detector(getApplicationContext());

        number2=Config.getNumber(getApplicationContext());
        number.setText(number2);
        //profile data getting
        profile();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=name.getText().toString();
               // number1=Config.getNumber(getApplicationContext());
                email1=email.getText().toString();

                Log.d("TAG", "onResponse:getin" + name1);
                Log.d("TAG", "onResponse:getin" + number1);
                Log.d("TAG", "onResponse:getin" + email1);


                if (name1.isEmpty()){
                    name.setError("please enter your name");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    email.setError("please enter valid email address");
                }else {

                    //updated profile data getting
                    profileupdate();
                    Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
                }



            }

        });
    }
        public void profileupdate(){
            if (connection_detector.isConnectingToInternet()) {
                try {

                    OkHttpClient okHttpClient = new OkHttpClient();
                    RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                            client(okHttpClient).
                            addConverterFactory(GsonConverterFactory
                                    .create()).build();
                    API api = RestClient.client.create(API.class);
                    //Call<Profile> call = api.profile(name.getText().toString(),number.getText().toString(),email.getText().toString());
                    Log.d("TAG", "onResponse:getin" + name1);
                    Log.d("TAG", "onResponse:getin" + number1);
                    Log.d("TAG", "onResponse:getin" + email1);
                    Call<Profile_Response> call = api.profile1(name1,number2,email1);
                    call.enqueue(new Callback<Profile_Response>() {
                        @Override
                        public void onResponse(Call<Profile_Response> call,
                                               Response<Profile_Response> response) {
                            profile_response = response.body();
                            Log.d("TAG", "onResponse:getin hi" + profile_response);
                            try {
                                if (profile_response.getStatus().equalsIgnoreCase("true")) {
                                    name.setText(profile_response.getName());
                                    number.setText(profile_response.getNumber());
                                    email.setText(profile_response.getEmail());
                                    Config.savepassname(getApplicationContext(),profile_response.getName());

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "please update your profile once", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Profile_Response> call, Throwable t) {
                            t.getMessage();
                            Toast.makeText(getApplicationContext(),
                                    "Try Again",
                                    Toast.LENGTH_LONG).show();
                            Log.d("TAG", "onFailure:t" + t);
                        }

                    });
           /* Intent i=new Intent(getContext(),GenIn_Activity.class);
            startActivity(i);*/
                } catch (Exception e) {
                    System.out.println("msg:" + e);
                }
            }else
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

        }


    public void profile(){
        if (connection_detector.isConnectingToInternet()) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                //Call<Profile> call = api.profile(name.getText().toString(),number.getText().toString(),email.getText().toString());
              /*  Log.d("TAG", "onResponse:getin" + name1);
                Log.d("TAG", "onResponse:getin" + number1);
                Log.d("TAG", "onResponse:getin" + email1);*/
                Call<Profile_Response> call = api.profile(number2);
                call.enqueue(new Callback<Profile_Response>() {
                    @Override
                    public void onResponse(Call<Profile_Response> call,
                                           Response<Profile_Response> response) {
                        profile_response = response.body();
                        Log.d("TAG", "onResponse:getin" + profile_response);
                        try {
                            if (profile_response.getStatus().equalsIgnoreCase("true")) {
                                name.setText(profile_response.getName());
                                number.setText(profile_response.getNumber());
                                email.setText(profile_response.getEmail());
                                Config.savepassname(getApplicationContext(),profile_response.getName());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "please update your profile", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<Profile_Response> call, Throwable t) {
                        t.getMessage();
                        Toast.makeText(getApplicationContext(),
                                "Try Again",
                                Toast.LENGTH_LONG).show();
                        Log.d("TAG", "onFailure:t" + t);
                    }

                });
           /* Intent i=new Intent(getContext(),GenIn_Activity.class);
            startActivity(i);*/
            } catch (Exception e) {
                System.out.println("msg:" + e);
            }
        }else
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

    }

}
