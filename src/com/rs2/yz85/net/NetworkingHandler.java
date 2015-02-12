package com.rs2.yz85.net;

import com.rs2.yz85.ServerEngine;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.net.packet.PacketQueue;
import com.rs2.yz85.net.protocol.LoginCodecFactory;
import com.rs2.yz85.util.Logger;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoFilter;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class NetworkingHandler implements IoHandler {
    private final PacketQueue packetQueue;
    private final IoFilter loginFactory = new ProtocolCodecFilter(new LoginCodecFactory());
    private final Map<String, Integer> conCounter;
    private final Set<String> notified, connected = new HashSet<String>();
    private final World world;

    public NetworkingHandler(ServerEngine engine) {
        this.packetQueue = engine.getPacketQueue();
        this.conCounter = new HashMap<String, Integer>(450);
        this.world = World.getWorld();
        this.notified = new HashSet<String>();
    }

    public final void sessionCreated(IoSession session) throws Exception {
        if(World.getWorld().isOpen()) {
            String host = ((InetSocketAddress) session.getRemoteAddress()).getHostName();
            if(!connected.contains(host) || host.contains("kyle")) {
                if(world.isTempHostBanned(host) && !notified.contains(host)) {
                    Logger.log("Refused connection from ".concat(host).concat(" as connections from this host are banned."));
                    notified.add(host);
                    session.close();
                    return;
                } else if(couldBeMalicious(host)) {
                    world.tempBanHost(host);
                }
                session.getFilterChain().addLast("protocolFilter", loginFactory);
                connected.add(host);
            } else {
                session.close();
            }
        }
    }

    private boolean couldBeMalicious(String host) {
        int conCounterAmount = 0;
        Object o = conCounter.get(host);
        if(o != null) {
            conCounterAmount = (Integer) o;
        }
        if(conCounterAmount == 0) {
            conCounter.put(host, 1);
        } else if(conCounterAmount < 15) {
            conCounter.remove(host);
            conCounter.put(host, ++conCounterAmount);
        } else {
            conCounter.remove(host);
            return true;
        }
        return false;
    }

    public final void sessionOpened(IoSession session) throws Exception {
        session.setIdleTime(IdleStatus.BOTH_IDLE, 5);
    }

    public final void sessionClosed(IoSession session) throws Exception {
        world.disconnectedPlayer((Player) session.getAttachment());
        connected.remove(((InetSocketAddress) session.getRemoteAddress()).getHostName());
    }

    public final void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
        session.close();
    }

    public final void exceptionCaught(IoSession ioSession, Throwable cause) throws Exception {
        ioSession.close();
    }

    public final void messageReceived(IoSession session, Object packet) throws Exception {
        packetQueue.add((Packet) packet);
    }

    public final void messageSent(IoSession ioSession, Object o) throws Exception {
    }
}
