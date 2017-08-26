package ru.mpts.map;

import ru.mpts.sprite.Sprite;

public class Object {
    private Location location;
    private int type;
    private int durability;
    private Sprite sprite;

    public Object(Location location, int type) {
        this.location = location;
        this.type = type;
        durability = 100;
        String path;

        switch (type) {
            case MapObjectType.AIR: {
                path = "air.png";
                break;
            }
            case MapObjectType.GRASS: {
                path = "grass(1).png";
                break;
            }
            case MapObjectType.HERO: {
                path = "hero(3).png";
                break;
            }
            case MapObjectType.IRON_ORE: {
                path = "ironOreBlockCenter(1).png";
                break;
            }
            case MapObjectType.STONE: {
                path = "stoneBlockCenter(1).png";
                break;
            }
            default: {
                path = "default.png";
                break;
            }
        }
        sprite = new Sprite(path, location);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
