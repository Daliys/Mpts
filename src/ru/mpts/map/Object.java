package ru.mpts.map;

import ru.mpts.sprite.Sprite;

public class Object {
    private Location location;
    private int type;
    private int durability;
    private Sprite sprite;

    public Object(Location location, int type, Sprite sprite) {
        this.location = location;
        this.type = type;
        durability = 100;
        this.sprite = sprite;
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

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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

    public Sprite getSprite() {
        return sprite;
    }
}
