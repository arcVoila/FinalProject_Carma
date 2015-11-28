package com.example.archana.POJO;

/**
 * Created by Archana on 11/19/2015.
 */
public class BlockedAppsPOJO {
    private String blockedAppName;
    private String blocked;

    public BlockedAppsPOJO(String blockedAppName, String blocked){
        this.blockedAppName = blockedAppName;
        this.blocked = blocked;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getBlockedAppName() {
        return blockedAppName;
    }

    public void setBlockedAppName(String blockedAppName) {
        this.blockedAppName = blockedAppName;
    }

}
