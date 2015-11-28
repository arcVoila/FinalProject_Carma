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
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;


public class MainActivity extends Activity {


    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        actionBar = getActionBar();

        ColorDrawable colorDrawable = new ColorDrawable(
                Color.parseColor("#9999FF"));
        actionBar.setBackgroundDrawable(colorDrawable);




       // Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        //startActivity(intent);
        MonitorApps ma = new MonitorApps();
        Log.e("permission",needPermissionForBlocking(getBaseContext())+"");
        //if(needPermissionForBlocking(getBaseContext()))
            //printForegroundTask();
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MonitorService.class));

    }
    public void emergencyContactList(View view){

        //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        Intent intent = new Intent(this,EmergencyContact.class);
        startActivity(intent);
    }





    public void startAppActivity(View view){
        Intent intent = new Intent(this,ListPhoneApps.class);
        startActivity(intent);
    }
 /*   public void initializeTimerTask(){
        timerTask = new TimerTask(){
            public void run() {
                printForegroundTask();
            }
        };
    }*/
   /* public String printForegroundTask() {
        String currentApp = "NULL";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager)this.getSystemService("usagestats");
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
            else{
                Log.e("adapter", "NULL applist");
            }
        } else {
            ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        Log.e("adapter", "Current App in foreground is: " + currentApp);
        SharedPreferences currentAppPref = getApplicationContext()
                .getSharedPreferences("currentApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = currentAppPref.edit();

        editor.putString("foreground",currentApp);
        editor.commit();

        return currentApp;
    }*/

   public static boolean needPermissionForBlocking(Context context){
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return  (mode != AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
       // startTimer();
    }

    /*public void startTimer() {
    timer = new Timer();
    //initializeTimerTask();
    timer.schedule(timerTask, 5000, 10000); //

}*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent(this, com.example.archana.finalproject_carma.PreferenceActivity.class);
        this.startActivity(intent);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
