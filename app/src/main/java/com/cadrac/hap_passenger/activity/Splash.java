package com.cadrac.hap_passenger.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cadrac.hap_passenger.MainActivity;
import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.utils.Config;

public class Splash extends AppCompatActivity {
    private TextView mytext;
    private ImageView mylogo;
    Animation fromtext,uplogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mytext=(TextView) findViewById(R.id.subtitle);
        mylogo=(ImageView) findViewById(R.id.applogo);
//
//        fromtext= AnimationUtils.loadAnimation(this,R.anim.bounce);
//        uplogo=AnimationUtils.loadAnimation(this,R.anim.fromtext);
//        mytext.setAnimation(uplogo);
//        mylogo.setAnimation(fromtext);

//        Animation myanimation= AnimationUtils.loadAnimation(this,R.anim.mytransation);
//        mytext.startAnimation(myanimation);
//        mylogo.startAnimation(myanimation);
        final Intent intent;
        if(Config.getLoginStatus(getApplicationContext()).equals("1")){
            intent=new Intent(this, MainActivity.class);

        }

         else {
            intent=new Intent(this,PassengerLogin.class);

        }
        Thread timer=new Thread(){

            public void run(){
                try{
                    sleep(1000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }


        };

        timer.start();

    }
}
