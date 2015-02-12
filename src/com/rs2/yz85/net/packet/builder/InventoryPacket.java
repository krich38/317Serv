package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.model.Item;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.net.packet.Packet;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class InventoryPacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] neededArgs) {
        PacketConstructor pb = new PacketConstructor(226).setId(34).setSize(Packet.Size.Short).addShort(3214);
        for(Item item : ((Player) session.getAttachment()).getInventory().getItems()) {
            if(item != null) {
                pb.addByte((byte) item.getIndex()).addShort(item.getId() + 1);
                if(item.getAmount() > 254) {
                    pb.addByte((byte) 255).addIntReversed(item.getAmount());
                } else {
                    pb.addByte((byte) item.getAmount());
                }
            }
        }
        session.write(pb.toPacket());
    }

    public final String getString() {
        return "inventory_packet";
    }
}
