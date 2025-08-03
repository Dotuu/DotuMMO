package me.dotu.MMO.Enums;

public class PermissionEnum {
    public static enum Permissions{
        ADMIN("admin"),
        STARTSEASON("startseason");

        private final String permission;

        Permissions(String permission){
            this.permission = permission;
        }

        public String getPermission(){
            return this.permission;
        }
    }
}
