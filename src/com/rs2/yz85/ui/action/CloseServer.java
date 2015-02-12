package com.rs2.yz85.ui.action;

import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class CloseServer extends ActionModule {
    private ServerUI ui;

    public CloseServer(ServerUI ui) {
        this.ui = ui;
    }

    public final void handleAction(ActionEvent event) {
        world.setOpen(ui.lastPublic);
        Logger.log("Server set to " + ui.status());
        ui.lastPublic = !ui.lastPublic;
    }

    public final String getActionCmd() {
        return "server closed";
    }
}
