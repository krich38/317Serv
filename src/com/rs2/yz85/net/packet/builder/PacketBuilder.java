package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.util.Logger;
import org.apache.mina.common.IoSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class PacketBuilder {
    public static final Map<String, PacketBuilder> builders = new HashMap<String, PacketBuilder>();

    public abstract void writePacket(IoSession session, Object[] neededArgs);

    public abstract String getString();

    public static void loadBuilders() throws FileNotFoundException {
        File builde = new File("./com/rs2/yz85/net/packet/builder/");
        if(builde.isDirectory()) {
            try {
                for(File i : builde.listFiles()) {
                    Class cTmp = Class.forName("com.rs2.yz85.net.packet.builder." + i.getName().replace(".class", ""));
                    if(cTmp.getSuperclass().getSimpleName().equals("PacketBuilder")) {
                        PacketBuilder packetBuilder = (PacketBuilder) cTmp.newInstance();
                        builders.put(packetBuilder.getString(), packetBuilder);
                    }
                }
            } catch(Exception e) {
                Logger.err(e);
            }
        } else {
            throw new FileNotFoundException("PacketBuilder file not found.");
        }
    }
}
