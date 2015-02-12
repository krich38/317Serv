package com.rs2.yz85.net.protocol;

import com.rs2.yz85.net.packet.Packet;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PacketEncoder implements ProtocolEncoder {
    public final void encode(IoSession session, Object packet, ProtocolEncoderOutput out) {
        Packet p = (Packet) packet;
        byte[] data = p.getData();
        int dataLength = p.getLength();
        ByteBuffer buffer;
        if(!p.isBare()) {
            buffer = ByteBuffer.allocate(dataLength + 3);
            int id = p.getID();
            Cryption c = (Cryption) session.getAttribute("CRYPTION_OUT");
            buffer.put((byte) (c != null ? id + c.getNextKey() : id));
            if(p.getSize() != Packet.Size.Fixed) {
                if(p.getSize() == Packet.Size.Byte) {
                    if(dataLength > 255) {
                        throw new IllegalArgumentException("Tried to send packet length " + dataLength + " in 8 bits [pid=" + p.getID() + "]");
                    }
                    buffer.put((byte) dataLength);
                } else if(p.getSize() == Packet.Size.Short) {
                    buffer.put((byte) (dataLength >> 8)).put((byte) dataLength);
                }
            }
        } else {
            buffer = ByteBuffer.allocate(dataLength);
        }
        out.write(buffer.put(data, 0, dataLength).flip());
    }

    public void dispose(IoSession session) {
    }
}
