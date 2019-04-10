package com.cadrac.hap_passenger.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cadrac.hap_passenger.R;

import com.cadrac.hap_passenger.responses.PassengerRequestResponse;

import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class PassengerRequest extends AppCompatActivity {

   // RecyclerView recyclerView;
    TextView source,fare,seatstext,vehicletypetxt,book;
    ImageView to,rupee;
    Spinner destination,seats,vehicletype;
    String selectseats ,selectdestination;
    ArrayList<String> places, veh_type,list;


    String source1,mvehicletype,seats_count,carcost,autocost;
    String l,autofare,carfare;
    int k,i,fare1,u;




    //  ArrayList<PassengerRequestResponse.data> data = new ArrayList<PassengerRequestResponse.data>();
    PassengerRequestResponse passengerRequestResponse;
   // PassengerRequestAdapter passengerRequestAdapter;

  //  String source,destination,fare,vehicletype,seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_request);

        source1 = Config.getSource(getApplicationContext());

        source=findViewById(R.id.source);
        destination=findViewById(R.id.destination);
        fare=findViewById(R.id.fare);
        seatstext=findViewById(R.id.seatstxt);
        vehicletypetxt=findViewById(R.id.vehicletypetxt);
        book=findViewById(R.id.book);

        to=findViewById(R.id.to);
        rupee=findViewById(R.id.rupee);

        seats=findViewById(R.id.seats);
        vehicletype=findViewById(R.id.vehicletype);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Passenger Request");
        toolbar.setTitleTextColor( Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        source.setText(source1);


        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectdestination = destination.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: "+destination);

                if (selectdestination.equalsIgnoreCase("Please Select Your destination") ){


                }else{
                    dispVehType();
                }


                vehicletype.setSelection(0);
                seats.setSelection(0);
                fare.setText("");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vehicletype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mvehicletype = vehicletype.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: vehicletype"+mvehicletype);

                seatsSpinner();
                costDetails();
                seats.setSelection(0);
                fare.setText("");




//                fareCal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //seatsSpinner();
        seats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectseats = seats.getSelectedItem().toString();
                Log.d("TAG", "onItemSelected: "+selectseats);
                seatsSpinner();
             //  costDetails();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        destinationSpinner();

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if (selectdestination.equalsIgnoreCase("Please Select Your destination") ){
                            Toast.makeText(getApplicationContext(),
                                    "select destination",
                                    Toast.LENGTH_LONG).show();

                        }
                else if (mvehicletype.equalsIgnoreCase("Vehicle Type")) {
                    Toast.makeText(getApplicationContext(),
                            "select vehicle type",
                            Toast.LENGTH_LONG).show();
                }
                        else if(u==0){
                            Toast.makeText(getApplicationContext(),
                                    "Select seats",
                                    Toast.LENGTH_LONG).show();
                        }

                        else{
                             //inserting data to db
                            datainsertion();

                            destination.setSelection(0);
                            vehicletype.setSelection(0);
                            seats.setSelection(0);
                            fare.setText("");

                        }

            }
        });


    }




    private void destinationSpinner() {

        Log.d("TAG", "sourceSpinner: ");

        try {

            passengerRequestResponse=new PassengerRequestResponse();
            Log.d("TAG", "sourceSpinner:1 ");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<PassengerRequestResponse> call = api.destination(source1);
            call.enqueue(new Callback<PassengerRequestResponse>() {
                @Override
                public void onResponse(Call<PassengerRequestResponse> call,
                                       Response<PassengerRequestResponse> response) {

                    passengerRequestResponse = response.body();
                    places=new ArrayList<String>();
                    Log.d("TAG", "onResponse: 1");
                    if (passengerRequestResponse.getStatus().equalsIgnoreCase("sucess")) {
                        Log.d("TAG", "onResponse: 2");
                        for(int i=0;i<passengerRequestResponse.getData().length;i++) {
                            places.add(passengerRequestResponse.getData()[i].getDestination().toString());
                        }
                        setPlacesAdapter(places);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"sorry",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call <PassengerRequestResponse> call,Throwable t) {
                    Log.d("TAG", "onFailure: "+t);
                }

            });
        }
        catch (Exception e){
            System.out.print( "Exception e" + e );

        }

    }

    public void setPlacesAdapter(ArrayList<String> place) {

        ArrayList<String> fun;
        fun = new ArrayList<String>();

        fun.add(0,"Please Select Your destination");

        fun.addAll(place);



        destination.setSelection(0);
        destination.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }

    public void dispVehType(){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Log.d("TAG", "onResponse:12 ");
            Call<PassengerRequestResponse> call = api.vehicleType();
            Log.d("TAG", "onResponse:123 ");
            call.enqueue(new Callback<PassengerRequestResponse>() {

                @Override
                public void onResponse(Call<PassengerRequestResponse> call,
                                       Response<PassengerRequestResponse> response) {
                    Log.d("TAG", "onResponse:1234 ");
                    veh_type = new ArrayList<String>();
                    passengerRequestResponse = new PassengerRequestResponse();
                    Log.d("TAG", "onResponse:type "+passengerRequestResponse.getStatus());
                    passengerRequestResponse = response.body();

                    if (passengerRequestResponse.getStatus().equalsIgnoreCase("success")) {
                        Log.d("TAG", "onResponse: 2");
                        try {


                            for (int i = 0; i < passengerRequestResponse.getData().length; i++) {
                                veh_type.add(passengerRequestResponse.getData()[i].getVehicletype().toString());
                                Log.d("TAG", "onResponse: vtype"+passengerRequestResponse.getData()[i].getVehicletype().toString());
                            }
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        setVehAdapter(veh_type);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "sorry", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PassengerRequestResponse> call, Throwable t) {

                }


            });

        }catch (Exception e) {
            System.out.print("Exception e" + e);

        }
    }
    public void setVehAdapter(ArrayList<String> rolelist) {

        ArrayList<String> fun;
        fun = new ArrayList<String>();

        fun.add(0,"Vehicle Type");

        fun.addAll(rolelist);

        vehicletype.setSelection(0);
        vehicletype.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }



    private void seatsSpinner() {

        Log.d("TAG", "sourceSpinner: ");

        try {

            passengerRequestResponse=new PassengerRequestResponse();
            Log.d("TAG", "sourceSpinner:1 ");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Log.d("TAG", "onItemSelected: vehicletype1"+mvehicletype);


            Call<PassengerRequestResponse> call = api.seatcount(mvehicletype);
            call.enqueue(new Callback<PassengerRequestResponse>() {
                @Override
                public void onResponse(Call<PassengerRequestResponse> call,
                                       Response<PassengerRequestResponse> response) {

                    passengerRequestResponse = response.body();
                    //seats_count=new ArrayList<String>();
                    Log.d("TAG", "onResponse: 1");
                    if (passengerRequestResponse.getStatus().equalsIgnoreCase("success")) {
                        Log.d("TAG", "onResponse: 2");
                       /* for(int i=0;i<passengerRequestResponse.getData().length;i++) {

                            seats_count.add(passengerRequestResponse.getData()[i].getSeats().toString());
                            Log.d("TAG", "seatscount"+ passengerRequestResponse.getData()[i].getSeats().toString());
                        }*/
                       seats_count=passengerRequestResponse.getSeats();

                        Log.d("TAG", "seatscount"+ passengerRequestResponse.getSeats());
                        //setSeatsAdapter(seats_count);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call <PassengerRequestResponse> call,Throwable t) {
                    Log.d("TAG", "onFailure: "+t);
                }

            });
        }
        catch (Exception e){
            System.out.print( "Exception e" + e );

        }

    }

 /*   public void setSeatsAdapter(ArrayList<String> place) {

        ArrayList<String> fun;
        fun = new ArrayList<String>();

        int seatsLeft =Integer.parseInt(seats_count);


        for(int i=1;i<=(seatsLeft);i++){
            fun.add("passenger "+String.valueOf(i));
            System.out.println("seats are "+i);

        }

        fun.add(0,"Please Select seats");

        fun.addAll(place);



        seats.setSelection(0);
        seats.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, fun));
    }*/

    public void costDetails(){
      //  if (connection_detector.isConnectingToInternet()) {
            try {


                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Log.d("TAG", "onItemSelected: so"+source1);
                Log.d("TAG", "onItemSelected: de"+selectdestination);

                Log.d("TAG", "onItemSelected: ve"+mvehicletype);
                Call<PassengerRequestResponse> call = api.cost(source1,
                        selectdestination,mvehicletype);
                call.enqueue(new Callback<PassengerRequestResponse>() {
                    @Override
                    public void onResponse(Call<PassengerRequestResponse> call,
                                           Response<PassengerRequestResponse> response) {


                        passengerRequestResponse = new PassengerRequestResponse();
                        passengerRequestResponse = response.body();
                        Log.d("TAG", "onResponse:1234567890 " + passengerRequestResponse);
                        try {

                            /*if(mvehicletype.equalsIgnoreCase("Auto")){
                                Log.d("TAG", "onItemSelected: vehicletype11"+mvehicletype);
                                Log.d("TAG", "autofare"+passengerRequestResponse.getData()[0].getAutocost());
                                autofare=passengerRequestResponse.getData()[0].getAutocost();
                                fare.setText(autofare);


                            }else if(mvehicletype.equalsIgnoreCase("Car")){

                                Log.d("TAG", "carfare"+passengerRequestResponse.getData()[0].getCabcost());


                                carfare=passengerRequestResponse.getData()[0].getCabcost().toString();
                                fare.setText(carfare);

                            }*/
                            Log.d("TAG", "autofare" + passengerRequestResponse.getStatus());
                            if (passengerRequestResponse.getStatus().equalsIgnoreCase("true")) {
                               autofare= passengerRequestResponse.getCost();
                                fare.setText(autofare);
                                fareCal();
                                Log.d("TAG", "autofare" + passengerRequestResponse.getCost());

                            }else{

                            }


                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onFailure(Call<PassengerRequestResponse> call, Throwable t) {
                        Log.d(TAG, "onItemSelected:  failed" + t);
                    }

                });
            } catch (Exception e) {
                System.out.print("Exception e" + e);

            }
       // }else Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }

    public void fareCal(){

        list = new ArrayList<String>();
        Log.d(TAG, "fareCal: "+seats_count);
        int seatsLeft =Integer.parseInt(seats_count);


        for(int i=1;i<=(seatsLeft);i++){
            list.add("passenger "+String.valueOf(i));
            System.out.println("seats are "+i);

        }
        fare1=Integer.parseInt(autofare);
        Log.d(TAG, "fareCal22:"+autofare);




        list.add(0, "Select seats");



        seats.setSelection(0);
        seats.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,list));

        seats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = seats.getSelectedItem().toString();



                u = position;

                Log.d("TAG", "onItemSelected: " +position);
                Object value = parent.getItemAtPosition(position);

                switch (u) {
                    case 0:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;
                    case 1:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;

                    case 2:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;

                    case 3:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;
                    case 4:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;
                    case 5:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;

                    case 6:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;

                    case 7:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;
                    case 8:
                        k = u * (fare1);
                        l = Double.toString(k);
                        fare.setText(l);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }

    public void datainsertion() {

            try {
                String numberofseats = String.valueOf(u);


                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                String usernumber = Config.getNumber(getApplicationContext());
                String gi_id=Config.getinAgent(getApplicationContext());
                Log.d(TAG, "hai:gi_id"+gi_id);
                Log.d(TAG, "hai:user "+usernumber);
                Log.d(TAG, "hai:so"+source1);
                Log.d(TAG, "hai:de "+selectdestination);
                Log.d(TAG, "hai:ve "+mvehicletype);
                Log.d(TAG, "hai:ns "+numberofseats);
                Log.d(TAG, "hai:af"+l);
                Call<PassengerRequestResponse> call = api.datainsertion(gi_id,usernumber,source1, selectdestination, mvehicletype,numberofseats,l);
                call.enqueue(new Callback<PassengerRequestResponse>() {
                    @Override
                    public void onResponse(Call<PassengerRequestResponse> call,
                                           Response<PassengerRequestResponse> response) {
                        passengerRequestResponse = response.body();
                        Toast.makeText(getApplicationContext(), "Ride Requested", Toast.LENGTH_SHORT).show();

                   /* Intent i1=new Intent(getContext(),GetOutActivity.class);
                    startActivity(i1);*/
                    }

                    @Override
                    public void onFailure(Call<PassengerRequestResponse> call, Throwable t) {
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


    }


}
