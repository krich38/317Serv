package com.rs2.yz85.util;

import com.rs2.yz85.model.Entity;

import java.util.*;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class EntityTracker<T extends Entity> {
    private final Collection<T> newEntities = new HashSet<T>();
    private final Collection<T> knownEntities = new LinkedHashSet<T>();
    private final Collection<T> entitiesToRemove = new HashSet<T>();

    public final int size() {
        return knownEntities.size();
    }

    public final void add(T entity) {
        newEntities.add(entity);
    }

    public final void add(Collection<T> entities) {
        newEntities.addAll(entities);
    }

    public final boolean contains(T entity) {
        return newEntities.contains(entity) || knownEntities.contains(entity);
    }

    public final boolean isKnown(T entity) {
        return knownEntities.contains(entity) && !entitiesToRemove.contains(entity);
    }

    public final void remove(T entity) {
        entitiesToRemove.add(entity);
    }

    public final boolean isRemoving(T entity) {
        return entitiesToRemove.contains(entity);
    }

    public final boolean isAdding(T entity) {
        return newEntities.contains(entity);
    }

    public final void update() {
        knownEntities.removeAll(entitiesToRemove);
        knownEntities.addAll(newEntities);
        newEntities.clear();
        entitiesToRemove.clear();
    }

    public final String toString(Collection<T> l) {
        StringBuilder out = new StringBuilder();
        Iterator<T> it = l.iterator();
        while(it.hasNext()) {
            out.append(((it.next())).getIndex());
            if(it.hasNext()) {
                out.append(",");
            }
        }
        return out.toString();
    }

    public final boolean changed() {
        return !entitiesToRemove.isEmpty() || !newEntities.isEmpty();
    }

    public final Collection<T> getRemovingEntities() {
        return entitiesToRemove;
    }

    public final Collection<T> getNewEntities() {
        return newEntities;
    }

    public final Collection<T> getKnownEntities() {
        return knownEntities;
    }

    public final Collection<T> getAllEntities() {
        List<T> temp = new ArrayList<T>();
        temp.addAll(newEntities);
        temp.addAll(knownEntities);
        return temp;
    }
}

