package com.rs2.yz85.ui.action;

import com.rs2.yz85.model.World;

import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class ActionModule {
    protected World world = World.getWorld();

    public abstract void handleAction(ActionEvent event);

    public abstract String getActionCmd();
}
