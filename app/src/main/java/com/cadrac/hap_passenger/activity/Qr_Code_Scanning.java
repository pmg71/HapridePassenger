package com.cadrac.hap_passenger.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.cadrac.hap_passenger.MainActivity;
import com.cadrac.hap_passenger.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Qr_Code_Scanning extends AppCompatActivity {
    SurfaceView surfaceView;
    TextView textView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    String val="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__code__scanning);
        surfaceView = (SurfaceView) findViewById(R.id.camera);
        textView = findViewById(R.id.textview);


        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        Log.d("TAG", "surfaceCreated:amith ");

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();
        Log.d("TAG", "surfaceCreated:amith11 ");


//        qrbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                val=editText.getText().toString();
//                    Intent i = new Intent(getApplicationContext(), FeedBack.class);
//                    i.putExtra("qrcode", val);
//                    startActivity(i);
//
//            }
//        });

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try
                {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                Log.d("TAG", "surfaceCreated:amith11d ");
                final SparseArray<Barcode> qrcode= detections.getDetectedItems();
                if (qrcode.size() != 0)
                {


//                    Log.d("TAG", "receiveDetections: aaa");
//
//               /*     Intent i = new Intent(getApplicationContext(), FeedBack.class);
//                    i.putExtra("qrcode", val);
//                    startActivity(i);*/
//                    Log.d("TAG", "surfaceCreated:amith11 ");
//                    Toast.makeText(Scanning.this, val, Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "surfaceCreated:amith11 "+ val);

                    textView.post(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getApplicationContext(), FeedBack.class);
                            intent.putExtra("session", qrcode.valueAt(0).displayValue);
                            startActivity(intent);
                        }
                    });

                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }
}