package com.rs2.yz85.net.packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PacketQueue {
    private final Queue<Packet> packets = new LinkedBlockingQueue<Packet>();

    public void add(Packet p) {
        synchronized(packets) {
            packets.offer(p);
        }
    }

    public boolean hasPackets() {
        synchronized(packets) {
            return !packets.isEmpty();
        }
    }

    public Packet pop() throws InterruptedException {
        synchronized(packets) {
            while(packets.isEmpty()) {
                packets.wait();
            }
            return packets.poll();
        }
    }

    public List<Packet> getPackets() {
        synchronized(packets) {
            List<Packet> tmpList = new ArrayList<Packet>(packets.size());
            tmpList.addAll(packets);
            packets.clear();
            return tmpList;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[packets = ");
        Iterator<Packet> i = packets.iterator();
        while(i.hasNext()) {
            sb.append(i.next());
            if(i.hasNext()) {
                sb.append(" | ");
            }
        }
        return sb.append("]").toString();
    }
}
