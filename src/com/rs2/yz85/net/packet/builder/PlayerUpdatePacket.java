package com.rs2.yz85.net.packet.builder;

import com.rs2.yz85.model.*;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.util.EntityTracker;
import org.apache.mina.common.IoSession;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PlayerUpdatePacket extends PacketBuilder {
    public final void writePacket(IoSession session, Object[] argsNeeded) {
        Player player = (Player) session.getAttachment();
        PacketConstructor mainPacket = new PacketConstructor().setId(81).setSize(Packet.Size.Short), updatePacket = new PacketConstructor();
        int mask = getUpdateMask(player, player);
        appendMovementUpdate(mainPacket, player, mask);
        appendUpdateBlock(updatePacket, player, mask);
        EntityTracker<Player> players = player.getWatchedPlayers();
        mainPacket.addBits(players.size(), 8);
        for(Player p : players.getKnownEntities()) {
            if(!players.isRemoving(p)) {
                int thisPlayersMask = getUpdateMask(player, p);
                boolean thisPlayerMapAreaChanged = p.mapAreaChanged();
                p.setMapAreaChanged(false);
                appendMovementUpdate(mainPacket, p, thisPlayersMask);
                p.setMapAreaChanged(thisPlayerMapAreaChanged);
                appendUpdateBlock(updatePacket, p, thisPlayersMask);
            } else {
                mainPacket.addBits(1, 1).addBits(3, 2);
            }
        }
        for(Player p : players.getNewEntities()) {
            addPlayer(player, p, mainPacket, updatePacket);
        }
        if(updatePacket.getLength() > 0) {
            mainPacket.addBits(2047, 11).addBytes(updatePacket.toPacket().getData());
        }
        session.write(mainPacket.toPacket());
    }

    private void appendMovementUpdate(PacketConstructor spb, Player player, int updateMask) {
        if(updateMask > 0 || player.mapAreaChanged() || player.hasMoved()) {
            spb.addBits(1, 1);
            if(player.mapAreaChanged()) {
                spb.addBits(3, 2).addBits(player.getWaypoint().getHeight(), 2).addBits(1, 1).addBits(updateMask > 0 ? 1 : 0, 1).addBits(player.getWaypoint().getLocalY(), 7).addBits(player.getWaypoint().getLocalX(), 7);
            } else if(player.hasMoved()) {
                if(player.isRunning() && player.getLastSprite() != -1) {
                    spb.addBits(2, 2).addBits(MobileEntity.CLIENT_SPRITES[player.getLastSprite()], 3).addBits(MobileEntity.CLIENT_SPRITES[player.getSprite()], 3).addBits(updateMask > 0 ? 1 : 0, 1);
                } else {
                    spb.addBits(1, 2).addBits(MobileEntity.CLIENT_SPRITES[player.getSprite()], 3).addBits(updateMask > 0 ? 1 : 0, 1);
                }
            } else {
                spb.addBits(0, 2);
            }
        } else {
            spb.addBits(0, 1);
        }
    }

    private void appendUpdateBlock(PacketConstructor spb, Player player, int updateMask) {
        if(updateMask != 0) {
            if(updateMask >= 256) {
                updateMask |= 64;
                spb.addByte((byte) (updateMask & 0xff)).addByte((byte) (updateMask >> 8));
            } else {
                spb.addByte((byte) updateMask);
            }
            if((updateMask & 128) != 0) {
                ChatMessage cm = player.getLastChatMessage();
                spb.addLEShort(((cm.getColour() & 0xff) << 8) + (cm.getEffects() & 0xFF)).addByte((byte) 0).addByte((byte) -cm.getChatData().length);
                byte[] msg = cm.getChatData();
                for(int i = msg.length - 1; i >= 0; i--) {
                    spb.addByte(msg[i]);
                }
            } else if((updateMask & 16) != 0) {
                PacketConstructor appearance = new PacketConstructor().addByte((byte) ((player.getGender().compareTo(PlayerGender.MALE) == 0) ? 0 : 1)).addByte((byte) 0);
                Equipped e = player.getEquipment();
                if(e.getHelm() != null) {
                    appearance.addShort(512 + e.getHelm().getId());
                } else {
                    appearance.addByte((byte) 0);
                }
                if(e.getCape() != null) {
                    appearance.addShort(512 + e.getCape().getId());
                } else {
                    appearance.addByte((byte) 0);
                }
                if(e.getAmulet() != null) {
                    appearance.addShort(512 + e.getAmulet().getId());
                } else {
                    appearance.addByte((byte) 0);
                }
                if(e.getWeapon() != null) {
                    appearance.addShort(512 + e.getWeapon().getId());
                } else {
                    appearance.addByte((byte) 0);
                }
                appearance.addShort(e.getChest() != null ? 512 + e.getChest().getId() : 256 + player.getAppearance().getTorso());
                if(e.getShield() != null) {
                    appearance.addShort(512 + e.getShield().getId());
                } else {
                    appearance.addByte((byte) 0);
                }
                appearance.addShort(256 + player.getAppearance().getArms()).addShort(e.getLegs() != null ? 512 + e.getLegs().getId() : 256 + player.getAppearance().getLegs()).addShort(256 + player.getAppearance().getHead()).addShort(e.getGloves() != null ? 512 + e.getGloves().getId() : 256 + player.getAppearance().getHands()).addShort(e.getBoots() != null ? 512 + e.getBoots().getId() : 256 + player.getAppearance().getFeet()).addByte((byte) 0).addByte((byte) player.getAppearance().getHairColour()).addByte((byte) player.getAppearance().getTorsoColour()).addByte((byte) player.getAppearance().getLegColour()).addByte((byte) player.getAppearance().getFeetColour()).addByte((byte) player.getAppearance().getSkinColour());
                for(int i : Packet.ANIMATION_INDICES) {
                    appearance.addShort(i);
                }
                Packet appearancePacket = appearance.addLong(player.getUserHash()).addByte((byte) player.getCombatLevel()).addShort((byte) 0).toPacket();
                spb.addByte((byte) (-appearancePacket.getLength())).addBytes(appearancePacket.getData(), 0, appearancePacket.getLength());
            }
        }
    }

    private void addPlayer(Player thisPlayer, Player player, PacketConstructor main, PacketConstructor update) {
        main.addBits(player.getIndex(), 11).addBits(1, 1);
        appendUpdateBlock(update, player, getUpdateMask(thisPlayer, player));
        main.addBits(1, 1);
        int dX = player.getWaypoint().getXCoord() - thisPlayer.getWaypoint().getXCoord(), dY = player.getWaypoint().getYCoord() - thisPlayer.getWaypoint().getYCoord();
        main.addBits(dY < 0 ? dY + 32 : dY, 5).addBits(dX < 0 ? dY + 32 : dY, 5);
    }

    private int getUpdateMask(Player player, Player target) {
        int updateMask = 0;
        if(target.getLastChatMessage() != null && !player.equals(target)) {
            updateMask |= 128;
        }
        if(player.getKnownAppearanceId(target) < target.getAppearance().getId()) {
            updateMask |= 16;
            player.setKnownAppearanceId(target, target.getAppearance().getId());
        }
        return updateMask;
    }

    public String getString() {
        return "player_update";
    }
}

