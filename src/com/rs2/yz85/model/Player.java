package com.rs2.yz85.model;

import com.rs2.yz85.net.packet.builder.PacketBuilder;
import com.rs2.yz85.util.Data;
import com.rs2.yz85.util.EntityTracker;
import com.rs2.yz85.util.WaypointQueue;
import org.apache.mina.common.IoSession;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Player extends MobileEntity {
    private boolean loggedIn, mapChanged = true, running;
    private String username, password, host;
    private PlayerAppearance appr;
    private int exp[], stats[], knownRegionY, knownRegionX, response;
    private ChatMessage lastChatMessage;
    private WaypointQueue waypointQueue;
    private long userHash;
    private PlayerRights rights;
    private Map<Player, Integer> knownAppearanceIds = new HashMap<Player, Integer>();
    private IoSession session;
    private EntityTracker<Player> watchedPlayers = new EntityTracker<Player>();
    private ScreenView view = World.getWorld().getScreenView();
    private PlayerGender gender;
    private Inventory invent;
    private Equipped equipment;
    private long[] friends;

    public final String getUsername() {
        return username;
    }

    public final void reset() {
        getAppearance().setChanged(false);
        setMapAreaChanged(false);
        setSprite(-1);
        setLastChatMessage(null);
        getWatchedPlayers().update();
    }

    public final void setRunning(boolean running) {
        this.running = running;
    }

    public final String getPassword() {
        return this.password;
    }

    public Player(IoSession session) {
        this.session = session;
    }

    public final void updateRegion(int regionX, int regionY) {
        this.knownRegionX = regionX;
        this.knownRegionY = regionY;
    }

    public final void setStats(int[] stats) {
        this.stats = stats;
    }

    public final void setExps(int[] exps) {
        this.exp = exps;
    }

    public final void setAppearance(PlayerAppearance appr) {
        this.appr = appr;
    }

    public final PlayerAppearance getAppearance() {
        return appr;
    }

    public final long getUserHash() {
        return userHash;
    }

    private void informOfPlayer(Player p) {
        if((!watchedPlayers.contains(p) || watchedPlayers.isRemoving(p)) && !watchedPlayers.isAdding(p) && withinRange(p, 16) && p.isLoggedIn()) {
            watchedPlayers.add(p);
            knownAppearanceIds.put(p, -1);
        }
    }

    public final void update() {
        if(getAppearance().hasChanged()) {
            for(Player p : watchedPlayers.getKnownEntities()) {
                p.knownAppearanceIds.put(this, getAppearance().getId());
            }
            knownAppearanceIds.put(this, getAppearance().getId());
            getAppearance().incrementId();
        }
        for(Player p : watchedPlayers.getKnownEntities()) {
            if(!withinRange(p, 16) || !p.isLoggedIn()) {
                watchedPlayers.remove(p);
            }
        }
        for(Player p : view.getPlayersInView(this)) {
            if(!p.equals(this)) {
                informOfPlayer(p);
                p.informOfPlayer(this);
            }
        }
    }

    public final boolean withinRange(Entity p, int rad) {
        if(p != null) {
            Waypoint location = p.getWaypoint();
            return Math.abs(location.getXCoord() - p.getX()) <= rad && Math.abs(location.getYCoord() - p.getY()) <= rad;
        } else {
            return false;
        }
    }

    public final int getKnownAppearanceId(Player target) {
        return knownAppearanceIds.get(target);
    }

    public final void setKnownAppearanceId(Player target, int id) {
        knownAppearanceIds.put(target, id);
    }

    public final byte getResponseCode() {
        return (byte) response;
    }

    public final void setLastChatMessage(ChatMessage msg) {
        this.lastChatMessage = msg;
    }

    public final int[] getExps() {
        return exp;
    }

    public final int getCurStat(int stat) {
        return stats[stat];
    }

    public final WaypointQueue getWaypointQueue() {
        return waypointQueue;
    }

    public final boolean mapAreaChanged() {
        return mapChanged;
    }

    public final EntityTracker<Player> getWatchedPlayers() {
        return watchedPlayers;
    }

    public final void setMapAreaChanged(boolean mapChanged) {
        this.mapChanged = mapChanged;
    }

    public final boolean equals(Object o) {
        return o instanceof Player && ((Player) o).getUserHash() == this.getUserHash();
    }

    public final boolean isRunning() {
        return running;
    }

    public final int getLastSprite() {
        return lastSprite;
    }

    public final ChatMessage getLastChatMessage() {
        return lastChatMessage;
    }

    public final void setCredentials(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.waypointQueue = new WaypointQueue(this);
        this.userHash = Data.playerNameToLong(username.toLowerCase());
        this.invent = new Inventory();
        this.equipment = new Equipped();
        this.response = World.getWorld().getPlayerLoader().loadPlayer(this);
        this.friends = new long[50];
        this.host = ((InetSocketAddress) getSession().getRemoteAddress()).getHostName();
    }

    public final boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public final void setWaypoint(Waypoint p) {
        if(location != null) {
            int[] knownMapArea = getKnownRegion(), newMapArea = { p.getRegionX(), p.getRegionY() }, differences = { Math.abs(newMapArea[0] - knownMapArea[0]), Math.abs(newMapArea[1] - knownMapArea[1]) };
            if(differences[0] >= 3 || differences[1] >= 3) {
                setMapAreaChanged(true);
            }
            super.setWaypoint(p, false);
        } else {
            setMapAreaChanged(true);
        }
    }

    public final IoSession getSession() {
        return session;
    }

    public final void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public final int[] getKnownRegion() {
        return new int[] { knownRegionX, knownRegionY };
    }

    public final PlayerRights getPlayerRights() {
        return rights;
    }

    public final void setPlayerRights(PlayerRights rights) {
        this.rights = rights;
    }

    public final PlayerGender getGender() {
        return gender;
    }

    public final void setGender(PlayerGender gender) {
        this.gender = gender;
    }

    public final Inventory getInventory() {
        return invent;
    }

    public final Equipped getEquipment() {
        return equipment;
    }

    public final void sendMessage(String msg) {
        PacketBuilder.builders.get("standard_string").writePacket(getSession(), new Object[] { msg });
    }

    public long[] getFriendsList() {
        return friends;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getHost() {
        return host;
    }
}
