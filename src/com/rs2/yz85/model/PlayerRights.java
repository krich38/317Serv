package com.rs2.yz85.model;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public enum PlayerRights {
    NORMAL_PLAYER(0),
    PLAYER_MODERATOR(1),
    ADMIN(2),
    OWNER(3);
    private int right;

    private PlayerRights(int right) {
        this.right = right;
    }

    public int getRight() {
        return right;
    }

    public static int valueOf(PlayerRights rights) {
        switch(rights) {
            case NORMAL_PLAYER:
                return 0;
            case PLAYER_MODERATOR:
                return 1;
            case ADMIN:
                return 2;
            case OWNER:
                return 3;
            default:
                return 0;
        }
    }
}
