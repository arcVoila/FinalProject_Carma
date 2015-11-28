package com.example.archana.finalproject_carma;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


public class SafePlace extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadPlacePicker(1);
        Intent intent = getIntent();
        // String value = intent.getStringExtra("Safe Location");*/
        setContentView(R.layout.activity_safe_place);
    }


    private void LoadPlacePicker(int PlacePickerRequest)
    {
        try {

            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(SafePlace.this);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, PlacePickerRequest);

        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), SafePlace.this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(SafePlace.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                LinearLayout layout = new LinearLayout(this);

                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_safe_place, menu);
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
