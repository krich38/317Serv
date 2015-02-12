package com.rs2.yz85.impl.model;

import com.rs2.yz85.model.PlayerAppearance;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PlayerAppearanceImpl extends PlayerAppearance {
    private int head, torso, arms, hands, legs, feet, hairColour, torsoColour, legColour, feetColour, skinColour, jaw, id = 0;
    private boolean hasChanged = true;

    public PlayerAppearanceImpl(int[] i) {
        this.head = i[0];
        this.torso = i[1];
        this.arms = i[2];
        this.hands = i[3];
        this.legs = i[4];
        this.feet = i[5];
        this.hairColour = i[6];
        this.torsoColour = i[7];
        this.legColour = i[8];
        this.feetColour = i[9];
        this.skinColour = i[10];
    }

    public final int getHead() {
        return head;
    }

    public final void setHead(int head) {
        this.head = head;
    }

    public final int getTorso() {
        return torso;
    }

    public final void setTorso(int torso) {
        this.torso = torso;
    }

    public final int getArms() {
        return arms;
    }

    public final void setArms(int arms) {
        this.arms = arms;
    }

    public final int getHands() {
        return hands;
    }

    public final void setHands(int hands) {
        this.hands = hands;
    }

    public final int getLegs() {
        return legs;
    }

    public final void setLegs(int legs) {
        this.legs = legs;
    }

    public final int getFeet() {
        return feet;
    }

    public final void setFeet(int feet) {
        this.feet = feet;
    }

    public final int getHairColour() {
        return hairColour;
    }

    public final void setHairColour(int hairColour) {
        this.hairColour = hairColour;
    }

    public final int getTorsoColour() {
        return torsoColour;
    }

    public final void setTorsoColour(int torsoColour) {
        this.torsoColour = torsoColour;
    }

    public final int getLegColour() {
        return legColour;
    }

    public final void setLegColour(int legColour) {
        this.legColour = legColour;
    }

    public final int getFeetColour() {
        return feetColour;
    }

    public final void setFeetColour(int feetColour) {
        this.feetColour = feetColour;
    }

    public final int getSkinColour() {
        return skinColour;
    }

    public final void setSkinColour(int skinColour) {
        this.skinColour = skinColour;
    }

    public final boolean hasChanged() {
        return hasChanged;
    }

    public final void setChanged(boolean b) {
        hasChanged = b;
    }

    public final void incrementId() {
        id++;
    }

    public final int getId() {
        return id;
    }

    public void setJaw(int jaw) {
        this.jaw = jaw;
    }
}
