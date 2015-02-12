package com.rs2.yz85.model;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class MobileEntity extends Entity {
    public static final int[][] MOB_SPRITES = new int[][] { { 1, 2, 3 }, { 0, -1, 4 }, { 7, 6, 5 } };
    public static final int[] CLIENT_SPRITES = new int[] { 1, 2, 4, 7, 6, 5, 3, 0 };
    public transient int lastSprite = -1, mobSprite = -1, combatLevel = 3;
    private boolean activityChecked;
    private long lastTimeActivity = System.currentTimeMillis();

    public final int getCombatLevel() {
        return combatLevel;
    }

    public final void setCombatLevel(int level) {
        combatLevel = level;
    }

    public final void setWaypoint(Waypoint p, boolean teleported) {
        if(!teleported && location != null) {
            updateSprite(p);
        }
        setLastMoved();
        super.setWaypoint(p);
    }

    private void setLastMoved() {
        this.lastTimeActivity = System.currentTimeMillis();
    }

    public final void updateSprite(Waypoint newLocation) {
        int dX = getWaypoint().getXCoord() - newLocation.getXCoord(), dY = getWaypoint().getYCoord() - newLocation.getYCoord();
        if(dX >= -1 && dX <= 1 && dY >= -1 && dY <= 1) {
            setSprite(MOB_SPRITES[dX + 1][dY + 1]);
        } else {
            throw new ArrayIndexOutOfBoundsException("Sprite index values out of range (-1<=i<=1): " + dX + "," + dY);
        }
    }

    public final void setSprite(int x) {
        lastSprite = mobSprite;
        mobSprite = x;
    }

    public final int getSprite() {
        return mobSprite;
    }

    public int getLastSprite() {
        return lastSprite;
    }

    public final boolean hasMoved() {
        return mobSprite != -1;
    }

    public final void resetMoved() {
        mobSprite = -1;
    }

    public long getLastActivity() {
        return lastTimeActivity;
    }

    public void hasBeenActiveCheck() {
        this.activityChecked = true;
    }

    public boolean beenActiveChecked() {
        return this.activityChecked;
    }
}
