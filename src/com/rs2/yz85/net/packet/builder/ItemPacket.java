package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.model.Item;
import com.rs2.yz85.net.packet.Packet;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ItemPacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] neededArgs) {
        Item item = (Item) neededArgs[0];
        PacketConstructor pb = new PacketConstructor().setId(34).setSize(Packet.Size.Short).addShort((Integer) neededArgs[1]).addByte((byte) item.getIndex()).addShort(item.getId() + 1);
        session.write((item.getAmount() > 254 ? pb.addByte((byte) 255).addIntReversed(item.getAmount()) : pb.addByte((byte) item.getAmount())).toPacket());
    }

    public final String getString() {
        return "item_string";
    }
}
