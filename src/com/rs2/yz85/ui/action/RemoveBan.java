package com.rs2.yz85.ui.action;

import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Set;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class RemoveBan extends ActionModule {
    private JFrame frame;

    public RemoveBan(ServerUI ui) {
        this.frame = ui.frame;
    }

    public final void handleAction(ActionEvent event) {
        String ban = JOptionPane.showInputDialog(frame, "What is the host or IP?");
        Set<String> bans = world.getTempHostBans();
        if(bans.contains(ban)) {
            world.getTempHostBans().remove(ban);
            Logger.log("Removed ban " + ban);
        } else {
            Logger.log("Ban list does not contain " + ban);
        }
    }

    public final String getActionCmd() {
        return "remove ban";
    }
}
