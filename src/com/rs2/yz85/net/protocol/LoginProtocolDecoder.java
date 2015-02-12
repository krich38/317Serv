package com.rs2.yz85.net.protocol;

import com.rs2.yz85.impl.net.PacketImpl;
import com.rs2.yz85.model.Item;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.net.packet.builder.PacketBuilder;
import com.rs2.yz85.net.packet.builder.PacketConstructor;
import com.rs2.yz85.util.Logger;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.util.Map;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class LoginProtocolDecoder extends CumulativeProtocolDecoder {
    private final World world = World.getWorld();
    private final Map<String, PacketBuilder> builders = PacketBuilder.builders;

    public final boolean doDecode(IoSession session, ByteBuffer in, ProtocolDecoderOutput out) {
        try {
            Object loginStageObj = session.getAttribute("COMPLETED_FIRST");
            boolean firstPart = false;
            if(loginStageObj != null) {
                firstPart = (Boolean) loginStageObj;
            }
            if(!firstPart) {
                if(in.remaining() >= 2) {
                    in.skip(2);
                    long serverSessionKey = ((long) (Math.random() * 99999999D) << 32) + (long) (Math.random() * 99999999D);
                    session.setAttribute("SERVER_SESSION_KEY", serverSessionKey);
                    session.write(new PacketConstructor().setBare(true).addBytes(Packet.FIRST_RESPONSE).addLong(serverSessionKey).toPacket());
                    session.setAttribute("COMPLETED_FIRST", true);
                    return true;
                }
                in.rewind();
                return false;
            } else {
                if(in.remaining() >= 2) {
                    in.skip(1);
                    int loginPacketSize = in.get() & 0xff;
                    if(in.remaining() >= loginPacketSize) {
                        byte[] payload = new byte[loginPacketSize];
                        in.get(payload);
                        Packet p = new PacketImpl(session, -1, payload);
                        p.skip(42);
                        long clientSessionKey = p.readLongA(), serverSessionKey = p.readLongA();
                        p.skip(4);
                        String user = p.readRSString(), pass = p.readRSString();
                        Logger.log(user + " is signing onto the server...");
                        int sessionKey[] = new int[] { (int) (clientSessionKey >> 32), (int) clientSessionKey, (int) (serverSessionKey >> 32), (int) serverSessionKey };
                        session.setAttribute("CRYPTION_IN", new Cryption(sessionKey));
                        for(int i = 0; i < sessionKey.length; i++) {
                            sessionKey[i] += 50;
                        }
                        session.setAttribute("CRYPTION_OUT", new Cryption(sessionKey));
                        session.getFilterChain().remove("protocolFilter");
                        session.getFilterChain().addLast("protocolFilter", new ProtocolCodecFilter(new CodecFactory()));
                        Player player = new Player(session);
                        session.setAttachment(player);
                        player.setCredentials(user, pass);
                        return completeLogin(player);
                    }
                }
                in.rewind();
                return false;
            }
        } catch(Exception e) {
            Logger.err(e);
        }
        return false;
    }

    private boolean completeLogin(Player player) {
        IoSession session = player.getSession();
        switch(player.getResponseCode()) {
            case 2:
                session.setIdleTime(IdleStatus.BOTH_IDLE, 30);
                builders.get("login_response").writePacket(session, null);
                builders.get("login_packet").writePacket(session, null);
                for(int i = 0; i < 25; i++) {
                    builders.get("single_stat").writePacket(session, new Object[] { i });
                }
                builders.get("unknown_107").writePacket(session, null);
                int[][] sideBarInterfaces = { { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0 }, { 3917, 638, 3213, 1644, 5608, 1151, 1, 5065, 5715, 2449, 4445, 147, 6299, 2423 } };
                for(int i = 0; i < sideBarInterfaces[0].length; i++) {
                    session.write(new PacketConstructor().setId(71).addShort(sideBarInterfaces[1][i]).addByteAlternateOrder((byte) sideBarInterfaces[0][i]).toPacket());
                }
                player.getInventory().add(new Item(1073, 1, Item.ItemType.ITEM_LEG));
                builders.get("inventory_packet").writePacket(session, null);
                builders.get("standard_string").writePacket(session, new Object[] { "Hello and welcome to my new server...omg" });
                world.connectedPlayer(player);
                return true;
            default:
                builders.get("login_response").writePacket(session, null);
                break;
        }
        return false;
    }
}
