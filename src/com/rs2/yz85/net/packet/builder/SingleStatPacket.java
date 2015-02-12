package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.model.Player;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class SingleStatPacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] argsNeeded) {
        int stat = Integer.valueOf(argsNeeded[0].toString());
        Player p = (Player) session.getAttachment();
        session.write(new PacketConstructor().setId(134).addByte((byte) stat).addIntBlock(p.getExps()[stat]).addByte((byte) p.getCurStat(stat)).toPacket());
    }

    public final String getString() {
        return "single_stat";
    }
}
