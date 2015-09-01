package com.example.benjamintauber.lakewoodmap2;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, WebServiceListener{

    private GoogleMap mMap;
    private Polyline polyline = null;

    ArrayList<LatLng> arrayPoints = new ArrayList<LatLng>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        /*LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = lm.getBestProvider(criteria, true);
        Location location = lm.getLastKnownLocation(provider);*/
        /*Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);*/
        //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14.0f) );
        double gps[] = new double[2];
        gps = getGPS();
        if(gps[0] == 0.0){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.0936662, -74.2031944), 14.0f) );
        }else{
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps[0], gps[1]), 14.0f) );
        }


    }
    private double[] getGPS() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
        Location l = null;

        for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }

        double[] gps = new double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;
    }

    @Override
    public void onMapClick(LatLng point) {
        //Toast.makeText(this, "" + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();
        arrayPoints.add(point);
        //mMap.addMarker(new MarkerOptions().position(point).title("Marker"));
        drawLine();
    }

    @Override
    public void onMapLongClick(LatLng point) {
        //Toast.makeText(this, "" + point.latitude + " " + point.longitude, Toast.LENGTH_LONG).show();


    }
    public void goButtonClicked(View view) {
        Log.w("", "");
        if(arrayPoints.size() < 3){
            Toast.makeText(this, "You need 3 markers to create a polygon",Toast.LENGTH_LONG);
            return;
        }
        //mMap.clear();
        drawPolygon();
        Button button = (Button)findViewById(R.id.newSearch);
        button.setEnabled(true);

    }

    public void drawPolygon() {
        if(polyline != null){
            polyline.remove();
        }

        PolygonOptions rectOptions = new PolygonOptions();
        rectOptions.addAll(arrayPoints);
        rectOptions.strokeColor(Color.BLUE);
        rectOptions.strokeWidth(7);
        //rectOptions.fillColor(Color.CYAN);
        Polygon polygon = mMap.addPolygon(rectOptions);
        ArrayList<LatLng> ltln = (ArrayList)polygon.getPoints();
        JSONArray jsa = new JSONArray();
        JSONObject jso = null;
        for(int i = 0; i < ltln.size() - 1; i++){
            jso = new JSONObject();
            try {
                jso.put("k",(double)ltln.get(i).latitude);
                jso.put("B",(double)ltln.get(i).longitude);
                jsa.put(jso);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject mjo = new JSONObject();

        try {
            mjo.put("poly",jsa);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpService.startActionBaz(this,mjo.toString());

    }

    public void drawLine() {

        PolylineOptions linesOption = new PolylineOptions();
        linesOption.addAll(arrayPoints);
        linesOption.color(Color.BLUE);
        linesOption.width(7);
        polyline = mMap.addPolyline(linesOption);
    }

    public void newSearch(View view) {
        polyline = null;
        mMap.clear();
        arrayPoints = new ArrayList<LatLng>();
        view.setEnabled(false);

    }

    @Override
    public void onLoginActionComplete(String result) {

    }
}
