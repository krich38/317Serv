package com.rs2.yz85.util;

import com.rs2.yz85.model.MobileEntity;
import com.rs2.yz85.model.Tile;
import com.rs2.yz85.model.World;

import java.util.NoSuchElementException;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class TileIterator {
    private final Tile[][] tiles = World.getWorld().getTiles();
    private int xoff, yoff, startX, startY, endX, endY, size;

    public TileIterator(int x1, int y1, int x2, int y2, MobileEntity mob) {
        int mobX = mob.getX(), mobY = mob.getY();
        startX = mobX - x1;
        if(startX < 0) {
            startX = 0;
        }
        startY = mobY - y1;
        if(startY < 0) {
            startY = 0;
        }
        endX = mobX + x2;
        if(endX >= 4000) {
            endX = 4000 - 1;
        }
        endY = mobY + y2;
        if(endY >= 4000) {
            endY = 4000 - 1;
        }
        size = startX > endX ? startX - endX : endX - startX * startY > endY ? startY - endY : endY - startY;
        this.xoff = yoff = 0;
    }

    public final Tile next() {
        if((xoff + startX) == endX) {
            xoff = 0;
            if((yoff + startY) == endY) {
                throw new NoSuchElementException("No more elements! (offset=" + xoff + "," + yoff + "; " + "start=" + startX + "," + startY + "; end=" + endX + "," + endY + "; size=" + size + ")");
            }
            yoff++;
        }
        return tiles[((xoff++) + startX) - 2000][(yoff + startY) - 2000];
    }

    public final int size() {
        return size;
    }
}

