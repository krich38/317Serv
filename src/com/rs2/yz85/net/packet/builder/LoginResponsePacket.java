package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.model.Player;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class LoginResponsePacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] argsNeeded) {
        Player p = (Player) session.getAttachment();
        session.write(new PacketConstructor().setBare(true).addByte(p.getResponseCode()).addByte((byte) p.getPlayerRights().getRight()).addByte((byte) 0).toPacket());
    }

    public final String getString() {
        return "login_response";
    }
}
