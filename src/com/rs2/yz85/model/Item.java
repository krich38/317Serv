package com.rs2.yz85.model;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Item extends Entity {
    private int amount;
    private ItemType itemType;

    public enum ItemType {
        ITEM_LEG,
        ITEM_SHIELD,
        ITEM_BOOTS,
        ITEM_WEAPON,
        ITEM_AMULET,
        ITEM_RING,
        ITEM_ARROW,
        ITEM_HELMET,
        ITEM_GLOVES,
        ITEM_CHEST
    }

    public Item(int id, int amount, ItemType itemType) {
        super.setId(id);
        this.amount = amount;
        this.itemType = itemType;
    }

    public final int getID() {
        return getId();
    }

    public final int getAmount() {
        return amount;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
