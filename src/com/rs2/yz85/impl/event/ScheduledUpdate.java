package com.rs2.yz85.impl.event;

import com.rs2.yz85.event.EventModule;
import com.rs2.yz85.io.ClientUpdater;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ScheduledUpdate extends EventModule {
    private final ClientUpdater updater = new ClientUpdater();

    public final void run() {
        updater.fullUpdate();
    }

    public final long[] getDelay() {
        return new long[] { 600, 600 };
    }

    public boolean willBeScheduled() {
        return true;
    }
}
