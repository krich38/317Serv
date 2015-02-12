package com.rs2.yz85.net.packet.handler;

import com.rs2.yz85.model.Player;
import com.rs2.yz85.model.PlayerAppearance;
import com.rs2.yz85.model.PlayerGender;
import com.rs2.yz85.net.packet.Packet;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class CharacterAppearance implements PacketHandler {
    public final void handlePacket(Packet p) {
        Player plr = (Player) p.getSession().getAttachment();
        PlayerAppearance ap = plr.getAppearance();
        int gender = p.readSignedByte(), head = p.readSignedByte(), jaw = p.readSignedByte(), torso = p.readSignedByte(), arms = p.readSignedByte(), hands = p.readSignedByte(), legs = p.readSignedByte(), feet = p.readSignedByte(), hairC = p.readSignedByte(), torsoC = p.readSignedByte(), legsC = p.readSignedByte(), feetC = p.readSignedByte(), skinC = p.readSignedByte();
        plr.setGender(gender == 0 ? PlayerGender.MALE : PlayerGender.FEMALE);
        ap.setHead(head);
        ap.setTorso(torso);
        ap.setArms(arms);
        ap.setHands(hands);
        ap.setLegs(legs);
        ap.setFeet(feet);
        ap.setHairColour(hairC);
        ap.setTorsoColour(torsoC);
        ap.setLegColour(legsC);
        ap.setFeetColour(feetC);
        ap.setSkinColour(skinC);
        ap.setJaw(jaw);
    }

    public final int[] getBindings() {
        return new int[] { APPERANCE_FRAME };
    }
}
