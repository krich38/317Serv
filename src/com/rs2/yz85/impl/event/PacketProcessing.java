package com.rs2.yz85.impl.event;

import com.rs2.yz85.event.EventModule;
import com.rs2.yz85.model.World;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.net.packet.PacketQueue;
import com.rs2.yz85.net.packet.handler.PacketHandler;
import com.rs2.yz85.util.Logger;

import java.util.Map;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PacketProcessing extends EventModule {
    private final PacketQueue queue = World.getWorld().getEngine().getPacketQueue();
    private final Map<Integer, PacketHandler> packetHandlers = World.getWorld().getEngine().getPacketHandlers();

    public final void run() {
        try {
            while(queue.hasPackets()) {
                Packet p = queue.pop();
                int id = p.getID();
                if(packetHandlers.containsKey(id)) {
                    packetHandlers.get(id).handlePacket(p);
                } else {
                    Logger.log("unhandled.log", "/** Unhandled packet **\\\n" + p + "\n");
                    Logger.log("Unhandled packet: " + p);
                }
            }
        } catch(InterruptedException e) {
            Logger.err(e);
        }
    }

    public final long[] getDelay() {
        return new long[] { 20, 20 };
    }

    public final boolean willBeScheduled() {
        return true;
    }
}
