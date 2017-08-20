package ru.mpts.units;

import ru.mpts.map.Location;

public class Action {
    private Location location;
    private boolean isTaken;
    private int action;

    public Action(Location location, int action) {
        this.location = location;
        this.action = action;
        isTaken = false;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public Location getLocation() {
        return location;
    }

    public int getAction() {
        return action;
    }
}
