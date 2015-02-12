package com.rs2.yz85.model;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Equipped {
    private final Item[] equipped;

    public Equipped() {
        this.equipped = new Item[14];
    }

    public final void setHelm(Item helm) {
        equipped[HELM] = helm;
    }

    public final void setCape(Item cape) {
        equipped[CAPE] = cape;
    }

    public final void setAmulet(Item amulet) {
        equipped[AMULET] = amulet;
    }

    public final void setArrows(Item arrows) {
        equipped[ARROWS] = arrows;
    }

    public final void setWeapon(Item weapon) {
        equipped[WEAPON] = weapon;
    }

    public final void setChest(Item plateBody) {
        equipped[CHEST] = plateBody;
    }

    public final void setShield(Item shield) {
        equipped[SHIELD] = shield;
    }

    public final void setLegs(Item legs) {
        equipped[LEGS] = legs;
    }

    public final void setGloves(Item gloves) {
        equipped[HANDS] = gloves;
    }

    public final void setBoots(Item boots) {
        equipped[FEET] = boots;
    }

    public final void setRing(Item ring) {
        equipped[RING] = ring;
    }

    public final Item getHelm() {
        return equipped[HELM];
    }

    public final Item getCape() {
        return equipped[CAPE];
    }

    public final Item getAmulet() {
        return equipped[AMULET];
    }

    public final Item getArrows() {
        return equipped[ARROWS];
    }

    public final Item getWeapon() {
        return equipped[WEAPON];
    }

    public final Item getChest() {
        return equipped[CHEST];
    }

    public final Item getShield() {
        return equipped[SHIELD];
    }

    public Item getLegs() {
        return equipped[LEGS];
    }

    public Item getGloves() {
        return equipped[HANDS];
    }

    public Item getBoots() {
        return equipped[FEET];
    }

    public Item getRing() {
        return equipped[RING];
    }

    public Item[] getEquippedItems() {
        return equipped;
    }

    public static final int HELM = 0, CAPE = 1, AMULET = 2, WEAPON = 3, CHEST = 4, SHIELD = 5, LEGS = 7, HANDS = 9, FEET = 10, RING = 12, ARROWS = 13;
}
