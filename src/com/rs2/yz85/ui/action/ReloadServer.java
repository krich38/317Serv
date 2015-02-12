package com.rs2.yz85.ui.action;

import com.rs2.yz85.model.Commands;
import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ReloadServer extends ActionModule {
    public ReloadServer(ServerUI ui) {
    }

    public final void handleAction(ActionEvent event) {
        Logger.logNoLine("Reloading commands..");
        Commands.loadCommands();
        Logger.logNoPrefix("done.");
        Logger.log("Reloading events..");
        world.getEngine().initiateEvents();
        Logger.log("Reloaded server.");
    }

    public final String getActionCmd() {
        return "reload";
    }
}
