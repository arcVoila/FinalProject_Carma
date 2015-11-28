package com.example.archana.finalproject_carma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class PreferenceActivity extends Activity implements Preference.OnPreferenceClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PreferenceFrag()).commit();

    }

    @Override
    public boolean onPreferenceClick (Preference preference)
    {
        Log.e("ARCHANA", "RAMA");
        String key = preference.getKey();
        if(key.equalsIgnoreCase("blockedApps")){
            Intent intent = new Intent(this,ListPhoneApps.class);
            startActivity(intent);
        }
       return true;
        // do what ever you want with this key
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
