package com.rs2.yz85.model;

import com.rs2.yz85.impl.model.TileImpl;

import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class Tile {
    public static Tile newTile(int x, int y) {
        return new TileImpl(x, y);
    }

    public abstract void remove(Player p);

    public abstract void add(Player p);

    public abstract int getXCoord();

    public abstract int getYCoord();

    public abstract Set<Player> getPlayers();
}
