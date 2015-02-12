package com.rs2.yz85.impl.command;

import com.rs2.yz85.impl.event.HostUnban;
import com.rs2.yz85.model.Commands;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.PlayerRights;
import com.rs2.yz85.model.World;
import com.rs2.yz85.util.Data;

import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class TempBan extends Commands {
    private final World world = World.getWorld();

    public final void handleCommand(String command, Player p) {
        if(p.getPlayerRights().getRight() >= PlayerRights.valueOf(PlayerRights.ADMIN)) {
            Matcher m = Pattern.compile("(\\w*)(\\s)(\\w*)\\s(\\d)h").matcher(command);
            if(m.matches()) {
                String name = m.group(3);
                long hash = Data.playerNameToLong(name);
                for(Player x : world.getPlayers()) {
                    System.out.println(x.getUserHash());
                    if(x.getUserHash() == hash) {
                        String host = ((InetSocketAddress) x.getSession().getRemoteAddress()).getHostName();
                        world.tempBanHost(host);
                        world.getEngine().getTimer().schedule(new HostUnban(host), Integer.parseInt(m.group(4)) * 60 * 60 * 1000);
                        x.setLoggedIn(false);
                        p.sendMessage("Player " + x.getUsername() + " banned.");
                        return;
                    }
                }
            } else {
                p.sendMessage("Incorrect syntax.");
            }
        }
    }

    public final String[] getInvocations() {
        return new String[] { "tbanplr" };
    }
}
