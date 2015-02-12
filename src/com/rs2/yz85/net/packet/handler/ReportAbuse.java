package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.util.Data;
import com.rs2.yz85.util.Logger;

import java.lang.reflect.Field;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ReportAbuse implements PacketHandler {
    public final void handlePacket(Packet p) {
        try {
            Player plr = (Player) p.getSession().getAttachment();
            String name = Data.longToPlayerName(p.readLongC());
            if(name.length() > 12) {
                name = name.substring(0, 12);
            }
            byte rule = (byte) ((p.readSignedByte() & 0xff) + 1);
            int mute = p.readSignedByte() & 0xff;
            String output = generateOutput(plr.getUsername() + " reported " + name + ".", rule, mute);
            Logger.log(output);
            Logger.log("reported.log", output);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String generateOutput(String s, byte rule, int mute) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder(s);
        int length = 30 - s.length();
        for(int i = 0; i < length; i++) {
            sb.append(" ");
        }
        Field field = null;
        for(Field i : this.getClass().getFields()) {
            if(i.getByte(i) == rule) {
                field = i;
                break;
            }
        }
        return sb.append("Rule: ").append(Data.optimizeText(field.getName().replaceAll("_", "").toLowerCase())).append(" Mute: ").append(mute == 0 ? "NO" : "YES").toString();
    }

    public final int[] getBindings() {
        return new int[] { REPORT_PLAYER };
    }

    public static final byte OFFENSIVE_LANGUAGE = 1, ITEM_SCAMMING = 2, PASSWORD_SCAMMING = 3, BUG_ABUSE = 4, JAGEX_IMPERSONATION = 5, ACCOUNT_SHARING = 6, MACROING = 7, MULTIPLE_LOGGING_IN = 8, ENCOURAGING_OTHERS_TO_BREAK_RULES = 9, MISUSE_OF_CUSTOMER_SUPPORT = 10, ADVERTISING = 11, REAL_WORLD_ITEM_TRADING = 12;
}
