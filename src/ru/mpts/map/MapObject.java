package ru.mpts.map;

public class MapObject {
    private Location location;
    private int type;

    public MapObject(Location location, int type) {
        this.location = location;
        this.type = type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }
}
