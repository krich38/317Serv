package com.rs2.yz85.impl.model;

import com.rs2.yz85.model.Waypoint;

import java.util.Arrays;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class WaypointImpl extends Waypoint {
    private int height, absolute[], mapRegion[], local[];

    public WaypointImpl(int x, int y) {
        this.absolute = new int[] { x, y };
        this.mapRegion = new int[] { (absolute[0] >> 3) - 6, (absolute[1] >> 3) - 6 };
        this.local = new int[] { absolute[0] - 8 * mapRegion[0], absolute[1] - 8 * mapRegion[1] };
        this.height = 0;
    }

    public final int getXCoord() {
        return absolute[0];
    }

    public final int getYCoord() {
        return absolute[1];
    }

    public final int getRegionX() {
        return mapRegion[0];
    }

    public final int getRegionY() {
        return mapRegion[1];
    }

    public final int getLocalX() {
        return local[0];
    }

    public final int getLocalY() {
        return local[1];
    }

    public final int getHeight() {
        return height;
    }

    public final boolean equals(Object o) {
        return o instanceof Waypoint && Arrays.equals(((Waypoint) o).getAbsolute(), this.absolute);
    }

    public final String toString() {
        return "[" + absolute[0] + "," + absolute[1] + "]";
    }

    public final int[] getAbsolute() {
        return absolute;
    }
}
