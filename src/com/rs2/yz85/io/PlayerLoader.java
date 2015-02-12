package com.rs2.yz85.io;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public interface PlayerLoader {
    public World world = World.getWorld();

    public byte loadPlayer(Player plr);

    public void savePlayer(Player plr);
}
