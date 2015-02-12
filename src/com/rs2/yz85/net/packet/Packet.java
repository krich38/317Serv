package com.rs2.yz85.net.packet;

import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public interface Packet {
    public static final byte[] FIRST_RESPONSE = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    public static final int packetLengths[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
            0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
            0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
            0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
            2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
            0, 0, 0, 12, 0, 0, 0, 0, 8, 0, //50
            0, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
            6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
            0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
            0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
            0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
            0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
            1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
            0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
            0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
            0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
            0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
            0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
            0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
            2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
            4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
            0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
            1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
            0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
            0, 0, 6, 6, 0, 0, 0            //250
    }, BITMASKS[] = { 0, 0x1, 0x3, 0x7, 0xf, 0x1f, 0x3f, 0x7f, 0xff, 0x1ff, 0x3ff, 0x7ff, 0xfff, 0x1fff, 0x3fff, 0x7fff, 0xffff, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1 }, FRAMESTACK_SIZE = 10, ANIMATION_INDICES[] = { 0x328, 0x337, 0x333, 0x334, 0x335, 0x336, 0x338 };
    public static final char[] hex = "0123456789ABCDEF".toCharArray();

    public enum Size {
        Byte, Short, Fixed
    }

    public long readLongC();

    public int getLength();

    public byte[] getData();

    public int getID();

    public Size getSize();

    public IoSession getSession();

    public boolean isBare();

    public byte[] getRemainingData();

    public byte readSignedByte();

    public byte readNegatedByte();

    public int readByteA();

    public int readByteS();

    public int readUnsignedShortA();

    public int readShortB();

    public int readUnsignedShortC();

    public int readShortD();

    public int read3BytesA();

    public int readIntA();

    public int readIntB();

    public int readIntC();

    public int readIntD();

    public long readLongA();

    public long readLongB();

    public void skip(int amnt);

    public String readRSString();

    public String toString();
}
