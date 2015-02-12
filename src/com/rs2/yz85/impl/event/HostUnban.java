package com.rs2.yz85.impl.event;

import com.rs2.yz85.event.EventModule;
import com.rs2.yz85.model.World;
import com.rs2.yz85.util.Logger;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class HostUnban extends EventModule {
    private String host;

    public HostUnban(String host) {
        this.host = host;
    }

    public HostUnban() {
    }

    public final void run() {
        Logger.log("Unbanning host " + host);
        World.getWorld().removeTempBan(host);
    }

    public long[] getDelay() {
        return null;
    }

    public boolean willBeScheduled() {
        return false;
    }
}
