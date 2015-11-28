package com.example.archana.finalproject_carma;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.app.ActivityManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;


public class MonitorService extends Service
{
    private boolean initialized = false;
    private final IBinder mBinder = new LocalBinder();
    private ServiceCallback callback = null;
    private Timer timer = null;
    private final Handler mHandler = new Handler();
    private String foreground = null;
    private ArrayList<HashMap<String,Object>> processList;
    private ArrayList<String> packages;
    private Date split = null;

    public static int SERVICE_PERIOD = 5000; // TODO: customize (this is for scan every 5 seconds)

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        // String mode = intent.getStringExtra("mode");
        //    Log.d("MODE","");
        start();
        return Service.START_STICKY;
    }

    public interface ServiceCallback
    {
        void sendResults(int resultCode, Bundle b);
    }

    public class LocalBinder extends Binder
    {
        MonitorService getService()
        {
            // Return this instance of the service so clients can call public methods
            return MonitorService.this;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        initialized = true;
 /*       processList ;
        packages = ((DRIApplication)getApplication()).getPackages();*/
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        if(initialized)
        {
            return mBinder;
        }
        return null;
    }

    public void setCallback(ServiceCallback callback)
    {
        this.callback = callback;
    }



    public void start()
    {
        Log.d("","Started this MonitorService");
        if(timer == null)
        {
            timer = new Timer();
            timer.schedule(new MonitoringTimerTask(), 500, SERVICE_PERIOD);
        }
    }

    public void stop()
    {
        timer.cancel();
        timer.purge();
        timer = null;
    }

    private class MonitoringTimerTask extends TimerTask
    {
        @Override
        public void run()
        {
            String currentApp = "NULL";
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                UsageStatsManager usm = (UsageStatsManager)getApplicationContext().getSystemService("usagestats");
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
                ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
                currentApp = tasks.get(0).processName;
            }

            Log.e("FOREGROUND APP",currentApp);

            SharedPreferences sharedPreferences = getApplicationContext()
                    .getSharedPreferences("blockedApps", Context.MODE_PRIVATE);

            final PackageManager pm = getPackageManager();
            //get a list of installed apps.
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            List<String> appPackageNames = new ArrayList<String>();
            List<String> blockedAppList = new ArrayList<String>();
            for (ApplicationInfo packageInfo : packages) {
                Log.d("TAG", "Installed package :" + packageInfo.packageName);
                appPackageNames.add(packageInfo.packageName);
                Log.d("TAG", "Source dir : " + packageInfo.sourceDir);
                Log.d("TAG", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
            }

            for(String appName : appPackageNames){
                if(sharedPreferences.getBoolean(appName,false)){
                    Log.e("BlockedApps",appName);
                    blockedAppList.add(appName);
                }


            }

            /*SharedPreferences currentAppPref = getApplicationContext()
                    .getSharedPreferences("currentApp", Context.MODE_PRIVATE);*/

            //currentApp = currentAppPref.getString("foreground","dafault_no");

           /* Log.e("****************","*****");
            Log.e("-------------------",currentApp);
            Log.e("****************","*****");
*/
            /*Log.d("BLOCKED APP LIST SIZE",blockedAppList.size()+"");
            for(String s : blockedAppList)
                Log.i("BA",s);*/

         if (blockedAppList.contains(currentApp) ) {
                Log.e("testing", "made it into the block");
                Intent intent;
                intent = new Intent(getBaseContext(),BlockActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroy(){
        Log.e("In onDestroy","s");
        stop();
        super.onDestroy();
    }


}