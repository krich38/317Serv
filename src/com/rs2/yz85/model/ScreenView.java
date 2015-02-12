package com.rs2.yz85.model;

import com.rs2.yz85.util.TileIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ScreenView {
    public final List<Player> getPlayersInAttackRange(MobileEntity mob) {
        return getPlayersInView(2, mob);
    }

    public final List<Player> getPlayersInView(MobileEntity mob) {
        return getPlayersInView(15, mob);
    }

    public final List<Player> getPlayersInView(int range, MobileEntity mob) {
        List<Player> players = new ArrayList<Player>();
        TileIterator ti = new TileIterator(range, range, range + 1, range + 1, mob);
        for(int i = 0; i < ti.size(); i++) {
            Tile t = ti.next();
            if(t != null) {
                Set<Player> temp = t.getPlayers();
                if(temp != null && !temp.isEmpty()) {
                    players.addAll(temp);
                }
            }
        }
        return players;
    }
}
