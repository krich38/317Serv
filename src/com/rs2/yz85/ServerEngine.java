package com.rs2.yz85;

import com.rs2.yz85.event.EventModule;
import com.rs2.yz85.model.World;
import com.rs2.yz85.net.packet.PacketQueue;
import com.rs2.yz85.net.packet.handler.PacketHandler;
import com.rs2.yz85.util.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ServerEngine {
    private PacketQueue queue;
    private Map<Integer, PacketHandler> packetHandlers = null;
    private Timer timer;

    public ServerEngine() {
        World.getWorld().setEngine(this);
    }

    public final void initiateEvents() {
        if(timer == null) {
            timer = new Timer();
        } else {
            timer.purge();
        }
        File eventDir = new File("./com/rs2/yz85/impl/event/");
        if(eventDir.isDirectory()) {
            try {
                for(File i : eventDir.listFiles()) {
                    Class c = Class.forName("com.rs2.yz85.impl.event." + i.getName().replace(".class", ""));
                    if(c.getSuperclass().getSimpleName().equals("EventModule")) {
                        EventModule o = (EventModule) c.newInstance();
                        if(o.willBeScheduled()) {
                            long initDelay = o.getDelay()[0], recurDelay = o.getDelay()[1];
                            timer.scheduleAtFixedRate(o, initDelay, recurDelay);
                            Logger.log("Loaded EventModule " + o.getClass().getSimpleName() + "[" + initDelay + "," + recurDelay + "]");
                        }
                    }
                }
            } catch(Exception e) {
                Logger.err(e);
            }
        }
    }

    public final PacketQueue getPacketQueue() {
        return queue == null ? queue = new PacketQueue() : queue;
    }

    public final void setPacketHandlers(Map<Integer, PacketHandler> packetHandlers) {
        if(this.packetHandlers == null) {
            this.packetHandlers = new HashMap<Integer, PacketHandler>(256);
        }
        this.packetHandlers.clear();
        this.packetHandlers.putAll(packetHandlers);
    }

    public final Map<Integer, PacketHandler> getPacketHandlers() {
        return packetHandlers;
    }

    public Timer getTimer() {
        return timer;
    }
}
