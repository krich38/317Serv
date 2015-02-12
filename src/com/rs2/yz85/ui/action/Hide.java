package com.rs2.yz85.ui.action;

import com.rs2.yz85.model.World;
import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public class Hide extends ActionModule {
    private ServerUI ui;
                private final SystemTray tray = SystemTray.getSystemTray();
                    private TrayIcon icon;

    public Hide(ServerUI ui) {
        try {
            this.ui = ui;
            this.icon = new TrayIcon(ImageIO.read(new File("./com/rs2/yz85/ui/etc/icon.jpg")), "317Serv");
        } catch(IOException e) {
            Logger.err(e);
        }
    }
    public void handleAction(ActionEvent event) {
            try {
                PopupMenu popup = new PopupMenu("317Serv---- There are currently " + World.getWorld().getPlayers().size() + " players online.");
                icon.setPopupMenu(popup);
                icon.setImageAutoSize(true);
                ui.frame.setVisible(false);
                icon.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        ui.frame.setVisible(true);
                        tray.remove(icon);
                    }
                });
                tray.add(icon);
            } catch(AWTException e) {
                Logger.err(e);
            }
        
    }

    public String getActionCmd() {
        return "hide";
    }
}
