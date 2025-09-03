package me.dotu.MMO.Tables;

public class ExpSource<T> {
    private int minExp;
    private int maxExp;
    private T source;

    public ExpSource(int minExp, int maxExp, T type){
        this.minExp = minExp;
        this.maxExp = maxExp;
        this.source = type;
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

}
