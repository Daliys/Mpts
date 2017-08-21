package ru.mpts.inventory;

public class Inventory {
    private Item[] items;
    private int itemsSize;

    public Inventory(int size) {
        items = new Item[size];
        itemsSize = size;
    }

    public Item getItem(int itemId) {
        return items[itemId];
    }

    public void addItem(Item item)
    {
        for (int i = 0; i < itemsSize; i++) {
            if(items[i].isNone()) {
                items[i] = item;
                break;
            }
        }
        itemsSize += 1;
    }

    public void addItem(int id, int data, int count, int durability) {
        addItem(new Item(id, data, count, durability));
    }

    public void removeItem(int id) {
        items[id].destroy();
        itemsSize -= 1;
    }

    public void removeItem(Item item) {
        removeItem(item.getId());
    }
}