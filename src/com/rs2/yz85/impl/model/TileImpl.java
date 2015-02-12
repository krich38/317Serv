package com.rs2.yz85.impl.model;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.Tile;
import com.rs2.yz85.model.Waypoint;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class TileImpl extends Tile {
    private Set<Player> plrs;
    private Waypoint waypoint;

    public TileImpl(int x, int y) {
        this(Waypoint.location(x, y));
    }

    public TileImpl(Waypoint waypoint) {
        this.waypoint = waypoint;
    }

    public final void add(Player p) {
        if(plrs == null) {
            plrs = new HashSet<Player>();
        }
        plrs.add(p);
    }

    public final void remove(Player p) {
        plrs.remove(p);
    }

    public final int getXCoord() {
        return waypoint.getXCoord();
    }

    public final int getYCoord() {
        return waypoint.getYCoord();
    }

    public final Set<Player> getPlayers() {
        return plrs;
    }
}
