package com.rs2.yz85.ui;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.ui.action.ActionModule;
import com.rs2.yz85.util.Data;
import com.rs2.yz85.util.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ServerUI extends MouseAdapter implements ActionListener, MouseListener {
    public JFrame frame;
    private World world = World.getWorld();
    private Vector<String> names;
    private Map<String, ActionModule> events;
    private JList playerList;
    private Toolkit kit;
    public JTextArea timeArea, memUsage, output;
    public boolean lastPublic;

    public ServerUI() {
        try {
            initComponents();
            names = new Vector<String>();
            world.setInterface(this);
            kit = Toolkit.getDefaultToolkit();
            loadEvents();
            updateList("rifklol");
            updateList("rifkl");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEvents() throws Exception {
        File actionDir = new File("./com/rs2/yz85/ui/action/");
        if(actionDir.isDirectory()) {
            events = new HashMap<String, ActionModule>();
            for(File i : actionDir.listFiles()) {
                Class c = Class.forName("com.rs2.yz85.ui.action." + i.getName().replaceAll(".class", ""));
                if(c.getSuperclass().getSimpleName().equals("ActionModule")) {
                    Constructor x = c.getConstructor(ServerUI.class);
                    ActionModule a = (ActionModule) x.newInstance(this);
                    events.put(a.getActionCmd(), a);
                }
            }
        } else {
            throw new Exception("ActionModule directory does not exist");
        }
    }

    private void initComponents() throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException, IOException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("317Serv - by Yz85Racer");
        UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        JPanel jPanel1 = new JPanel(), jPanel2 = new JPanel();
        Banner banner = new Banner();
        banner.setImage(ImageIO.read(new File("./com/rs2/yz85/ui/etc/banner.jpg")));
        output = new JTextArea();
        output.setColumns(20);
        output.setEditable(false);
        output.setFont(new Font("Metal", 0, 12));
        output.setRows(5);
        output.setLineWrap(true);
        output.setToolTipText("Output from the server will be displayed here.");
        JCheckBox serverPub = new JCheckBox();
        JScrollPane jScrollPane1 = new JScrollPane(), jScrollPane2 = new JScrollPane(), jScrollPane3 = new JScrollPane(), jScrollPane4 = new JScrollPane();
        playerList = new JList();
        playerList.addMouseListener(this);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerList.addFocusListener(new FocusAdapter() {
            public final void focusLost(FocusEvent e) {
                ((JList) e.getSource()).clearSelection();
            }
        });
        timeArea = new JTextArea();
        memUsage = new JTextArea();
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu fileMenu = new JMenu(), editMenu = new JMenu(), helpMenu = new JMenu();
        JMenuItem jMenuItem5 = new JMenuItem(), jMenuItem3 = new JMenuItem(), jMenuItem4 = new JMenuItem();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setBackground(new Color(51, 51, 255));
        banner.setBackground(new Color(0, 0, 0));
        GroupLayout bannerLayout = new GroupLayout(banner);
        banner.setLayout(bannerLayout);
        bannerLayout.setHorizontalGroup(bannerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 580, Short.MAX_VALUE));
        bannerLayout.setVerticalGroup(bannerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 90, Short.MAX_VALUE));
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setViewportView(output);
        serverPub.setText("Server closed");
        serverPub.addActionListener(this);
        playerList.setToolTipText("Players currently online will be displayed here");
        jScrollPane2.setViewportView(playerList);
        jScrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setToolTipText("Online time of the server");
        jScrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        timeArea.setColumns(1);
        timeArea.setRows(1);
        timeArea.setText("0d0h0m");
        timeArea.setEditable(false);
        timeArea.setToolTipText("Online time of the server");
        jScrollPane3.setViewportView(timeArea);
        jScrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        memUsage.setColumns(1);
        memUsage.setRows(1);
        memUsage.setEditable(false);
        memUsage.setToolTipText("Memory usage of the server.");
        jScrollPane4.setViewportView(memUsage);
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(banner, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(serverPub)
                                        .addGap(253, 253, 253)
                                        .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                                .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addComponent(jScrollPane3))))
                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(banner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(serverPub)
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)));
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 538, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        frame.setPreferredSize(new Dimension(600, 612));
        jMenuBar1.setToolTipText("Clear the output box");
        fileMenu.setText("File");
        jMenuItem5.setText("Reload");
        jMenuItem5.setToolTipText("Reload commands, events and event modules");
        jMenuItem5.addActionListener(this);
        JMenuItem banList = new JMenuItem("Ban List");
        banList.addActionListener(this);
        banList.setToolTipText("List of all banned hosts and IPs.");
        JMenuItem banRemove = new JMenuItem("Remove Ban");
        banRemove.addActionListener(this);
        banRemove.setToolTipText("Remove a banned host or IP.");
        JMenuItem clearList = new JMenuItem("Clear Bans");
        clearList.addActionListener(this);
        clearList.setToolTipText("Clear all banned hosts and IP's from the ban list.");
        JMenuItem addList = new JMenuItem("Add Ban");
        addList.addActionListener(this);
        addList.setToolTipText("Add a ban to the list.");
                fileMenu.add(jMenuItem5);
        fileMenu.add(addList);
        fileMenu.add(banRemove);
        fileMenu.add(banList);
        if(SystemTray.isSupported()) {
                JMenuItem hide = new JMenuItem("Hide");
        hide.addActionListener(this);
        hide.setToolTipText("Hide this window in system tray.");
        fileMenu.add(hide);
        }
        fileMenu.add(clearList);
        jMenuBar1.add(fileMenu);
        editMenu.setText("Edit");
        jMenuItem3.setText("Clear");
        jMenuItem3.setToolTipText("Clear the output box");
        jMenuItem3.addActionListener(this);
        editMenu.add(jMenuItem3);
        jMenuBar1.add(editMenu);
        helpMenu.setText("Help");
        jMenuItem4.setText("About");
        jMenuItem4.addActionListener(this);
        jMenuItem4.setToolTipText("About 317Serv");
        helpMenu.add(jMenuItem4);
        jMenuBar1.add(helpMenu);
        frame.setJMenuBar(jMenuBar1);
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        frame.pack();
    }

    public final void actionPerformed(ActionEvent event) {
        try {
            String cmd = event.getActionCommand().toLowerCase();
            if(events.containsKey(cmd)) {
                events.get(cmd).handleAction(event);
            } else {
                Logger.log("Unknown action command: " + cmd);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public final void updateList(String username) {
        username = Data.optimizeText(username);
        if(names.contains(username)) {
            names.remove(username);
        } else {
            names.add(username);
        }
        kit.beep();
        Collections.sort(names);
        DefaultListModel model = new DefaultListModel();
        for(String i : names) {
            model.addElement(i);
        }
        playerList.setModel(model);
    }

    public final void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
            String value = playerList.getModel().getElementAt(playerList.locationToIndex(e.getPoint())).toString();
            if(value != null) {
                for(final Player p : world.getPlayers()) {
                if(p.getUsername().equalsIgnoreCase(value)) {
                String[] options = { "Kick", "Ban", "Message", "Cancel" };
                int n = JOptionPane.showOptionDialog(frame, "What would you like to do to " + value + "?", "Action Required", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                switch(n) {
                    case 0:
                        p.setLoggedIn(false);
                        Logger.log(value + " player kicked.");
                        break;
                    case 1:
                        world.tempBanHost(p.getHost());
                        p.sendMessage("You have been banned.");
                        world.getEngine().getTimer().schedule(new TimerTask() {
                            public final void run() {
                                p.setLoggedIn(false);
                            }
                        }, 2000);
                        Logger.log(value + " player banned.");
                        break;
                    case 2:
                        String msg = JOptionPane.showInputDialog(frame, "What would you like to message?");
                        if(msg != null) {
                            p.sendMessage(msg);
                            Logger.log("Message successfully sent to " + value);
                        } else {
                            Logger.log("Message not sent.");
                        }
                        break;
                }
                }
                }
            }
        }
    }

    public String status() {
       return  (lastPublic ? "OPEN." : "CLOSED.");
    }

    public static class Banner extends JPanel {
        protected Image ci = null;

        public void setImage(Image si) {
            this.ci = si;
            validate();
            repaint();
        }

        public Image getDisplayedImage() {
            return this.ci;
        }

        public void update(Graphics g) {
            if(ci != null) {
                g.drawImage(ci, 0, 0, this.getSize().width, this.getSize().height, this);
            } else {
                Color c = g.getColor();
                g.setColor(Color.white);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(c);
            }
        }

        public void paint(Graphics g) {
            update(g);
        }
    }
}


