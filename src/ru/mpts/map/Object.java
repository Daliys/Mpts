package ru.mpts.map;

public class Object {
    private Location location;
    private int type;
    private int durability;

    public Object(Location location, int type) {
        this.location = location;
        this.type = type;
        durability = 100;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Location getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }

    public int getDurability() {
        return durability;
    }
}
