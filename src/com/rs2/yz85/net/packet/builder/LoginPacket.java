package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.model.Player;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class LoginPacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] argsNeeded) {
        session.write(new PacketConstructor().setId(249).addByteAlternateOrder((byte) 1).addShortAlternateOrder(((Player) session.getAttachment()).getIndex()).toPacket());
    }

    public final String getString() {
        return "login_packet";
    }
}
