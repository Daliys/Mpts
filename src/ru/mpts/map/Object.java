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

        switch (type) {
            case MapObjectType.AIR: {
                sprite = new Sprite("air.png");
                break;
            }
            case MapObjectType.GRASS: {
                sprite = new Sprite("grass(1).png");
                break;
            }
            case MapObjectType.HERO: {
                sprite = new Sprite("hero(1).png");
                break;
            }
            case MapObjectType.IRON_ORE: {
                sprite = new Sprite("ironOreBlockCenter(1).png");
                break;
            }
            case MapObjectType.STONE: {
                sprite = new Sprite("stoneBlockCenter(1).png");
                break;
            }
            default: {
                sprite = new Sprite("default.png");
                break;
            }
        }
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
