package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.net.packet.Packet;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class RSStringPacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] argsNeeded) {
        session.write(new PacketConstructor().setId(253).setSize(Packet.Size.Byte).addString((String) argsNeeded[0]).toPacket());
    }

    public final String getString() {
        return "standard_string";
    }
}
