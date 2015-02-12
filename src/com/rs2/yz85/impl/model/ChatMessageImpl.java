package com.rs2.yz85.impl.model;

import com.rs2.yz85.model.ChatMessage;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.util.Data;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ChatMessageImpl implements ChatMessage {
    private final byte[] chat;
    private final Player player;
    private final int colour, effects, length;
    private final String message;

    public ChatMessageImpl(Player player, byte[] chatData, int chatTextColour, int chatTextEffects) {
        this.player = player;
        this.chat = chatData;
        this.colour = chatTextColour;
        this.effects = chatTextEffects;
        this.length = chatData.length;
        this.message = Data.chatDataToString(chatData);
    }

    public final byte[] getChatData() {
        return chat;
    }

    public final String getMessage() {
        return message;
    }

    public final int getLength() {
        return length;
    }

    public final int getColour() {
        return colour;
    }

    public final int getEffects() {
        return effects;
    }

    public final Player getSender() {
        return player;
    }
}
