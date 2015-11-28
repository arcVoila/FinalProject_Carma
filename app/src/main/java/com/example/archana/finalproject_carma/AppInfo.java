package com.example.archana.finalproject_carma;

/**
 * Created by Archana on 11/18/2015.
 */
public class AppInfo {
    private String appName;
    private boolean checked = false;

    public AppInfo(String appName, boolean checked){
        this.appName = appName;
        this.checked = checked;
    }

    public void toggleChecked()
    {
        checked = !checked;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }




}
