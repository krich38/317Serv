package com.rs2.yz85.model;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public interface ChatMessage {
    public byte[] getChatData();

    public String getMessage();

    public int getLength();

    public int getColour();

    public int getEffects();

    public Player getSender();
}
