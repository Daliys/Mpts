package ru.mpts.inventory;

public class Item {
    private int id;
    private int data;
    private int durability;
    private int count;

    public Item(int id, int data, int count, int durability) {
        this.id = id;
        this.data = data;
        this.count = count;
        this.durability = durability;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getId() {
        return id;
    }

    public int getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public int getDurability() {
        return durability;
    }
}
