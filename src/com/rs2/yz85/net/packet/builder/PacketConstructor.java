package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.impl.net.PacketImpl;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.net.protocol.Cryption;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PacketConstructor {
    private byte[] payload;
    private int curLength, id, bitPosition;
    private Packet.Size size = Packet.Size.Fixed;
    private boolean bare = false;

    public PacketConstructor() {
        this(32);
    }

    public PacketConstructor(int capacity) {
        payload = new byte[capacity];
    }

    private void ensureCapacity(int minimumCapacity) {
        if(minimumCapacity >= payload.length) {
            expandCapacity(minimumCapacity);
        }
    }

    private void expandCapacity(int minimumCapacity) {
        int newCapacity = (payload.length + 1) * 2;
        if(newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;
        } else if(minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
        }
        byte[] newPayload = new byte[newCapacity];
        while(curLength > payload.length) {
            curLength--;
        }
        System.arraycopy(payload, 0, newPayload, 0, curLength);
        payload = newPayload;
    }

    public final PacketConstructor setBare(boolean bare) {
        this.bare = bare;
        return this;
    }

    public final PacketConstructor setId(int id) {
        this.id = id;
        return this;
    }

    public final PacketConstructor setSize(Packet.Size s) {
        size = s;
        return this;
    }

    public final PacketConstructor addBits(int value, int numBits) {
        int bytePos = bitPosition >> 3, bitOffset = 8 - (bitPosition & 7);
        bitPosition += numBits;
        curLength = (bitPosition + 7) / 8;
        ensureCapacity(curLength);
        for(; numBits > bitOffset; bitOffset = 8) {
            payload[bytePos] &= ~Packet.BITMASKS[bitOffset];
            payload[bytePos++] |= (value >> (numBits - bitOffset)) & Packet.BITMASKS[bitOffset];
            numBits -= bitOffset;
        }
        if(numBits == bitOffset) {
            payload[bytePos] &= ~Packet.BITMASKS[bitOffset];
            payload[bytePos] |= value & Packet.BITMASKS[bitOffset];
        } else {
            payload[bytePos] &= ~(Packet.BITMASKS[numBits] << (bitOffset - numBits));
            payload[bytePos] |= (value & Packet.BITMASKS[numBits]) << (bitOffset - numBits);
        }
        return this;
    }

    public final PacketConstructor addBytes(byte[] data) {
        return addBytes(data, 0, data.length);
    }

    public final PacketConstructor addBytes(byte[] data, int offset, int len) {
        int newLength = curLength + len;
        ensureCapacity(newLength);
        System.arraycopy(data, offset, payload, curLength, len);
        curLength = newLength;
        return this;
    }

    public final PacketConstructor addShortAlternateOrder(int i) {
        ensureCapacity(curLength + 2);
        return addByte((byte) (i >> 8), false).addByte((byte) (i + 128), false);
    }

    public final PacketConstructor addByte(byte val) {
        return addByte(val, true);
    }

    public final PacketConstructor addByteAlternateOrder(int i) {
        return addByte((byte) (i + 128), true);
    }

    private PacketConstructor addByte(byte val, boolean checkCapacity) {
        if(checkCapacity) {
            ensureCapacity(curLength + 1);
        }
        payload[curLength++] = val;
        return this;
    }

    public final PacketConstructor addShort(int val) {
        ensureCapacity(curLength + 2);
        addByte((byte) (val >> 8), false);
        addByte((byte) val, false);
        return this;
    }

    public final PacketConstructor addLEShort(int val) {
        ensureCapacity(curLength + 2);
        addByte((byte) val, false);
        addByte((byte) (val >> 8), false);
        return this;
    }

    public final PacketConstructor setShort(int val, int offset) {
        payload[offset++] = (byte) (val >> 8);
        payload[offset++] = (byte) val;
        if(curLength < offset + 2) {
            curLength += 2;
        }
        return this;
    }

    public final PacketConstructor addInt(int val) {
        ensureCapacity(curLength + 4);
        return addByte((byte) (val >> 24), false).addByte((byte) (val >> 16), false).addByte((byte) (val >> 8), false).addByte((byte) val, false);
    }

    public final PacketConstructor addIntReversed(int val) {
        ensureCapacity(curLength + 4);
        addByte((byte) (val >> 8), false);
        addByte((byte) val, false);
        addByte((byte) (val >> 24), false);
        addByte((byte) (val >> 16), false);
        return this;
    }

    public final PacketConstructor addIntC(int i) {
        payload[curLength++] = (byte) (i >> 16);
        payload[curLength++] = (byte) (i >> 24);
        payload[curLength++] = (byte) i;
        payload[curLength++] = (byte) (i >> 8);
        return this;
    }

    public final PacketConstructor addIntBlock(int val) {
        ensureCapacity(curLength + 4);
        addByte((byte) (val >> 16), false);
        addByte((byte) (val >> 24), false);
        addByte((byte) val, false);
        addByte((byte) (val >> 8), false);
        return this;
    }

    public final PacketConstructor addLEInt(int val) {
        ensureCapacity(curLength + 4);
        addByte((byte) val, false);
        addByte((byte) (val >> 8), false);
        addByte((byte) (val >> 16), false);
        addByte((byte) (val >> 24), false);
        return this;
    }

    public final PacketConstructor addLong(long val) {
        addInt((int) (val >> 32));
        addInt((int) (val));
        return this;
    }

    public final PacketConstructor addLELong(long val) {
        addLEInt((int) (val));
        addLEInt((int) (val >> 32));
        return this;
    }

    private static final int frameStackSize = 10;
    private int frameStackPtr = -1;
    private int frameStack[] = new int[frameStackSize];

    public PacketConstructor createFrameVarSize(int id, Cryption cr) {
        payload[curLength++] = (byte) (id + cr.getNextKey());
        payload[curLength++] = 0;
        if(frameStackPtr >= frameStackSize - 1) {
            throw new RuntimeException("Stack overflow");
        } else {
            frameStack[++frameStackPtr] = curLength;
        }
        return this;
    }

    public PacketConstructor addIBigEndian(int i) {
        payload[curLength++] = (byte) (i + 128);
        payload[curLength++] = (byte) (i >> 8);
        return this;
    }

    public PacketConstructor createFrameVarSizeWord(int id, Cryption cr) {
        payload[curLength++] = (byte) (id + cr.getNextKey());
        addIntK(0);
        if(frameStackPtr >= frameStackSize - 1) {
            throw new RuntimeException("Stack overflow");
        } else {
            frameStack[++frameStackPtr] = curLength;
        }
        return this;
    }

    public PacketConstructor endFrameVarSize() {
        if(frameStackPtr < 0) {
            throw new RuntimeException("Stack empty");
        } else {
            writeFrameSize(curLength - frameStack[frameStackPtr--]);
        }
        return this;
    }

    public PacketConstructor endFrameVarSizeWord() {
        if(frameStackPtr < 0) {
            throw new RuntimeException("Stack empty");
        } else {
            writeFrameSizeWord(curLength - frameStack[frameStackPtr--]);
        }
        return this;
    }

    public PacketConstructor writeFrameSize(int i) {
        payload[curLength - i - 1] = (byte) i;
        return this;
    }

    public PacketConstructor addIntK(int i) {
        payload[curLength++] = (byte) (i >> 8);
        payload[curLength++] = (byte) i;
        return this;
    }

    public PacketConstructor writeFrameSizeWord(int i) {
        payload[curLength - i - 2] = (byte) (i >> 8);
        payload[curLength - i - 1] = (byte) i;
        return this;
    }

    public final PacketConstructor addString(String s) {
        ensureCapacity(curLength + s.length() + 1);
        System.arraycopy(s.getBytes(), 0, payload, 0, s.length());
        curLength += s.length();
        payload[curLength++] = 10;
        return this;
    }

    public final int getLength() {
        return curLength;
    }

    public final Packet toPacket() {
        byte[] data = new byte[curLength];
        System.arraycopy(payload, 0, data, 0, curLength);
        return new PacketImpl(null, data, id, size, bare);
    }
}
