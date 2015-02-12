package com.rs2.yz85.impl.command;

import com.rs2.yz85.model.Commands;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.util.Logger;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ReloadPackets extends Commands {
    public final void handleCommand(String command, Player p) {
        try {
            World.getWorld().getServer().loadPackets();
            p.sendMessage("Loaded packets.");
        } catch(Exception e) {
            Logger.err(e);
            p.sendMessage("Exception thrown when loading packets:");
        }
    }

    public final String[] getInvocations() {
        return new String[] { "reloadpackets" };
    }
}
