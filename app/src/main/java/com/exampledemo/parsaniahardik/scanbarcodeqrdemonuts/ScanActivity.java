package com.exampledemo.parsaniahardik.scanbarcodeqrdemonuts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private  TextView tvResult;
    private String barcoderesult;

    //camera permission is needed.

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanres);

        tvResult = (TextView) findViewById(R.id.tvresult);

        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {

        Bundle extras = new Bundle();
        extras.putString("result",  result.getContents());
        extras.putString("format",  result.getContents());

        // Do something with the result here
        Log.d("result", result.getContents()); // Prints scan results
        Log.d("format", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        Intent intent = new Intent(ScanActivity.this, ScanResActivity.class).putExtras(extras);
        startActivity(intent);
        finish();

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}

