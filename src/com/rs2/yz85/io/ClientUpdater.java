package com.rs2.yz85.io;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.net.packet.builder.PacketBuilder;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ClientUpdater {
    private final World world = World.getWorld();

    public final void fullUpdate() {
        for(Player p : world.getPlayers()) {
            if(p.isLoggedIn()) {
                if(!p.mapAreaChanged()) {
                    p.getWaypointQueue().update();
                    if(p.isRunning()) {
                        p.getWaypointQueue().update();
                    }
                }
                p.update();
            } else {
                p.getSession().close();
            }
        }
        for(Player p : world.getPlayers()) {
            if(p.mapAreaChanged()) {
                PacketBuilder.builders.get("map_area").writePacket(p.getSession(), null);
                p.updateRegion(p.getWaypoint().getRegionX(), p.getWaypoint().getRegionY());
            }
            PacketBuilder.builders.get("player_update").writePacket(p.getSession(), null);
        }
        for(Player p : world.getPlayers()) {
            updateTimeout(p);
            p.reset();
        }
    }

    private void updateTimeout(Player p) {
        long cur = System.currentTimeMillis();
        if(cur - p.getLastActivity() >= 600000 && !p.beenActiveChecked()) {
            p.sendMessage("Warning! You have been inactive for 10 minutes, please move within the next minute or you will be booted.");
            p.hasBeenActiveCheck();
        } else if(p.beenActiveChecked()) {
            if(cur - p.getLastActivity() >= 60000 && p.isLoggedIn()) {
                p.setLoggedIn(false);
            }
        }
    }
}
