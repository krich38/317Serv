package com.rs2.yz85.ui.action;

import com.rs2.yz85.ui.ServerUI;

import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ClearOutput extends ActionModule {
    private ServerUI ui;

    public ClearOutput(ServerUI ui) {
        this.ui = ui;
    }

    public final void handleAction(ActionEvent event) {
        ui.output.setText("");
    }

    public final String getActionCmd() {
        return "clear";
    }
}
