package com.rs2.yz85.impl.command;

import com.rs2.yz85.model.Commands;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ReloadEvents extends Commands {
    public final void handleCommand(String command, Player p) {
        World.getWorld().getEngine().initiateEvents();
        p.sendMessage("Loaded events.");
    }

    public final String[] getInvocations() {
        return new String[] { "reloadevents" };
    }
}
