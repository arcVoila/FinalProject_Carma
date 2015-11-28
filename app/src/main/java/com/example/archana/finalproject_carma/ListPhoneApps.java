package com.example.archana.finalproject_carma;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archana.DAO.BlockedAppsDAO;
import com.example.archana.POJO.BlockedAppsPOJO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ListPhoneApps extends ListActivity{
    private BlockedAppsDAO blockedAppsDAO;
    List<String> packageNames = new ArrayList<String>();
    Set<String> blockedApps = new HashSet<String>();
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    private PackageManager packageManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_phone_apps);
        packageManager = getPackageManager();
        new LoadApplications().execute();

     /*   List<BlockedAppsPOJO> values = blockedAppsDAO.getAllBAs();
        List<String> setToTrue = new ArrayList<String>();
        Log.d("Size of Blocked List ",values.size()+"");

        for(BlockedAppsPOJO bap : values){
            setToTrue.add(bap.getBlockedAppName());
            Log.d("HEREEEEEE",bap.getBlockedAppName());
        }

        List<Integer> positionsOfApps = new ArrayList<Integer>();
        final PackageManager pm = getPackageManager();*/
        //get a list of installed apps.
       /* List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        int position = 0;
        for (ApplicationInfo packageInfo : packages) {
            Log.d("TAG", "Installed package :" + packageInfo.packageName);
            if(setToTrue.contains(packageInfo.packageName))
                positionsOfApps.add(position);
            position++;
            packageNames.add(packageInfo.packageName);
            Log.d("TAG", "Source dir : " + packageInfo.sourceDir);
            Log.d("TAG", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
        }*/


       /* setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,packageNames));*/



       // ArrayAdapter<String> appAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, packageNames);
        //ListView appNames = (ListView)findViewById(R.id.listView);
        //appNames.setAdapter(appAdapter);


    }


    public void onListItemClick(ListView parent, View v,int position,long id){
        /*CheckedTextView item = (CheckedTextView) v;
        if(item.isChecked())
            blockedApps.add(packageNames.get(position));
        else
            blockedApps.remove(packageNames.get(position));*/
            Log.e("check","HEREEE");
      /*  Toast.makeText(this, packageNames.get(position) + " checked : "+ Toast.LENGTH_SHORT).show();*/
    }

    public void updateBlockedAppList(View v){

     RelativeLayout r1 = (RelativeLayout)v.getParent();

     String app_name = r1.findViewById(R.id.app_name).toString();
     //String status = r1.findViewById(R.id.doneCheckBox).toString();

     //Log.d("TAG", app_name + " "+status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_phone_apps, menu);
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


    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }


    private void loadSavedPreferences() {

        SharedPreferences sharedPreferences = getApplicationContext()

                .getSharedPreferences("blockedApps", Context.MODE_PRIVATE);
        int count = 0;
        for(ApplicationInfo appInfo : applist){

            boolean name = sharedPreferences.getBoolean(String.valueOf(appInfo.loadLabel(packageManager)),false);
           //Log.e("Check Bool Value ", name+"");

        }

    }
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            Collections.sort(applist,new Comparator<ApplicationInfo>() {
                @Override
                public int compare(ApplicationInfo lhs, ApplicationInfo rhs) {

                    String app1 = String.valueOf(lhs.loadLabel(packageManager)).toLowerCase();
                    String app2 =String.valueOf(rhs.loadLabel(packageManager)).toLowerCase();

                    //ascending order
                    return app1.compareTo(app2);

                }
            });
              //ListView listView = getListView();
               // listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
            //ListView appNames = (ListView)findViewById(R.id.listView);
            listadaptor = new ApplicationAdapter(ListPhoneApps.this,
                   android.R.layout.simple_list_item_checked, applist);

            //listView.setAdapter(listadaptor);
            //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            loadSavedPreferences();
            return null;
        }



        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.d("OMGGGGGGGGGGGGGGGGg","haha");
            setListAdapter(listadaptor);
            //getListView().setChoiceMode(getListView().CHOICE_MODE_MULTIPLE);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ListPhoneApps.this, null,
                    "Loading applications on your phone ...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}

