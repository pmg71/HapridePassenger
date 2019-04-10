package com.cadrac.hap_passenger;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap_passenger.activity.CurrentRoutes;
import com.cadrac.hap_passenger.activity.Home;
import com.cadrac.hap_passenger.activity.PassengerLogin;
import com.cadrac.hap_passenger.activity.Profile;
import com.cadrac.hap_passenger.activity.Qr_Code_Scanning;
import com.cadrac.hap_passenger.activity.aboutus;
import com.cadrac.hap_passenger.activity.contactus;
import com.cadrac.hap_passenger.activity.howtouseapp;
import com.cadrac.hap_passenger.adapters.AgentListAdapter;
import com.cadrac.hap_passenger.adapters.RouteListAdapter;
import com.cadrac.hap_passenger.responses.RouteListResponse;
import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity  extends FragmentActivity implements OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
TextView name;

    RecyclerView recyclerView,recyclerView1,recyclerView2;
    TextView textView,agentavl,route;
    // android.support.v7.widget.SearchView search;
    EditText search;
    private GoogleMap mMap;

    RouteListAdapter routeListAdapter;
    AgentListAdapter agentListAdapter;
    Button  feedback;


    ArrayList<RouteListResponse.data> data = new ArrayList<>();
    RouteListResponse routeListResponse;

    Context context;




    int i;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location currentlocation, freshloc;
    private Marker currentlocationmarker;
    public static final int REQUEST_LOCATION_CODE = 99;

    private Button searchb;
    // private LocationResponse locationResponse;
    float currentlattitude, currentlongitude;
    int l;
    private  String username,friend_name;
    CameraPosition cameraPosition;
    String url,gi_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Your Status");
        toolbar.setTitleTextColor(Color.WHITE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        feedback= findViewById(R.id.feedback);

        feedback.setOnClickListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        name=(TextView)header.findViewById(R.id.name);
        name.setText(Config.getpassname(getApplicationContext()));
        navigationView.setNavigationItemSelectedListener(this);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search=(EditText) findViewById(R.id.search);
        recyclerView = (RecyclerView)findViewById( R.id.recyclerView );
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerView2);
       agentavl=(TextView)findViewById(R.id.agentavl);
       route=(TextView)findViewById(R.id.routes);
        /*

        noroute=(TextView)findViewById(R.id.noroute);
        noroute.setVisibility(View.GONE);
*/

        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        recyclerView.setVisibility(View.GONE);
        l=0;

    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();

        }*/
        routes();
        //  buildApiClient();

//        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//                routeListAdapter.getFilter().filter(query);
//                recyclerView.setVisibility(View.VISIBLE);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                // filter recycler view when text is changed
//                try {
//                    routeListAdapter.getFilter().filter(query);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }catch (Exception e) {
//                    recyclerView.setVisibility(View.GONE);
//                    return false;
//                }
//                return false;
//            }
//        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                route.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                routeListAdapter.getFilter().filter(s.toString());
                if(s.length()==0)
                {
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.GONE);
                    route.setVisibility(View.GONE);
                    agentavl.setVisibility(View.GONE);

                    /*agentavl.setText("");*/

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void routes(){
        //  if (connection_detector.isConnectingToInternet()) {
        // Config.showLoader(this);
        data = new ArrayList<RouteListResponse.data>();


        OkHttpClient okHttpClient = new OkHttpClient();
        RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory
                        .create()).build();
        API api = RestClient.client.create(API.class);


        Call<RouteListResponse> call = api.routes();


        call.enqueue(new Callback<RouteListResponse>() {
            @Override
            public void onResponse(Call<RouteListResponse> call, Response<RouteListResponse> response) {

                routeListResponse = response.body();
                Log.d("TAG", "resbodybg" + routeListResponse);

                try {

                    if (routeListResponse.getStatus().equalsIgnoreCase("true")) {

                        for (int i = 0; i < routeListResponse.getData().length; i++) {

                            Log.d("TAG", "resbody" + routeListResponse.getData()[i].getLattitude());
                            data.add(routeListResponse.getData()[i]);
                            route.setText("No Routes Avliable try another route");
                        }
                    }
                    Log.d("TAG", "onResponse:true" + data);
                    setListView();
                } catch (Exception e) {
                   // route.setText("No Routes");


                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<RouteListResponse> call, Throwable t) {
                /*noroute.setVisibility(View.VISIBLE);*/
                route.setText("No Routes");
                Log.d("TAG", "onResponse:nottrue");
                Log.d("TAG", "onFailure:t" + t);
            }
        });
//        }else {
//            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
//        }

    }
    public void setListView() {

        Log.d("TAG", "setListView: "+data);
        // getoutAdapter = new GetOutAdapter(data,GetOutActivity2.this,getApplicationContext() );
        routeListAdapter=new RouteListAdapter(data,MainActivity.this,getApplicationContext());
        recyclerView.setAdapter(routeListAdapter);
        routeListAdapter.notifyDataSetChanged();

    }


    //agent list dialog
    public void AgentListDialog(final String source, LatLng latLng) {


        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.agentlistadapter,null);

        search.setText(source);

        Config.saveSource(getApplicationContext(),source);

        search.setSelection(search.getText().length());
        recyclerView1=(RecyclerView)view.findViewById(R.id.recyclerView1);
        textView=(TextView)view.findViewById(R.id.textview);
        recyclerView2.setVisibility(View.VISIBLE);
        agentavl.setVisibility(View.VISIBLE);

        final LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.ll2);

        agents(source);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
       // dialog.show();
        recyclerView.setVisibility(View.GONE);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title(source));
        cameraPosition = new CameraPosition.Builder()
                .target(latLng)             // Sets the center of the map to Mountain View
                //     .zoom(8)                   // Sets the zoom
                .bearing(30)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        //  mMap.animateCamera(CameraUpdateFactory.zoomBy(15));



    }

    public void agents(String source){


        OkHttpClient okHttpClient = new OkHttpClient();
        RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory
                        .create()).build();
        API api = RestClient.client.create(API.class);
        data = new ArrayList<>();

        Call<RouteListResponse> call = api.agents(source);


        call.enqueue(new Callback<RouteListResponse>() {
            @Override
            public void onResponse(Call<RouteListResponse> call, Response<RouteListResponse> response) {

                routeListResponse=response.body();
                Log.d("TAG", "resbodybg"+routeListResponse);

                try{
                    Log.d("TAG", "resbody"+routeListResponse.getStatus());
                    if (routeListResponse.getStatus().equalsIgnoreCase( "True" )){

                        for (int i = 0; i < routeListResponse.getData().length; i++) {

                            //  Log.d("TAG", "onResponse:r"+routeListResponse.getData()[i].getPlaces());
                            data.add( routeListResponse.getData()[i]);
                            gi_id=data.get(0).getAgentid();
                            Config.saveinAgent(getApplicationContext(),gi_id);
                           agentavl.setText("Avaliable Agents");


                        }
                        Log.d("TAG", "onResponse:number"+data.get(0).getAgentnumber());
                        Log.d("TAG", "onResponse:name"+data.get(0).getAgentname());
                        Log.d("TAG", "onResponse:id"+data.get(0).getAgentid());
                    }
                    Log.d("TAG", "onResponse:true"+data);
                    setListView1();
                }catch(Exception e)
                {
                   agentavl.setText("No Agents Avaliable");
                    e.printStackTrace();

                }
//
//

            }

            @Override
            public void onFailure(Call<RouteListResponse> call, Throwable t) {
                agentavl.setText("No Agents Avaliable");
                Log.d("TAG", "onResponse:nottrue");
                Log.d("TAG", "onFailure:t"+t);
            }
        });

    }
    public void setListView1() {

        Log.d("TAG", "setListView: "+data);
        // getoutAdapter = new GetOutAdapter(data,GetOutActivity2.this,getApplicationContext() );
        agentListAdapter=new AgentListAdapter( data,MainActivity.this,getApplicationContext() );
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(agentListAdapter);

    }





    public void turnGPSOn(){
        try
        {

            LocationManager locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            locationManager.setTestProviderEnabled(provider,true);

            Log.d("TAG", "turnGPSOn: ");

            //    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if(!provider.contains("gps")){ //if gps is disabled

                Log.d("TAG", "turnGPSOn:1 ");
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        }
        catch (Exception e) {
            Log.d("TAG", "turnGPSOn: "+e);
        }
    }







    @Override
    public void onMapReady(GoogleMap googleMap) {

//turnGPSOn();


        mMap = googleMap;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMyLocationEnabled(true);
    }


    protected synchronized void buildApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    @Override
    public void onLocationChanged(Location location) {

        Log.d("TAG", "onLocationChanged: "+location);


        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("your location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        if(l==0) {
            cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))             // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .bearing(30)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),15));

            //  mMap.animateCamera(CameraUpdateFactory.zoomBy(15));
            l=1;
        }


    }

  /*  @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildApiClient();

                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest=LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        Log.d("TAG", "onConnected: 11");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /*public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }*/





    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home)
        {
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);


        }
        else if (id == R.id.profile)
        {
            //ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this);

            Intent i=new Intent(getApplicationContext(), Profile.class);
            startActivity(i);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                startActivity(i,optionsCompat.toBundle());
//            }
        }
        else if (id == R.id.currentroutes)
        {

            Intent i=new Intent(getApplicationContext(), CurrentRoutes.class);
            startActivity(i);
        }

        else if (id == R.id.Howtouseapp)
        {

            Intent i=new Intent(getApplicationContext(), howtouseapp.class);
            startActivity(i);
        }
        else if (id == R.id.aboutus)
        {
            Intent i=new Intent(getApplicationContext(), aboutus.class);
            startActivity(i);

        }
        else if (id == R.id.contactus)
        {
            Intent i=new Intent(getApplicationContext(), contactus.class);
            startActivity(i);

        }

        else if (id == R.id.logout)
        {

            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("Do you want to logout..!");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Config.saveLoginStatus(getApplicationContext(),"0");
                    Intent intent = new Intent(getApplicationContext(), PassengerLogin.class);
                    startActivity(intent);

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            AlertDialog alertDialog=builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.feedback)
        {
            Intent i = new Intent(getApplicationContext(), Qr_Code_Scanning.class);
            startActivity(i);
        }

    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(this, "Press Back again to Exit from App.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

}
