package com.example.benjamintauber.lakewoodmap2;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by benjamintauber on 8/26/15.
 */
public class DownloadTask extends AsyncTask<String, Integer, String> {
    private WebServiceListener wsl;
    public DownloadTask(WebServiceListener wsl){
        this.wsl = wsl;
    }
    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            //HttpPost postRequest = new HttpPost("http://192.168.10.140:3000");
            HttpPost postRequest = new HttpPost("http://smartlistlocal.com:8809");
            StringEntity input = new StringEntity(params[1]);
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
                //Intent listViewActivity = new Intent(conte, ListViewActivity.class);
                //Global global = (Global)getApplicationContext();
                //global.setData(result);
                //conte.startActivity(listViewActivity);
            }
        }catch(IOException e){

        }


        return result;
    }

    @Override
    protected void onPostExecute(String json){

    }
}

