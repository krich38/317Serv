package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class IgnorePlayer implements PacketHandler {
    public void handlePacket(Packet p) {
        Player plr = (Player) p.getSession().getAttachment();
        switch(p.getID()) {
            case ADD_IGNORE:
                break;
            case REMOVE_IGNORE:
                break;
        }
    }

    public int[] getBindings() {
        return new int[] { ADD_IGNORE, REMOVE_IGNORE };
    }
}
