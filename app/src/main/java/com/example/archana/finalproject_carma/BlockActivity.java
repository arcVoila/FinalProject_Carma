package com.example.archana.finalproject_carma;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class BlockActivity extends Activity {

    String phoneNo4;
    String phoneNo2;
    String phoneNo3;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        actionBar = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(
                Color.parseColor("#9999FF"));
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        actionBar.setBackgroundDrawable(colorDrawable);



        setNamesForContacts();
    }

    private void setNamesForContacts() {
        SharedPreferences prefs = getApplicationContext()
                .getSharedPreferences("phoneNosFinal", Context.MODE_PRIVATE);

        Map<String,?> keys = prefs.getAll();

        int count = 0;
        String button4 = null;
        String button2 = null;
        String button3 = null;
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " +
                    entry.getValue().toString());
            count++;
            if(count == 1){
                button2 = entry.getKey();
                phoneNo2 =   entry.getValue().toString();
                phoneNo2 = phoneNo2.replace("-","");
            }

            else if(count == 2){
                button3 =  entry.getKey();
                phoneNo3 = entry.getValue().toString();
            }

                else if(count == 3){
                button4 =  entry.getKey();
                phoneNo4 = entry.getValue().toString();
            }

        }
        Log.d("ENAMES", button2 + " "+button3+ " "+button4);


        Button mButton2=(Button)findViewById(R.id.button2);
        if(button2!=null)
            mButton2.setText(button2);
        else
            mButton2.setVisibility(View.INVISIBLE);


        Button mButton3=(Button)findViewById(R.id.button3);
        if(button3!=null){

            mButton3.setText(button3);
        }
        else
           mButton3.setVisibility(View.INVISIBLE);


        Button mButton4=(Button)findViewById(R.id.button4);
        if(button4!=null)
             mButton4.setText(button4);
        else
            mButton4.setVisibility(View.INVISIBLE);

    }

/*****************/

    public void placePicker(View view){
        SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedSafeSpot = null;
        if (spref.contains("safeSpots")) {
            selectedSafeSpot = spref.getString("safeSpots", "");
        }
        String actualPlace = null;
        switch(selectedSafeSpot){
            case "1": actualPlace = "restaurants";break;
            case "2": actualPlace = "parking spots";break;
            case "3": actualPlace = "gas stations";break;
            case "4": actualPlace = "coffee shops";break;
        }
        Log.d("SELECTED SAFE sPOT",selectedSafeSpot+"");
        Uri gmmIntentUri = Uri.parse("geo:0.0?q="+actualPlace);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    /*****************/

    public void callPerson2(View view){

        Uri number = Uri.parse("tel:"+phoneNo2);
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);
    }

    public void callPerson3(View view){

        Uri number = Uri.parse("tel:"+phoneNo3);
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);
    }

    public void callPerson4(View view){

        Uri number = Uri.parse("tel:"+phoneNo4);
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_block, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
