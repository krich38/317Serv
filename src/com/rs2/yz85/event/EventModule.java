package com.rs2.yz85.event;

import java.util.TimerTask;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class EventModule extends TimerTask {
    public abstract void run();

    public abstract long[] getDelay();

    public abstract boolean willBeScheduled();
}
