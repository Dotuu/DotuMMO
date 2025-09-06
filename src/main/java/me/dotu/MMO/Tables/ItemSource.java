package me.dotu.MMO.Tables;

public class ItemSource<T> {
    private int minExp;
    private int maxExp;
    private T source;
    private int requiredLevel;

    public ItemSource(int minExp, int maxExp, T type, int requiredLevel){
        this.minExp = minExp;
        this.maxExp = maxExp;
        this.source = type;
        this.requiredLevel = requiredLevel;
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

    public T getTableSource() {
        return this.source;
    }

    public void setTableSource(T type) {
        this.source = type;
    }

    public int getRequiredLevel() {
        return this.requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

}
