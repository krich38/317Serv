package com.rs2.yz85.model;

import com.rs2.yz85.Server;
import com.rs2.yz85.ServerEngine;
import com.rs2.yz85.impl.model.WorldImpl;
import com.rs2.yz85.io.PlayerLoader;
import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.EntityList;
import com.rs2.yz85.util.ServerCache;

import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class World {
    private static World world = new WorldImpl();

    public static World getWorld() {
        return world;
    }

    public abstract EntityList<Player> getPlayers();

    public abstract void disconnectedPlayer(Player p);

    public abstract void connectedPlayer(Player player);

    public abstract ServerCache getCache();

    public abstract PlayerLoader getPlayerLoader();

    public abstract void setWaypoint(Entity entity, Waypoint location, Waypoint p);

    public abstract void setPlayerLoader(PlayerLoader loader);

    public abstract ScreenView getScreenView();

    public abstract void setPlayers(EntityList<Player> newStore);

    public abstract void tempBanHost(String host);

    public abstract boolean isTempHostBanned(String host);

    public abstract void setMaxPlayers(int maxPlayers);

    public abstract boolean isFull();

    public abstract ServerEngine getEngine();

    public abstract void setEngine(ServerEngine engine);

    public abstract Server getServer();

    public abstract void setServer(Server server);

    public abstract void removeTempBan(String host);

    public abstract Tile[][] getTiles();

    public abstract void removeTempMute(String host);

    public abstract boolean isPlayerMuted(String host);

    public abstract void setInterface(ServerUI serverUI);

    public abstract ServerUI getInterface();

    public abstract boolean isOpen();

    public abstract void setOpen(boolean open);

    public abstract Set<String> getTempHostBans();
}
