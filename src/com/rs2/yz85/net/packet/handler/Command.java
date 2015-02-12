package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Commands;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 * <p/>
 * TODO: Write a command design which doesn't suck. This is only temp.
 */
public final class Command implements PacketHandler {
    public final void handlePacket(Packet p) {
        String command = p.readRSString();
        String cmdIndex = command.split(" ")[0].toLowerCase();
        Player plr = (Player) p.getSession().getAttachment();
        if(Commands.hasCommand(cmdIndex)) {
            Commands.getCommand(cmdIndex).handleCommand(command, plr);
        } else {
            plr.sendMessage("Unknown command: " + command);
        }
    }

    public final int[] getBindings() {
        return new int[] { PLAYER_COMMAND };
    }
}
