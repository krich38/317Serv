package com.rs2.yz85.model;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class Inventory {
    private final Item[] items = new Item[27];
    private int index = 0;
    private boolean changed;

    public boolean hasChanged() {
        return changed;
    }

    public final void add(Item i) {
        items[index++] = i;
        i.setIndex(index);
        setChanged(true);
    }

    public final void remove(Item i) {
        for(int i1 = 0; i1 < items.length; i1++) {
            Item x = items[i1];
            if(i == x) {
                items[i1] = null;
            }
        }
        setChanged(true);
    }

    public final Item[] getItems() {
        return items;
    }

    public Item getItem(int index) {
        return items[index];
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
