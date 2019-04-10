package com.cadrac.hap_passenger.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.responses.currentroutelistResponse;
import com.cadrac.hap_passenger.responses.currentroutelistResponse;
import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentRoutes extends AppCompatActivity {
    TextView source;
    TextView destination;
    TextView vehicletype;
    TextView fare;
    TextView seats;
    TextView cancel;
    String phonenumber;
    currentroutelistResponse currentroutelistResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_current_routes);
        source=(TextView)findViewById( R.id.source );
        destination=(TextView)findViewById( R.id.destination );
        vehicletype=findViewById( R.id.vehicletype );
        fare=findViewById( R.id.fare );
        seats=findViewById( R.id.seats );
        cancel=findViewById( R.id.cancel );
        phonenumber = Config.getNumber(getApplication());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Current Routes");
        toolbar.setTitleTextColor( Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



        getCurrentdetails();
    }
    public void getCurrentdetails() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory( GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            //   Log.d("TAG", "onClick: " + mobileNum.getText().toString());

            //    num = mobileNum.getText().toString();


            //  Log.d("TAG", "onClick: " + num);
            Call<currentroutelistResponse> call = api.getDetails(phonenumber);
            call.enqueue(new Callback<currentroutelistResponse>() {
                @Override
                public void onResponse(Call<currentroutelistResponse> call,
                                       Response<currentroutelistResponse> response) {

                    currentroutelistResponse = new currentroutelistResponse();
                    currentroutelistResponse = response.body();
                    Log.d( "TAG", "onResponse: "+currentroutelistResponse);
//                    Log.d( "TAG", "onResponse:123 "+currentroutelistResponse.getStatus());
                    if(currentroutelistResponse.getStatus().equalsIgnoreCase("true")){
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        String s=currentroutelistResponse.getStatus();
                        String s1=currentroutelistResponse.getData()[0].getSource();
                        String s2=currentroutelistResponse.getData()[0].getDestination();
                        String s3=currentroutelistResponse.getData()[0].getSeats();
                        String s4=currentroutelistResponse.getData()[0].getVehicletype();
                        String s5=currentroutelistResponse.getData()[0].getFare();
                        source.setText( s1);
                        destination.setText( s2 );
                        seats.setText( s3 );
                        vehicletype.setText( s4 );
                        fare.setText( s5 );
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<currentroutelistResponse> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }


    }



}