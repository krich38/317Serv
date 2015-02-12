package com.rs2.yz85.impl.command;

import com.rs2.yz85.model.Commands;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.PlayerRights;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ReloadCommands extends Commands {
    public final void handleCommand(String command, Player p) {
        if(p.getPlayerRights().getRight() >= PlayerRights.valueOf(PlayerRights.ADMIN)) {
            Commands.loadCommands();
            p.sendMessage("Loaded commands.");
        }
    }

    public final String[] getInvocations() {
        return new String[] { "reloadcommands" };
    }
}
