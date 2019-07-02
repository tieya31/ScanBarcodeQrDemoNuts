package com.exampledemo.parsaniahardik.scanbarcodeqrdemonuts;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpPost implements Runnable{
    private volatile StringBuilder response;
    private String url;
    private JSONObject dataJO;

    public HttpPost(String url, JSONObject dataJO){
        this.response = new StringBuilder();
        this.url = url;
        this.dataJO = dataJO;
    }

    private void setPostRequestContent(HttpURLConnection conn) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(this.dataJO.toString());
        writer.flush();
        writer.close();
        os.close();
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            setPostRequestContent(conn);
            conn.connect();

            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("HTTP RESPONSE", response.toString());
    }

    public String getResponse(){
        return response.toString();
    }
}