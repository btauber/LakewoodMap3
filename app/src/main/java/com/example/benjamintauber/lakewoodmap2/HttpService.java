package com.example.benjamintauber.lakewoodmap2;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;


public class HttpService extends IntentService implements Serializable {

    private static final String ACTION_BAZ = "com.example.benjamintauber.lakewoodmap.action.BAZ";
    private static final String EXTRA_PARAM1 = "com.example.benjamintauber.lakewoodmap.extra.PARAM1";


    private static Context conte;



    public static void startActionBaz(Context context, String param1) {
        conte = context;
        Intent intent = new Intent(context, HttpService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    public HttpService() {
        super("HttpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String jsonObject = intent.getStringExtra(EXTRA_PARAM1);
        String result = "";
        JSONArray json;

        try {


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://192.168.10.140:3000");

            StringEntity input = new StringEntity(jsonObject);
            input.setContentType("application/json;charset=UTF-8");
            postRequest.setEntity(input);
            input.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
            postRequest.setHeader("Accept", "application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            if(result.equals("") == false){
                Intent listViewActivity = new Intent(conte, ListViewActivity.class);
                Global global = (Global)getApplicationContext();
                global.setData(result);
                conte.startActivity(listViewActivity);
            }//
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
