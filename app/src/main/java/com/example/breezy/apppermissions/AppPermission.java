package com.example.breezy.apppermissions;

/**
 * Created by kristianhfischer on 5/5/15.
 */
public class AppPermission {
    private final String permissionName;
    private final String permissionDescription;

    public AppPermission(String permissionName, String permissionDescription) {
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }
}
