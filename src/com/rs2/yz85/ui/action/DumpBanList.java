package com.rs2.yz85.ui.action;

import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import java.awt.event.ActionEvent;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class DumpBanList extends ActionModule {
    public DumpBanList(ServerUI ui) {
    }

    public final void handleAction(ActionEvent event) {
        if(!world.getTempHostBans().isEmpty()) {
            Logger.log("BAN LIST DUMP -----------------");
            for(String i : world.getTempHostBans()) {
                Logger.log("\t" + i);
            }
            Logger.log("END BAN LIST DUMP --------");
        } else {
            Logger.log("No bans in list.");
        }
    }

    public final String getActionCmd() {
        return "ban list";
    }
}
