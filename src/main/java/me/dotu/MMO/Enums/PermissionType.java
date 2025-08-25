package me.dotu.MMO.Enums;

public enum PermissionType {
    ADMIN("admin"),
    PVP("pvp"),
    SPAWNER("spawner");

    private final String permission;

    PermissionType(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
