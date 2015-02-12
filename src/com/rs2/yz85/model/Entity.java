package com.rs2.yz85.model;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public class Entity {
    public Waypoint location;
    public int id;
    public transient int index;

    public final int getId() {
        return id;
    }

    public final void setId(int newId) {
        id = newId;
    }

    public final int getIndex() {
        return index;
    }

    public final void setIndex(int newIndex) {
        index = newIndex;
    }

    public void setWaypoint(Waypoint p) {
        World.getWorld().setWaypoint(this, location, p);
        location = p;
    }

    public final Waypoint getWaypoint() {
        return location;
    }

    public final int getX() {
        return location.getXCoord();
    }

    public final int getY() {
        return location.getYCoord();
    }
}