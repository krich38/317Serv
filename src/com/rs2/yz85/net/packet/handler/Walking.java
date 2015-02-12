package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.Waypoint;
import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Walking implements PacketHandler {
    public final void handlePacket(Packet p) {
        Player player = (Player) p.getSession().getAttachment();
        int length = p.getLength();
        if(p.getID() == 248) {
            length -= 14;
        }
        player.getWaypointQueue().clear();
        int stepCount = (length - 5) / 2, firstStepX = p.readUnsignedShortA(), wayPointOffsets[][] = new int[stepCount][2];
        for(int i = 0; i < stepCount; i++) {
            wayPointOffsets[i][0] = p.readSignedByte() + firstStepX;
            wayPointOffsets[i][1] = p.readSignedByte();
        }
        int firstStepY = p.readUnsignedShortC();
        p.skip(1);
        player.getWaypointQueue().add(Waypoint.location(firstStepX, firstStepY));
        for(int i = 0; i < stepCount; i++) {
            wayPointOffsets[i][1] += firstStepY;
            player.getWaypointQueue().add(Waypoint.location(wayPointOffsets[i][0], wayPointOffsets[i][1]));
        }
    }

    public final int[] getBindings() {
        return new int[] { MAP_WALK, WALK_COMMAND, REGULAR_WALK };
    }
}
