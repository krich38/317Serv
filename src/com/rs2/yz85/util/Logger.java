package com.rs2.yz85.util;

import com.rs2.yz85.model.World;
import com.rs2.yz85.ui.ServerUI;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles logging events.
 */
public class Logger {
    public static final DecimalFormat df2 = new DecimalFormat("00");
    private static PrintStream out, err;
    private static Map<String, PrintStream> outStreams = new HashMap<String, PrintStream>();
    public static long START_TIME;
    private static ServerUI ui = World.getWorld().getInterface();

    private static PrintStream newStream(String file) throws IOException {
        PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("logs/" + file)), 1024), true);
        outStreams.put(file, p);
        return p;
    }

    private static String time() {
        int seconds = (int) ((System.currentTimeMillis() - START_TIME) / 1000);
        int minutes = (seconds / 60);
        int hours = (minutes / 60);
        synchronized(df2) {
            return df2.format(hours % 24) + ":" + df2.format(minutes % 60) + ":" + df2.format(seconds % 60);
        }
    }

    private static String prefix() {
        return "[" + time() + "] ";
    }

    public static void close() {
        for(Map.Entry<String, PrintStream> stringPrintStreamEntry : outStreams.entrySet()) {
            PrintStream stream = stringPrintStreamEntry.getValue();
            stream.println("Closing stream..");
            stream.close();
        }
    }

    public static void flush(String f) {
        PrintStream tmpStream = outStreams.get(f);
        if(tmpStream == null) {
            Logger.err("Failed to flush " + f + " (No such stream)");
        } else {
            tmpStream.flush();
        }
    }

    public static synchronized void log(Object o) {
        String s = prefix() + o.toString();
        out.println(s);
        out.flush();
        ui.output.append(s + "\n");
        ui.output.setCaretPosition(ui.output.getDocument().getLength());
    }

    public static synchronized void log(String f, Object o) {
        String s = prefix() + o.toString();
        try {
            PrintStream tmpStream = outStreams.get(f);
            if(tmpStream == null) {
                tmpStream = newStream(f);
            }
            tmpStream.println(s);
        } catch(IOException ex) {
            Logger.err("Failed to log to " + f + ", msg='" + s + "'");
        }
    }

    public static synchronized void err(Throwable e) {
        String s = prefix() + e.toString();
        err.println(s);
        ui.output.append(s + "\n");
        for(StackTraceElement ste : e.getStackTrace()) {
            String s1 = "\t" + ste.toString();
            err.println(s1);
            ui.output.append(s1 + "\n");
        }
        ui.output.setCaretPosition(ui.output.getDocument().getLength());
        err.flush();
    }

    public static synchronized void err(String s1) {
        String s = prefix() + s1;
        err.println(s);
        err.flush();
        ui.output.append(s + "\n");
        ui.output.setCaretPosition(ui.output.getDocument().getLength());
    }

    public static void logNoLine(Object o) {
        String s = prefix() + o.toString();
        out.print(s);
        out.flush();
        ui.output.append(s);
        ui.output.setCaretPosition(ui.output.getDocument().getLength());
    }

    public static void logNoPrefix(Object o) {
        String s = o.toString();
        out.println(s);
        out.flush();
        ui.output.append(s + "\n");
        ui.output.setCaretPosition(ui.output.getDocument().getLength());
    }

    static {
        START_TIME = System.currentTimeMillis();
        try {
            out = newStream("out.log");
            err = newStream("err.log");
        } catch(IOException ieo) {
            ieo.printStackTrace();
        }
    }
}
