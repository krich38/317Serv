package com.rs2.yz85.ui.action;

import com.rs2.yz85.ui.ServerUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class AboutServer extends ActionModule {
    private JFrame frame;

    public AboutServer(ServerUI ui) {
        this.frame = ui.frame;
    }

    public final void handleAction(ActionEvent event) {
        JOptionPane.showMessageDialog(frame, "317Serv is a RuneScape 2 emulation framework designed for the 317\n protocol, more information can be found here:\n http://moparscape.org/smf/index.php/topic,309081.0.html");
    }

    public final String getActionCmd() {
        return "about";
    }
}
