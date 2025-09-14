package me.dotu.MMO.Tables;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SkillTable<T> {
    private String tableName;
    private ArrayList<SkillSource<?>> expItems;
    private T tableType;

    public SkillTable(String tableName, ArrayList<SkillSource<?>> expItems, T tableType) {
        this.tableName = tableName;
        this.expItems = expItems;
        this.tableType = tableType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<SkillSource<?>> getExpItems() {
        return this.expItems;
    }

    public void setExpItems(ArrayList<SkillSource<?>> expItems) {
        this.expItems = expItems;
    }

    public T getTableType() {
        return this.tableType;
    }

    public void setTableType(T tableType) {
        this.tableType = tableType;
    }

    public boolean isMaterialTable(){
        return this.tableType instanceof Material;
    }

    public boolean isEntityTable(){
        return this.tableType instanceof EntityType;
    }

    public ArrayList<Material> asMaterials(){
        if (!(this.tableType instanceof Material)){
            return new ArrayList<>();
        }

        ArrayList<Material> returnList = new ArrayList<>();

        for (SkillSource<?> item : this.expItems){
            Material material = (Material) item.getTableSource();
            returnList.add(material);
        }

        return returnList;
    }

    public ArrayList<EntityType> asEntityTypes() {
        if (!(this.tableType instanceof EntityType)){
            return new ArrayList<>();
        }

        ArrayList<EntityType> returnList = new ArrayList<>();

        for (SkillSource<?> item : this.expItems){
            EntityType entityType = (EntityType) item.getTableSource();
            returnList.add(entityType);
        }

        return returnList;
    }
}
