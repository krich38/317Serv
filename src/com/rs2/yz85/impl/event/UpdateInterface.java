package com.rs2.yz85.impl.event;

import com.rs2.yz85.event.EventModule;
import com.rs2.yz85.model.World;
import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import java.text.DecimalFormat;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class UpdateInterface extends EventModule {
    private final DecimalFormat df2 = Logger.df2;
    private final ServerUI ui = World.getWorld().getInterface();

    public final void run() {
        int seconds = (int) ((System.currentTimeMillis() - Logger.START_TIME) / 1000), minutes = (seconds / 60), hours = (minutes / 60), days = (hours / 24);
        ui.timeArea.setText(days + "d" + df2.format(hours % 24) + "h" + df2.format(minutes % 60) + "m" + df2.format(seconds % 60) + "s");
        long maxMem = Runtime.getRuntime().maxMemory();
        ui.memUsage.setText((maxMem - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "/" + maxMem / 1024 / 1024 + "mb");
    }

    public final long[] getDelay() {
        return new long[] { 1, 10000 };
    }

    public final boolean willBeScheduled() {
        return true;
    }
}
