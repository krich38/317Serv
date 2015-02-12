package com.rs2.yz85.impl.io;

import com.rs2.yz85.io.PlayerLoader;
import com.rs2.yz85.model.*;
import com.rs2.yz85.util.Data;
import com.rs2.yz85.util.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class PlayerLoaderImpl implements PlayerLoader {
    private Statement stmt;

    public final byte loadPlayer(Player plr) {
        Connection con = World.getWorld().getServer().getConnection();
        if(world.isFull()) {
            return 7;
        } else if(world.getPlayers().contains(plr)) {
            return 5;
        } else if(!isValid(plr.getUsername(), plr.getPassword())) {
            return 3;
        } else if(world.isTempHostBanned(plr.getHost())) {
            return 4;
        }
        plr.setAppearance(PlayerAppearance.instance(new int[] { 3, 19, 29, 35, 39, 44, 7, 8, 9, 5, 0 }));
        plr.setWaypoint(Waypoint.location(3254, 3420), true);
        int[] stats = new int[25];
        Arrays.fill(stats, 1);
        plr.setPlayerRights(PlayerRights.NORMAL_PLAYER);
        plr.setGender(PlayerGender.MALE);
        plr.setStats(stats);
        plr.setExps(stats);
        plr.setPassword(Data.convertTextToMD5(plr.getPassword()));
        Logger.log("done.");
        return 2;
        /**} else {
         try {
         stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM `users` WHERE `username`='" + plr.getUsername() + "' ");
         plr.setPassword(Data.convertTextToMD5(rs.getString("password")));
         plr.setWaypoint(Waypoint.location(rs.getInt("X_COORD"), rs.getInt("Y_COORD")), true);
         plr.setPlayerRights(PlayerRights.valueOf(rs.getString("PLAYER_RIGHTS")));
         int[] stats = new int[25];
         Arrays.fill(stats, 1);
         plr.setStats(stats);
         plr.setExps(stats);
         rs.close();
         } catch(SQLException e) {
         e.printStackTrace();
         }
         return 2;*/
    }

    private boolean isValid(String username, String password) {
        if((username.length() >= 3 && username.length() < 13) && (password.length() >= 3 && password.length() < 13)) {
            for(char c : username.toCharArray()) {
                if(!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)) {
                    return false;
                }
            }
            for(char c : password.toCharArray()) {
                if(!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public final void savePlayer(Player plr) {
        /**try {
         Connection con = World.getWorld().getServer().getConnection();
         stmt = con.createStatement();
         if(!userExists(plr.getUsername())) {
         stmt.executeUpdate("INSERT INTO `users` SET `username`='" + plr.getUsername() + "',`password`='" + plr.getPassword() + "',`X_COORD`='" + plr.getWaypoint().getXCoord() + "',`Y_COORD`='" + plr.getWaypoint().getYCoord() + "', `PLAYER_RIGHTS`='" + plr.getPlayerRights().toString() + "', `PLAYER_GENDER`='" + plr.getGender() + "' ");
         } else {
         stmt.executeUpdate("UPDATE `users` SET `username`='" + plr.getUsername() + "',`password`='" + plr.getPassword() + "',`X_COORD`='" + plr.getWaypoint().getXCoord() + "',`Y_COORD`='" + plr.getWaypoint().getYCoord() + "', `PLAYER_RIGHTS`='" + plr.getPlayerRights().toString() + "', `PLAYER_GENDER`='" + plr.getGender() + "' ");
         }
         } catch(SQLException e) {
         Logger.err(e);
         }*/
    }

    public final boolean userExists(String user) {
        /**try {
         Connection con = World.getWorld().getServer().getConnection();
         stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM `users`");
         while(rs.next()) {
         if(rs.getString("username").equalsIgnoreCase(user)) {
         return true;
         }
         }
         } catch(SQLException e) {
         Logger.err(e);
         }
         return false;*/
        return false;
    }
}
