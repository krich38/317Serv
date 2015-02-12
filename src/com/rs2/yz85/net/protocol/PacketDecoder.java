package com.rs2.yz85.net.protocol;

import com.rs2.yz85.impl.net.PacketImpl;
import com.rs2.yz85.net.packet.Packet;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PacketDecoder extends CumulativeProtocolDecoder {
    protected final boolean doDecode(IoSession session, ByteBuffer in, ProtocolDecoderOutput out) throws Exception {
        if(in.remaining() >= 1) {
            int id = in.getUnsigned();
            Cryption c = (Cryption) session.getAttribute("CRYPTION_IN");
            if(c != null) {
                id = (id & 0xff) - c.getNextKey() & 0xff;
            }
            int len = Packet.packetLengths[id];
            if(len == -1) {
                if(in.remaining() >= 1) {
                    len = in.get() & 0xff;
                }
            }
            if(len >= 0) {
                if(in.remaining() >= len) {
                    byte[] payload = new byte[len];
                    in.get(payload);
                    out.write(new PacketImpl(session, id, payload));
                    return true;
                }
                in.rewind();
                return false;
            }
            throw new IllegalArgumentException("Negative packet length.");
        }
        return false;
    }
}
