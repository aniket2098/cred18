package com.pisb.credenz18;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class AddReceipt extends AppCompatActivity {

    CameraSource cameraSource;
    SurfaceView cameraView;
    TextView textView;
    BarcodeDetector barcodeDetector;
    NetWork netWork;
    private static final int requestPermissionID = 101;
    Button button;
    RecepitData kkk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_add_receipt);

        button = findViewById(R.id.btn_add_receipt);


        netWork = new NetWork(AddReceipt.this);
        kkk = new RecepitData();


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_addreceipts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cameraView = findViewById(R.id.surfaceView);


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        textView = findViewById(R.id.text_view);

        try {
            startCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddReceipt.this, AddManually.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            String url = "https://sites.google.com/view/credenz-18-privacy-policies";

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);

            //pass the url to intent data
            intent.setData(Uri.parse(url));

            startActivity(intent);
        }
        else {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startCamera() throws IOException {

        if (!barcodeDetector.isOperational()) {
            Log.d("startCamera", "detector not loaded");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(2.0f)
                    .build();


            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(AddReceipt.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    requestPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
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
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    if (barcodes.size() != 0) {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(barcodes.valueAt(0).displayValue);
                                netWork.getDataFromNetwork(barcodes.valueAt(0).displayValue);
                                if(isNetworkAvailable()) {
                                    FancyToast.makeText(AddReceipt.this, "Scanned Successfully!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                }else{
                                    FancyToast.makeText(AddReceipt.this,"Please check your internet connection",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                }
                            }
                        });
                    }
                }
            });
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
