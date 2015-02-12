package com.rs2.yz85.util;

import com.rs2.yz85.model.Entity;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class EntityListIterator<E extends Entity> implements Iterator<E> {
    private final Integer[] indicies;
    private final Object[] entities;
    private final EntityList entityList;
    private int curIndex = 0;

    public EntityListIterator(Object[] entities, Set<Integer> indicies, EntityList entityList) {
        this.entities = entities;
        this.indicies = indicies.toArray(new Integer[indicies.size()]);
        this.entityList = entityList;
    }

    public final boolean hasNext() {
        return indicies.length != curIndex;
    }

    public final E next() {
        Object tmp = entities[indicies[curIndex]];
        curIndex++;
        return (E) tmp;
    }

    public final void remove() {
        if(curIndex >= 1) {
            entityList.remove(indicies[curIndex - 1]);
        }
    }
}
