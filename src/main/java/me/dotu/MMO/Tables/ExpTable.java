package me.dotu.MMO.Tables;

public class ExpTable<T> {
    private String tableName;
    private int minExp;
    private int maxExp;
    private T sourceType;

    public ExpTable(String tableName, int minExp, int maxExp, T sourceType) {
        this.minExp = minExp;
        this.maxExp = maxExp;
        this.sourceType = sourceType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getMinExp() {
        return this.minExp;
    }

    public void setMinExp(int minExp) {
        this.minExp = minExp;
    }

    public int getMaxExp() {
        return this.maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public T getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(T sourceType) {
        this.sourceType = sourceType;
    }
}
