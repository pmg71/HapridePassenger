package com.cadrac.hap_passenger.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap_passenger.MainActivity;
import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.responses.FeedBackResponse;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedBack extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    EditText editText,rideid;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FeedBackResponse FeedBackResponse1;
    String sessionId;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        textView = findViewById(R.id.driverid);
        editText = findViewById(R.id.review);
        rideid = (EditText)findViewById(R.id.rideid);
        button = findViewById(R.id.submit);
        radioGroup = findViewById(R.id.radioo);

        button.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("About Us");
        toolbar.setTitleTextColor(Color.WHITE);


        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        sessionId= getIntent().getStringExtra("session");

        textView.setText(sessionId);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.submit)
        {

            String b=editText.getText().toString();
            Log.d("TAG", "onClick: a"+b);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            Log.d("TAG", "onClick: radio"+ radioButton);






            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<FeedBackResponse> call = api.passengerfeedback(sessionId,rideid.getText().toString(),radioButton.getText().toString(),b);
                call.enqueue(new Callback<FeedBackResponse>() {
                    @Override
                    public void onResponse(Call<FeedBackResponse> call, Response<FeedBackResponse> response) {

                        FeedBackResponse1 = response.body();
                        if (FeedBackResponse1.getStatus().equalsIgnoreCase("True")) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<FeedBackResponse> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();

                    }
                });

            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }




        }

    }
    public void feedback()
    {

    }


}