package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class IgnorePacket implements PacketHandler {
    public final void handlePacket(Packet p) {
    }

    public final int[] getBindings() {
        return new int[] { IDLE_PACKET, LOADING_FINISHED, CAMERA_ANGLE_CHANGED, MOUSE_CLICKED, AR_TYPE, CAMERA_FOCUS, MAP_REGION_CHANGED };
    }
}
