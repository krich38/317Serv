package com.rs2.yz85;

import com.rs2.yz85.impl.ServerImpl;
import com.rs2.yz85.io.PlayerLoader;

import java.sql.Connection;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public abstract class Server {
    public static Server getInstance(int port) {
        return new ServerImpl(port);
    }

    public abstract void start() throws Exception;

    public abstract void pluginPlayerLoader(PlayerLoader loader);

    public abstract void setMaxPlayers(int maxPlayers);

    public abstract void closeListener();

    public abstract void loadPackets() throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    public abstract void setDatabase(String user, String host, String database, String password);

    public abstract Connection getConnection();
}
