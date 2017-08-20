package ru.mpts.map;

public class Location {
    private int x;
    private int y;
    private int worldId;

    public Location(int x, int y, int worldId) {
        this.x = x;
        this.y = y;
        this.worldId = worldId;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWorldId() {
        return worldId;
    }
}
