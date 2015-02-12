package com.rs2.yz85.impl.net;

import com.rs2.yz85.net.packet.Packet;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PacketImpl implements Packet {
    private boolean isBare;
    private byte[] packetData;
    private final IoSession session;
    private Packet.Size size = Packet.Size.Fixed;
    private int caret = 0, packetID;

    public PacketImpl(IoSession session, byte[] data, int id, Packet.Size size, boolean bare) {
        this.session = session;
        this.packetData = data;
        this.packetID = id;
        this.size = size;
        this.isBare = bare;
    }

    public PacketImpl(IoSession session, int pID, byte[] pData, boolean bare) {
        this(session, pData, pID, Packet.Size.Fixed, bare);
    }

    public PacketImpl(IoSession session, int pID, byte[] pData) {
        this(session, pID, pData, false);
    }

    public final int getLength() {
        return packetData.length;
    }

    public final byte[] getData() {
        return packetData;
    }

    public final int getID() {
        return packetID;
    }

    public final Size getSize() {
        return size;
    }

    public final IoSession getSession() {
        return session;
    }

    public final boolean isBare() {
        return isBare;
    }

    public final byte[] getRemainingData() {
        byte[] data = new byte[getLength() - caret];
        System.arraycopy(packetData, caret, data, 0, data.length);
        caret += data.length;
        return data;
    }

    public final byte readSignedByte() {
        return packetData[caret++];
    }

    public final byte readNegatedByte() {
        return (byte) -packetData[caret++];
    }

    public final int readByteA() {
        return 0xff & packetData[caret++] - 128;
    }

    public final int readByteS() {
        return 128 - (0xff & packetData[caret++]);
    }

    public final int readUnsignedShortA() {
        int i = ((packetData[caret++] - 128) & 0xff) + ((packetData[caret++] & 0xff) << 8);
        if(i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public final int readShortB() {
        return ((packetData[caret++] & 0xff) << 8) + (packetData[caret++] & 0xff);
    }

    public final int readUnsignedShortC() {
        int i = (packetData[caret++] & 0xff) + ((packetData[caret++] & 0xff) << 8);
        if(i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public final int readShortD() {
        return ((packetData[caret++] & 0xff) << 8) + (0xff & packetData[caret++] - 128);
    }

    public final int read3BytesA() {
        return ((packetData[caret++] & 0xff) << 16) + ((packetData[caret++] & 0xff) << 8) + (packetData[caret++] & 0xff);
    }

    public final int readIntA() {
        return ((packetData[caret++] & 0xff) << 24) + ((packetData[caret++] & 0xff) << 16) + ((packetData[caret++] & 0xff) << 8) + (packetData[caret++] & 0xff);
    }

    public final int readIntB() {
        return ((packetData[caret++] & 0xff) << 16) + ((packetData[caret++] & 0xff) << 24) + (packetData[caret++] & 0xff) + ((packetData[caret++] & 0xff) << 8);
    }

    public final int readIntC() {
        return ((packetData[caret++] & 0xff) << 8) + (packetData[caret++] & 0xff) + ((packetData[caret++] & 0xff) << 24) + ((packetData[caret++] & 0xff) << 16);
    }

    public final int readIntD() {
        return (packetData[caret++] & 0xff) + ((packetData[caret++] & 0xff) << 8) + ((packetData[caret++] & 0xff) << 16) + ((packetData[caret++] & 0xff) << 24);
    }

    public final long readLongA() {
        long l = 0xffffffffL & readIntA();
        long l2 = readIntA() & 0xffffffffL;
        return l2 + (l << 32);
    }

    public int readLongD() {
        caret += 4;
        return ((packetData[caret - 4] & 0xff) << 24) + ((packetData[caret - 3] & 0xff) << 16) + ((packetData[caret - 2] & 0xff) << 8) + (packetData[caret - 1] & 0xff);
    }

    public final long readLongB() {
        long l = 0xffffffffL & readIntD();
        long l2 = readIntD() & 0xffffffffL;
        return (l2 << 32) + l; //32
    }

    public final long readLongC() {
        long l = (long) readLongD() & 0xffffffffL;
        long l1 = (long) readLongD() & 0xffffffffL;
        return (l << 32) + l1;
    }

    public final void skip(int amnt) {
        this.caret += amnt;
    }

    public String readRSString() {
        int i = caret;
        while(packetData[caret++] != 10) {
            ;
        }
        return new String(packetData, i, caret - i - 1);
    }

    public final String toString() {
        StringBuilder s = new StringBuilder("PACKET " + packetID + ": length=" + getLength());
        if(packetData.length > 0) {
            s.append(", data=[");
            for(int i = 0; i < getLength(); i++) {
                int ub = packetData[i] & 0xff;
                if(ub / 16 > 0) {
                    s.append(Packet.hex[ub / 16]);
                }
                s.append(Packet.hex[ub % 16]);
            }
        }
        return s.append("]").toString();
    }
}
