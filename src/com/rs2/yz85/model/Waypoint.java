package com.rs2.yz85.model;

import com.rs2.yz85.impl.model.WaypointImpl;
import com.rs2.yz85.util.ServerCache;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class Waypoint {
    private static ServerCache cache = World.getWorld().getCache();
    public static final int NONE = -1, NORTH = 0, NORTHEAST = 2, EAST = 4, SOUTHEAST = 6, SOUTH = 8, SOUTHWEST = 10, WEST = 12, NORTHWEST = 14;

    public static Waypoint location(int x, int y) {
        long key = x << 16 | y;
        Waypoint point = cache.getWaypoint(key);
        if(point == null) {
            point = new WaypointImpl(x, y);
            cache.addWaypoint(point);
        }
        return point;
    }

    public abstract int getXCoord();

    public abstract int getYCoord();

    public abstract int[] getAbsolute();

    public abstract int getHeight();

    public abstract int getLocalY();

    public abstract int getLocalX();

    public abstract int getRegionX();

    public abstract int getRegionY();

    public abstract boolean equals(Object o);

    public abstract String toString();
}
