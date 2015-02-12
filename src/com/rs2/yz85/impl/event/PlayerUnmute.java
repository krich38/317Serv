package com.rs2.yz85.impl.event;

import com.rs2.yz85.event.EventModule;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.util.Logger;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PlayerUnmute extends EventModule {
    private Player plr;

    public PlayerUnmute(Player plr) {
        this.plr = plr;
    }

    public PlayerUnmute() {
    }

    public final void run() {
        Logger.log("Unmuting host " + plr.getHost());
        World.getWorld().removeTempMute(plr.getHost());
    }

    public long[] getDelay() {
        return null;
    }

    public boolean willBeScheduled() {
        return false;
    }
}
