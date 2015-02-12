package com.rs2.yz85.net.packet.builder;

import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class LogoutPacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] argsNeeded) {
        session.write(new PacketConstructor().setId(109).toPacket());
    }

    public final String getString() {
        return "logout_packet";
    }
}
