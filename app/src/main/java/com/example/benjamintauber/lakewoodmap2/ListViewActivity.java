package com.example.benjamintauber.lakewoodmap2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.benjamintauber.lakewoodmap2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ListViewActivity extends Activity {
    ArrayAdapter<Person> adapter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Intent intent = this.getIntent();
        JSONArray listViewData = null;
        Global global = (Global)getApplicationContext();
        try {
            listViewData = new JSONArray(new String(global.getData()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ListAdapter(this,R.layout.list_layout);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        setDataInList(listViewData);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,  AlertDialog.THEME_HOLO_DARK);
                alertDialogBuilder.setTitle("Calling...");
                alertDialogBuilder
                        .setMessage("Click no to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Person person = adapter.getItem(position);
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+person.getPhone()));
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        String filename = "myfile.txt";
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        if(file.exists())
            file.delete();
        try{
            file.createNewFile();
            file.setReadable(true,false);
            file.setWritable(true,false);


            String string = new String(global.getData());
            FileOutputStream outputStream = new FileOutputStream(file);

            //com.csvreader.CsvReader
            outputStream = openFileOutput(file.getName(), Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDataInList(JSONArray jsa){
        ArrayList<Person> allListing = new ArrayList<Person>();
        int length = jsa.length();
        for(int i = 0; i < length; i++ ){
            try {
                JSONObject jso = jsa.getJSONObject(i);
                allListing.add(new Person(jso.getString("family_name"),
                        jso.getString("first_name"),
                        jso.getString("spouse"),
                        jso.getString("address"),
                        jso.getString("phone")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.clear();
        adapter.addAll(allListing);


    }

}
