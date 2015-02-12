package com.rs2.yz85.model;

import com.rs2.yz85.util.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Kyle Richards
 * Created by IntelliJ IDEA.
 *
 * Simple immutable object that outputs "Hello World".
 */
public abstract class Commands {
    private static Map<String, Commands> cmds;

    public abstract void handleCommand(String command, Player p);

    public abstract String[] getInvocations();

    public static void loadCommands() {
        File cmdDir = new File("./com/rs2/yz85/impl/command/");
        if(cmds == null) {
            cmds = new HashMap<String, Commands>(100);
        }
        cmds.clear();
        if(cmdDir.isDirectory()) {
            try {
                for(File i : cmdDir.listFiles()) {
                    Class c = Class.forName("com.rs2.yz85.impl.command." + i.getName().replace(".class", ""));
                    if(c.getSuperclass().getSimpleName().equals("Commands")) {
                        Commands o = (Commands) c.newInstance();
                        for(String l : o.getInvocations()) {
                            cmds.put(l, o);
                        }
                    }
                }
            } catch(Exception e) {
                Logger.err(e);
            }
        } else {
            throw new IllegalArgumentException("Command directory does not exist.");
        }
    }

    public static boolean hasCommand(String cmdIndex) {
        return cmds.containsKey(cmdIndex);
    }

    public static Commands getCommand(String cmdIndex) {
        return cmds.get(cmdIndex);
    }
}
