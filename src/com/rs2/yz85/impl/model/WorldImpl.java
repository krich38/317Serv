package com.rs2.yz85.impl.model;

import com.rs2.yz85.Server;
import com.rs2.yz85.ServerEngine;
import com.rs2.yz85.impl.io.PlayerLoaderImpl;
import com.rs2.yz85.io.PlayerLoader;
import com.rs2.yz85.model.*;
import com.rs2.yz85.net.packet.builder.PacketBuilder;
import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.EntityList;
import com.rs2.yz85.util.Logger;
import com.rs2.yz85.util.ServerCache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class WorldImpl extends World {
    private EntityList<Player> players;
    private ServerCache cache;
    private PlayerLoader loader;
    private ScreenView view;
    private Set<String> tempHostBans;
    private Set<String> tempMuteHosts;
    private int maxPlayers = 10;
    private ServerEngine engine;
    private Server server;
    private Map<Integer, Tile> tiles;
    private ServerUI serverUI;
    private boolean open = true;

    public WorldImpl() {
        this.players = new EntityList<Player>();
        this.cache = new ServerCache();
        this.loader = new PlayerLoaderImpl();
        this.view = new ScreenView();
        this.tempHostBans = new HashSet<String>();
        this.tempMuteHosts = new HashSet<String>();
        this.tiles = new HashMap<Integer, Tile>(2500);
    }

    public final EntityList<Player> getPlayers() {
        return players;
    }

    public final void disconnectedPlayer(Player p) {
        players.remove(p);
        PacketBuilder.builders.get("logout_packet").writePacket(p.getSession(), null);
        Logger.log(p.getUsername() + " disconnected.");
        serverUI.updateList(p.getUsername());
    }

    public final void connectedPlayer(Player player) {
        players.add(player);
        player.setLoggedIn(true);
        serverUI.updateList(player.getUsername());
    }

    public final ServerCache getCache() {
        return cache;
    }

    public final PlayerLoader getPlayerLoader() {
        return loader;
    }

    public final void setWaypoint(Entity entity, Waypoint location, Waypoint p) {
        if(location != null) {
            getTile(location.getXCoord(), location.getYCoord()).remove((Player) entity);
        }
        if(p != null) {
            getTile(p.getXCoord(), p.getYCoord()).add((Player) entity);
        }
    }

    public final void setPlayerLoader(PlayerLoader loader) {
        this.loader = loader;
    }

    public final ScreenView getScreenView() {
        return view;
    }

    public final void setPlayers(EntityList<Player> players) {
        this.players.clear();
        this.players.addAll(players);
    }

    public final void tempBanHost(String host) {
        this.tempHostBans.add(host);
    }

    public final boolean isTempHostBanned(String host) {
        return tempHostBans.contains(host);
    }

    public final void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public final boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public final ServerEngine getEngine() {
        return engine;
    }

    public final void setEngine(ServerEngine engine) {
        this.engine = engine;
    }

    public final Server getServer() {
        return server;
    }

    public final void setServer(Server server) {
        this.server = server;
    }

    public final void removeTempMute(String host) {
        this.tempMuteHosts.remove(host);
    }

    public final boolean isPlayerMuted(String host) {
        return tempMuteHosts.contains(host);
    }

    public final void setInterface(ServerUI serverUI) {
        this.serverUI = serverUI;
    }

    public final ServerUI getInterface() {
        return serverUI;
    }

    public final boolean isOpen() {
        return open;
    }

    public final void setOpen(boolean open) {
        this.open = open;
    }

    public final Set<String> getTempHostBans() {
        return tempHostBans;
    }

    public final void removeTempBan(String host) {
        this.tempHostBans.remove(host);
    }

    public final Tile[][] getTiles() {
        Tile[][] tmp = new Tile[2000][2000];
        for(Tile i : tiles.values()) {
            tmp[i.getXCoord() - 2000][i.getYCoord() - 2000] = i;
        }
        return tmp;
    }

    private Tile getTile(int x, int y) {
        int id = x + y - 2000;
        Tile t = tiles.get(id);
        if(t == null) {
            t = new TileImpl(x, y);
            tiles.put(id, t);
        }
        return t;
    }
}
