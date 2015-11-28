package com.example.archana.finalproject_carma;

/**
 * Created by Archana on 11/23/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.archana.DAO.BlockedAppsDAO;
import com.example.archana.POJO.BlockedAppsPOJO;
import com.example.archana.dataAccess.BlockedAppsHelper;

public class ApplicationAdapter extends ArrayAdapter<ApplicationInfo> implements AdapterView.OnItemClickListener {
    private List<ApplicationInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;
    boolean[] checkBoxState;
    ViewHolder viewHolder;
    private BlockedAppsDAO blockedAppsDAO;
    Set<String> blockedApps = new HashSet<String>();
    HashMap<String, Boolean> appStatus = new HashMap<String, Boolean>();
    BlockedAppsHelper blockedAppsHelper;
    int count = 0;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    public ApplicationAdapter(Context context, int textViewResourceId, List<ApplicationInfo> appsList) {
        super(context, textViewResourceId, appsList);
        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
        blockedAppsDAO = new BlockedAppsDAO(context);
        blockedAppsDAO.open();
        //checkBoxState=readFromDB();
        loadSavedPreferences();
    }

/*
    public boolean[] readFromDB() {
        checkBoxState = new boolean[appsList.size()];
        List<BlockedAppsPOJO> values = blockedAppsDAO.getAllBAs();
        List<String> setToTrue = new ArrayList<String>();
        Log.d("Size of Blocked List ", values.size() + "");
        for (int i = 0; i < appsList.size(); i++) {
            for (BlockedAppsPOJO pojo : values) {

                if ((pojo.getBlockedAppName().toLowerCase()).contains(String.valueOf(appsList.get(i).loadLabel(packageManager)).toLowerCase()))
                    checkBoxState[i] = true;
            }

        }
        return checkBoxState;
    }*/

    private class ViewHolder {
        ImageView photo;
        TextView name, pack_name;
        CheckBox checkBox;
    }

    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


 /*   public void updateBlockedAppList() {

        // List<BlockedAppsPOJO> blockedAppsList = new ArrayList<BlockedAppsPOJO>();

        Log.d("*******", "start");
        for (String s : appStatus.keySet()) {
            Log.d("BlockedApps", s + " " + appStatus.get(s));
            // blockedAppsList.add(new BlockedAppsPOJO(s,"true"));
        }
        Log.d("*******", "end");
        //  blockedAppsDAO.saveBAs(blockedAppsList);

    }*/

    private void loadSavedPreferences() {
        checkBoxState = new boolean[appsList.size()];
        SharedPreferences sharedPreferences = context
                .getSharedPreferences("blockedApps",Context.MODE_PRIVATE);
       int count = 0;
       for(ApplicationInfo appInfo : appsList){

           boolean name = sharedPreferences.getBoolean(String.valueOf(appInfo.packageName),false);
          // Log.e("Check Bool Value ", name+"");
           if(name)
               checkBoxState[count] = true;
           count++;
       }




    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       sharedPrefs = context.getSharedPreferences("blockedApps", Context.MODE_PRIVATE);
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.snippet_list_row, null);
            viewHolder = new ViewHolder();

            viewHolder.photo = (ImageView) view.findViewById(R.id.app_icon);
            viewHolder.name = (TextView) view.findViewById(R.id.app_name);
            // viewHolder.pack_name=(TextView) view.findViewById(R.id.app_paackage);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.doneCheckBox);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        editor = sharedPrefs.edit();
        ApplicationInfo applicationInfo = appsList.get(position);
        if (null != applicationInfo) {

            viewHolder.photo.setImageDrawable(applicationInfo.loadIcon(packageManager));
            viewHolder.name.setText(applicationInfo.loadLabel(packageManager));
            // viewHolder.pack_name.setText(applicationInfo.packageName);
            // adding to update in DB
           // appStatus.put(String.valueOf(applicationInfo.loadLabel(packageManager)), checkBoxState[position]);
            //updateBlockedAppList();

            viewHolder.checkBox.setChecked(checkBoxState[position]);
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        checkBoxState[position] = true;
                    } else {
                        checkBoxState[position] = false;
                    }
                    editor.putBoolean(String.valueOf(appsList.get(position).packageName),checkBoxState[position]);
                    editor.commit();
                }
            }
            );


         /*   TextView appName = (TextView) view.findViewById(R.id.app_name);
            TextView packageName = (TextView) view.findViewById(R.id.app_paackage);
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);
            CheckBox doneCheckBox = (CheckBox)view.findViewById(R.id.doneCheckBox);*/
            /*doneCheckBox.setChecked(false);
            appName.setText(applicationInfo.loadLabel(packageManager));
            packageName.setText(applicationInfo.packageName);
            iconview.setImageDrawable(applicationInfo.loadIcon(packageManager));*/
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("TADAAAAAAAAAAAAAAAA","finally");
    }
}
