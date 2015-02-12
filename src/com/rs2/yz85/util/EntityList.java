package com.rs2.yz85.util;

import com.rs2.yz85.model.Entity;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class EntityList<T extends Entity> extends AbstractCollection<T> {
    private final Entity[] entities;
    private final Set<Integer> indicies = new HashSet<Integer>();
    private int curIndex = 1, capacity;

    public EntityList(int capacity) {
        entities = new Entity[capacity];
        this.capacity = capacity;
    }

    public EntityList() {
        this(50);
    }

    public final boolean add(T entity) {
        add(entity, curIndex);
        return true;
    }

    public final void remove(T entity) {
        entities[entity.getIndex()] = null;
        indicies.remove(entity.getIndex());
    }

    public final T remove(int index) {
        Object temp = entities[index];
        entities[index] = null;
        indicies.remove(index);
        return (T) temp;
    }

    public final T get(int index) {
        return (T) entities[index];
    }

    private void add(T entity, int index) {
        if(entities[curIndex] != null) {
            increaseIndex();
            add(entity, curIndex);
        } else {
            entities[curIndex] = entity;
            entity.setIndex(index);
            indicies.add(curIndex);
            increaseIndex();
        }
    }

    public final Iterator<T> iterator() {
        return new EntityListIterator<T>(entities, indicies, this);
    }

    private void increaseIndex() {
        curIndex++;
        if(curIndex >= capacity) {
            curIndex = 1;
        }
    }

    public final boolean contains(T entity) {
        return indexOf(entity) > -1;
    }

    public final int indexOf(T entity) {
        for(int index : indicies) {
            if(entities[index].equals(entity)) {
                return index;
            }
        }
        return -1;
    }

    public final int count() {
        return indicies.size();
    }

    public final int size() {
        return indicies.size();
    }
}
