package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.util.Data;
import com.rs2.yz85.util.Logger;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Friends implements PacketHandler {
    public final void handlePacket(Packet p) {
        Player plr = (Player) p.getSession().getAttachment();
        long friend = p.readLongC();
        switch(p.getID()) {
            case REMOVE_FRIEND:
                for(long i : plr.getFriendsList()) {
                    if(i == friend) {
                    }
                }
                System.out.println(Data.longToPlayerName(friend));
                plr.sendMessage("That player is currently not on your friends list!");
                break;
            case ADD_FRIEND:
                for(long i : plr.getFriendsList()) {
                }
                break;
            case ADD_IGNORE:
        }
        Logger.log(friend);
        Logger.log(plr.getUserHash());
        Logger.log(plr.getUserHash());
    }

    public final int[] getBindings() {
        return new int[] { REMOVE_FRIEND, ADD_FRIEND };
    }
}
