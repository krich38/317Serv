package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.net.packet.Packet;
import com.rs2.yz85.util.Logger;

import java.lang.reflect.Field;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class ButtonClick implements PacketHandler {
    public final void handlePacket(Packet p) {
        try {
            Player player = (Player) p.getSession().getAttachment();
            int button = p.readShortB();
            switch(button) {
                case LOGOUT:
                    player.setLoggedIn(false);
                    break;
                case MOVE_RUN:
                    player.setRunning(true);
                    break;
                case MOVE_WALK:
                    player.setRunning(false);
                    break;
                default:
                    Field match = null;
                    for(Field i : this.getClass().getFields()) {
                        if(i.getInt(i) == button) {
                            match = i;
                            break;
                        }
                    }
                    Logger.log("Action button: " + button + " Matches field " + match.getName());
            }
        } catch(IllegalAccessException e) {
            Logger.err(e);
        }
    }

    public int[] getBindings() {
        return new int[] { BUTTON_CLICK };
    }

    public static final int BRIGHTNESS_DARK = 5452, BRIGHTNESS_NORMAL = 6273, BRIGHTNESS_BRIGHT = 6275, BRIGHTNESS_VBRIGHT = 6277, MBUTTONS_ONE = 6279, //mouse buttons: 1
            MBUTTONS_TWO = 6278, CHATEFFECTS_ON = 6280, CHATEFFECTS_OFF = 6281, SPLITPRIVATE_ON = 952, SPLITPRIVATE_OFF = 953, ACCEPTAID_YES = 12591, ACCEPTAID_NO = 12590, LOGOUT = 2458, MOVE_WALK = 152, MOVE_RUN = 153, RETALIATE_ON = 150, RETALIATE_OFF = 151, ATTACK_CHOP = 2429, ATTACK_SLASH = 2432, ATTACK_LUNGE = 2431, ATTACK_BLOCK = 2430, PRAY_THICKSKIN = 5609, PRAY_BURSTSTR = 5610, PRAY_CLARITY = 5611, PRAY_ROCKSKIN = 5612, PRAY_SUPERSTRENGTH = 5613, PRAY_IMPROVED = 5614, PRAY_RAPIDRESTORE = 5615, PRAY_RAPIDHEAL = 5616, PRAY_PROTECT = 5617, PRAY_STEELSKIN = 5618, PRAY_ULTIMATESTR = 5619, PRAY_INCREDIBLEREFLEX = 5620, PRAY_PROTECTMAG = 5621, PRAY_PROTECTMIS = 5622, PRAY_PROTECTMELEE = 5623, PRAY_RETRIBUTION = 683, PRAY_REDEMPTION = 684, PRAY_SMITE = 685, MAG_B2B = 1159, //BONES TO BANANAS
            MAG_TELE_VARROCK = 1164, MAG_TELE_LUMBY = 1167, MAG_TELE_FALA = 1170, MAG_TELE_CAMALOT = 1174, MAG_TELE_ARDOUGNE = 1540, MAG_TELE_WATCHTOWER = 1541, MAG_BONESTOPEACHES = 15877, MAG_TELE_TROLLHEIM = 7455, MAG_TELE_APEATOLL = 18470, MAG_CHARGE = 1193, STAT_ATT = 8654, STAT_HITS = 8655, STAT_MINING = 8656, STAT_STR = 8657, STAT_AGILITY = 8658, STAT_SMITHING = 8659, STAT_DEF = 8660, STAT_HERB = 8661, STAT_FISHING = 8662, STAT_RANGE = 8663, STAT_THIEVING = 8664, STAT_COOKING = 8665, STAT_PRAY = 8666, STAT_CRAFTING = 8667, STAT_FIREMAKING = 8668, STAT_MAGIC = 8669, STAT_FLETCHING = 8670, STAT_WOODCUTTING = 8671, STAT_RUNECRAFT = 8672, STAT_SLAYER = 12162, STAT_FARMING = 13928, EMOTE_YES = 168, EMOTE_NO = 169, EMOTE_THINK = 162, EMOTE_BOW = 164, EMOTE_ANGRY = 165, EMOTE_CRY = 161, EMOTE_LAUGH = 170, EMOTE_CHEER = 161, EMOTE_WAVE = 163, EMOTE_BECKON = 167, EMOTE_CLAP = 172, EMOTE_DANCE = 166, EMOTE_PANIC = 13362, EMOTE_JIG = 13363, EMOTE_SPIN = 13364, EMOTE_HEADBANG = 13365, EMOTE_JUMP = 13366, EMOTE_RASPBERRY = 13367, EMOTE_YAWN = 13368, EMOTE_SALUTE = 13369, EMOTE_SHRUG = 13370, EMOTE_KISS = 11100, EMOTE_GLASSBOX = 667, EMOTE_CLIMBROPE = 6503, EMOTE_LEAN = 6506, EMOTE_GLASSWALL = 666, EMOTE_GOBLINBOW = 13383, EMOTE_GOBLINDANCE = 13384, EMOTE_SCARED = 15166, EMOTE_ZOMBIEWALK = 18464, EMOTE_ZOMBIEDANCE = 18465;
}
