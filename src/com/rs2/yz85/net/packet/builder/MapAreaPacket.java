package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.model.Player;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class MapAreaPacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] argsNeeded) {
        Player p = (Player) session.getAttachment();
        session.write(new PacketConstructor().setId(73).addShortAlternateOrder(p.getWaypoint().getRegionX() + 6).addShort(p.getWaypoint().getRegionY() + 6).toPacket());
    }

    public final String getString() {
        return "map_area";
    }
}
