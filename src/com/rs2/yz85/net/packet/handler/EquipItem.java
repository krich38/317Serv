package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Equipped;
import com.rs2.yz85.model.Inventory;
import com.rs2.yz85.model.Item;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class EquipItem implements PacketHandler {
    public final void handlePacket(Packet p) {
        Player player = (Player) p.getSession().getAttachment();
        switch(p.getID()) {
            case EQUIP_ITEM:
                int itemID = p.readShortB(), index = p.readShortD(), interfaceId = p.readShortD();
                if(interfaceId == 3214) {
                    Inventory inv = player.getInventory();
                    Item i = inv.getItem(index);
                    if(i != null && itemID == i.getId()) {
                        Equipped eq = player.getEquipment();
                        switch(i.getItemType()) {
                            case ITEM_LEG:
                                eq.setLegs(i);
                                break;
                            case ITEM_AMULET:
                                eq.setAmulet(i);
                                break;
                            case ITEM_BOOTS:
                                eq.setBoots(i);
                                break;
                            case ITEM_SHIELD:
                                eq.setShield(i);
                                break;
                            case ITEM_WEAPON:
                                eq.setWeapon(i);
                                break;
                            case ITEM_RING:
                                eq.setRing(i);
                                break;
                            case ITEM_ARROW:
                                eq.setArrows(i);
                                break;
                            case ITEM_GLOVES:
                                eq.setGloves(i);
                                break;
                            case ITEM_CHEST:
                                eq.setChest(i);
                                break;
                            case ITEM_HELMET:
                                eq.setHelm(i);
                                break;
                        }
                        inv.remove(i);
                        //PacketBuilder.builders.get("delete_item").writePacket(player.getSession(), new Object[] { 3214 });
                        player.getAppearance().setChanged(true);
                    }
                }
                break;
            case DEQUIP_ITEM:
                interfaceId = p.readShortD();
                index = p.readShortD();
                itemID = p.readShortD();
                System.out.println(interfaceId + " : " + index + " : " + itemID);
        }
    }

    public final int[] getBindings() {
        return new int[] { EQUIP_ITEM, DEQUIP_ITEM };
    }
}
