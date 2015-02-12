package com.rs2.yz85.ui.action;

import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class AddBan extends ActionModule {
    public AddBan(ServerUI ui) {
    }

    public final void handleAction(ActionEvent event) {
        String ban = JOptionPane.showInputDialog("Enter the IP or Host:");
        world.tempBanHost(ban);
        Logger.log("Ban " + ban + " successfully added.");
    }

    public final String getActionCmd() {
        return "add ban";
    }
}
