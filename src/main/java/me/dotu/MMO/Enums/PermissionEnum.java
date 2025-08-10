package me.dotu.MMO.Enums;

public class PermissionEnum {
    public static enum Permissions{
        ADMIN("admin"),
        PVP("pvp"),
        SPAWNER("spawner");

        private final String permission;

        Permissions(String permission){
            this.permission = permission;
        }

        public String getPermission(){
            return this.permission;
        }
    }
}
