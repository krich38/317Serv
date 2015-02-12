package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public interface PacketHandler {
    public void handlePacket(Packet p);

    public int[] getBindings();

    public static final int IDLE_PACKET = 0, FOCUS_CHANGE = 3, MOUSE_CLICKED = 241, LOADING_FINISHED = 121, CAMERA_ANGLE_CHANGED = 86, ITEM_ON_PLAYER = 14, ALTERNATE_ITEM_OPTION = 16, WALK_COMMAND = 98, REGULAR_WALK = 164, MAP_WALK = 248, PUBLIC_CHAT = 4, BUTTON_CLICK = 185, AR_TYPE = 77, PLAYER_COMMAND = 103, CAMERA_FOCUS = 3, MAP_REGION_CHANGED = 210, REMOVE_FRIEND = 215, ADD_FRIEND = 188, ADD_IGNORE = 133, REMOVE_IGNORE = 74, EQUIP_ITEM = 41, DEQUIP_ITEM = 145, REPORT_PLAYER = 218, APPERANCE_FRAME = 101;
}
