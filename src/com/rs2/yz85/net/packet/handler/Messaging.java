package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.impl.model.ChatMessageImpl;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Messaging implements PacketHandler {
    public final void handlePacket(Packet p) {
        Player player = (Player) p.getSession().getAttachment();
        switch(p.getID()) {
            case PUBLIC_CHAT:
                if(!World.getWorld().isPlayerMuted(player.getHost())) {
                    int chatTextEffects = 128 - p.readByteS() & 0xff, chatTextColour = 128 - p.readByteS() & 0xff, chatTextSize = p.getLength() - 2;
                    byte[] rawChatData = p.getRemainingData(), chatData = new byte[chatTextSize];
                    for(int i = 0; i < chatTextSize; i++) {
                        chatData[i] = (byte) (rawChatData[chatTextSize - 1 - i] - 128);
                    }
                    player.setLastChatMessage(new ChatMessageImpl(player, chatData, chatTextColour, chatTextEffects));
                } else {
                    player.sendMessage("You are muted.");
                }
                break;
        }
    }

    public final int[] getBindings() {
        return new int[] { PUBLIC_CHAT };
    }
}
