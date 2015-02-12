package com.rs2.yz85.util;

import com.rs2.yz85.model.Waypoint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ServerCache {
    private final Map<Long, Object> cache = new HashMap<Long, Object>();

    public final boolean hasCached(long l) {
        return cache.containsKey(l);
    }

    public final Waypoint getWaypoint(long key) {
        return (Waypoint) cache.get(key);
    }

    public final void addWaypoint(Waypoint point) {
        cache.put((long) point.getXCoord() << 16 | point.getYCoord(), point);
    }
}
