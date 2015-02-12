package com.rs2.yz85.util;

import com.rs2.yz85.model.MobileEntity;
import com.rs2.yz85.model.Waypoint;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class WaypointQueue {
    private final LinkedList<Waypoint> waypoints = new LinkedList<Waypoint>();
    private final MobileEntity ent;

    public WaypointQueue(MobileEntity ent) {
        this.ent = ent;
    }

    public final void add(Waypoint point) {
        synchronized(waypoints) {
            waypoints.offer(point);
        }
    }

    public final Waypoint pop() {
        synchronized(waypoints) {
            return waypoints.pollFirst();
        }
    }

    public final boolean hasWaypoints() {
        return !waypoints.isEmpty();
    }

    public final void clear() {
        waypoints.clear();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[waypoints = ");
        Iterator<Waypoint> i = waypoints.iterator();
        while(i.hasNext()) {
            sb.append(i.next());
            if(i.hasNext()) {
                sb.append(" | ");
            }
        }
        return sb.append("]").toString();
    }

    public final int getNextCoord(int startCoord, int destCoord) {
        if(startCoord == destCoord) {
            return startCoord;
        } else if(startCoord > destCoord) {
            return --startCoord;
        } else if(startCoord < destCoord) {
            return ++startCoord;
        }
        return 0;
    }

    public final void update() {
        if(hasWaypoints()) {
            Waypoint newPoint = waypoints.peek();
            if(newPoint != null) {
                if(newPoint.equals(ent.getWaypoint())) {
                    waypoints.remove();
                    newPoint = waypoints.peek();
                    if(newPoint == null) {
                        return;
                    }
                }
                ent.setWaypoint(Waypoint.location(getNextCoord(ent.getWaypoint().getXCoord(), newPoint.getXCoord()), getNextCoord(ent.getWaypoint().getYCoord(), newPoint.getYCoord())));
            }
        }
    }
}

