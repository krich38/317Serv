package com.rs2.yz85.model;

import com.rs2.yz85.impl.model.PlayerAppearanceImpl;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class PlayerAppearance {
    public static PlayerAppearance instance(int[] args) {
        return new PlayerAppearanceImpl(args);
    }

    public abstract int getHead();

    public abstract void setHead(int head);

    public abstract int getTorso();

    public abstract void setTorso(int torso);

    public abstract int getArms();

    public abstract void setArms(int arms);

    public abstract int getHands();

    public abstract void setHands(int hands);

    public abstract int getLegs();

    public abstract void setLegs(int legs);

    public abstract int getFeet();

    public abstract void setFeet(int feet);

    public abstract int getHairColour();

    public abstract void setHairColour(int hairColour);

    public abstract int getTorsoColour();

    public abstract void setTorsoColour(int torsoColour);

    public abstract int getLegColour();

    public abstract void setLegColour(int legColour);

    public abstract int getFeetColour();

    public abstract void setFeetColour(int feetColour);

    public abstract int getSkinColour();

    public abstract void setSkinColour(int skinColour);

    public abstract boolean hasChanged();

    public abstract void setChanged(boolean b);

    public abstract void incrementId();

    public abstract int getId();

    public abstract void setJaw(int jaw);
}
