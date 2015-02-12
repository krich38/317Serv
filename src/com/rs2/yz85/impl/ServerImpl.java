package com.rs2.yz85.impl;

import com.rs2.yz85.Server;
import com.rs2.yz85.ServerEngine;
import com.rs2.yz85.io.PlayerLoader;
import com.rs2.yz85.model.Commands;
import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.World;
import com.rs2.yz85.net.NetworkingHandler;
import com.rs2.yz85.net.packet.builder.PacketBuilder;
import com.rs2.yz85.net.packet.handler.PacketHandler;
import com.rs2.yz85.ui.ServerUI;
import com.rs2.yz85.util.EntityList;
import com.rs2.yz85.util.Logger;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import java.io.File;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ServerImpl extends Server {
    private int port;
    private ServerEngine engine;
    private SocketAcceptor acceptor;
    private World world;
    private Connection con;
    private SocketAcceptorConfig sac;
    private NetworkingHandler network;
    private InetSocketAddress sock;

    public ServerImpl(int port) {
        try {
            System.out.print("Starting user interface...");
            new ServerUI().frame.setVisible(true);
            this.port = port;
            this.engine = new ServerEngine();
            this.acceptor = new SocketAcceptor();
            this.world = World.getWorld();
            loadPackets();
            PacketBuilder.loadBuilders();
            Commands.loadCommands();
            System.out.println("done.");
            Logger.log("Loaded " + engine.getPacketHandlers().size() + " packet handlers.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public final void loadPackets() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        File packetDir = new File("./com/rs2/yz85/net/packet/handler/");
        if(packetDir.isDirectory()) {
            Map<Integer, PacketHandler> packets = new HashMap<Integer, PacketHandler>();
            for(File i : packetDir.listFiles()) {
                String name = i.getName();
                if(name.contains(".class")) {
                    Class c = Class.forName("com.rs2.yz85.net.packet.handler." + name.replace(".class", ""));
                    for(Class x : c.getInterfaces()) {
                        if(x.getSimpleName().equals("PacketHandler")) {
                            PacketHandler tmp = (PacketHandler) c.newInstance();
                            for(int y : tmp.getBindings()) {
                                packets.put(y, tmp);
                            }
                        }
                    }
                }
            }
            engine.setPacketHandlers(packets);
        }
    }

    public final void setDatabase(String user, String host, String database, String password) {
        try {
            long begin = System.currentTimeMillis();
            Logger.logNoLine("Connecting to MySQL (" + host + ") ");
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
            if(!con.isClosed()) {
                Logger.logNoPrefix("Connection established in " + (System.currentTimeMillis() - begin) + "ms.");
            }
        } catch(Exception e) {
            Logger.err(e);
        }
    }

    public Connection getConnection() {
        return con;
    }

    public final void start() throws Exception {
        engine.initiateEvents();
        Logger.logNoLine("Building listener... ");
        sac = new SocketAcceptorConfig();
        sac.getSessionConfig().setTcpNoDelay(false);
        sac.setReuseAddress(true);
        world.setServer(this);
        sac.setBacklog(100);
        acceptor.bind(sock = new InetSocketAddress(port), network = new NetworkingHandler(engine), sac);
        Logger.logNoPrefix("Listener built, binding on " + port);
    }

    public final void pluginPlayerLoader(PlayerLoader loader) {
        world.setPlayerLoader(loader);
    }

    public final void setMaxPlayers(int maxPlayers) {
        EntityList<Player> tmpStore = World.getWorld().getPlayers();
        if(maxPlayers > tmpStore.size()) {
            EntityList<Player> newStore = new EntityList<Player>(maxPlayers);
            newStore.addAll(tmpStore);
            world.setPlayers(newStore);
            world.setMaxPlayers(maxPlayers);
        } else {
            throw new IllegalArgumentException("Cannot set max players, there are more than " + maxPlayers + " online.");
        }
    }

    public final void closeListener() {
        try {
            Logger.logNoLine("Closing listener...");
            for(Player p : World.getWorld().getPlayers()) {
                p.setLoggedIn(false);
            }
            Thread.sleep(1000);
            Logger.logNoPrefix("Listener closed.");
        } catch(InterruptedException e) {
            Logger.err(e);
        }
    }
}
