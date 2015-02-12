package com.rs2.yz85.ui.action;

import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ClearBanList extends ActionModule {
    private JFrame frame;

    public ClearBanList(ServerUI ui) {
        this.frame = ui.frame;
    }

    public final void handleAction(ActionEvent event) {
        int b = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear the ban list?");
        if(b == 0) {
            if(!world.getTempHostBans().isEmpty()) {
                world.getTempHostBans().clear();
                Logger.log("Cleared ban list.");
            } else {
                Logger.log("No bans to be cleared.");
            }
        }
    }

    public final String getActionCmd() {
        return "clear bans";
    }
}
