package com.exampledemo.parsaniahardik.scanbarcodeqrdemonuts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanResActivity extends AppCompatActivity {
    private ZBarScannerView mScannerView;
    private  TextView tv_barcode;
    private String barcoderesult;
    //camera permission is needed.

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanres);
        Bundle extras = getIntent().getExtras();
        tv_barcode = (TextView) findViewById(R.id.tv_barcode);
        tv_barcode.setText(extras.getString("result"));

        JSONObject scanJO = new JSONObject();
        try {
            scanJO.put("result", "result");
           // scanJO.put("two", "LLAAASDJSAJDSADSA");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost("https://www.excerp.tech/api/demoPost", scanJO);
        Thread httpPostThread = new Thread();
        httpPostThread.start();
        try {
            httpPostThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v("RETURN FROM HTTP POT", httpPost.getResponse());
    }


}

