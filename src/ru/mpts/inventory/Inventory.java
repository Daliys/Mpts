package ru.mpts.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<Item>();

    public Inventory() {

    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addItem(int id, int data, int count, int durability) {
        items.add(new Item(id, data, count, durability));
    }

    public void removeItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
            }
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
